package com.marina.surfgallery.core.presentation.fragment

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.marina.surfgallery.R
import com.marina.surfgallery.common.SharedPrefsHelper
import com.marina.surfgallery.common.AppDatabase
import com.marina.surfgallery.common.Constants
import com.marina.surfgallery.core.data.local.file.SavePictureInFile
import com.marina.surfgallery.core.data.repository.PictureRepositoryImpl
import com.marina.surfgallery.core.domain.use_case.DeletePictureUseCase
import com.marina.surfgallery.core.domain.use_case.GetFavoritePicturesUseCase
import com.marina.surfgallery.core.presentation.FavoriteViewModelFactory
import com.marina.surfgallery.core.presentation.adapter.PicturesListAdapter
import com.marina.surfgallery.core.presentation.view_model.FavoriteFragmentViewModel
import com.marina.surfgallery.databinding.FragmentFavoritesBinding

class FavoriteFragment : Fragment(R.layout.fragment_favorites) {

    private lateinit var binding: FragmentFavoritesBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var picturesListAdapter: PicturesListAdapter
    private lateinit var viewModel: FavoriteFragmentViewModel


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFavoritesBinding.bind(view)
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav_bar).visibility =
            View.VISIBLE
        setupRecyclerView()

        val sp = requireContext().getSharedPreferences(Constants.SHARED_PREFS_NAME, Context.MODE_PRIVATE)
        val dataSource = SharedPrefsHelper(sp)
        val saver = SavePictureInFile(requireActivity().application)
        val database = AppDatabase.getInstance(requireActivity().application)
        val repository = PictureRepositoryImpl(database, dataSource, saver)
        val delUseCase = DeletePictureUseCase(repository)
        val getAll = GetFavoritePicturesUseCase(repository)
        viewModel = ViewModelProvider(
            this,
            FavoriteViewModelFactory(delUseCase, getAll)
        )[FavoriteFragmentViewModel::class.java]

        viewModel.picturesList.observe(viewLifecycleOwner) {
            picturesListAdapter.submitList(it.data)
        }
    }

    private fun setupRecyclerView() {
        recyclerView = binding.favoriteRv
        picturesListAdapter = PicturesListAdapter()
        recyclerView.apply {
            adapter = picturesListAdapter
            layoutManager = GridLayoutManager(activity, 2)
        }
        setupClickListener()
    }

    private fun setupClickListener() {
        picturesListAdapter.onPictureItemClickListenerDelete = {
            it.isFavorite = false
            viewModel.deletePicture(it)
        }

        picturesListAdapter.onPictureItemClick = {
            val action = FavoriteFragmentDirections.actionFavoriteFragmentToFragmentDetail(it)
            findNavController().navigate(action)
        }
    }
}