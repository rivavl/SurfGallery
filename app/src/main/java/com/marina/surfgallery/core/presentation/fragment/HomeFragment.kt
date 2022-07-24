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
import com.marina.surfgallery.core.data.local.db.AppDatabase
import com.marina.surfgallery.core.data.local.file.SavePictureInFile
import com.marina.surfgallery.core.data.repository.PictureRepositoryImpl
import com.marina.surfgallery.core.domain.use_case.DeletePictureUseCase
import com.marina.surfgallery.core.domain.use_case.GetAllPicturesUseCase
import com.marina.surfgallery.core.domain.use_case.SaveBitmapUseCase
import com.marina.surfgallery.core.domain.use_case.SavePictureInfoUseCase
import com.marina.surfgallery.core.presentation.HomeViewModelFactory
import com.marina.surfgallery.core.presentation.adapter.PicturesListAdapter
import com.marina.surfgallery.core.presentation.view_model.HomeFragmentViewModel
import com.marina.surfgallery.databinding.FragmentHomeBinding

class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var picturesListAdapter: PicturesListAdapter
    private lateinit var viewModel: HomeFragmentViewModel


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav_bar).visibility =
            View.VISIBLE
        setupRecyclerView()

        val sp = requireContext().getSharedPreferences("1234567890", Context.MODE_PRIVATE)
        val dataSource = SharedPrefsHelper(sp)
        val saver = SavePictureInFile(requireActivity().application)
        val database = AppDatabase.getInstance(requireActivity().application)
        val repository = PictureRepositoryImpl(database, dataSource, saver)
        val useCase = GetAllPicturesUseCase(repository)
        val delUseCase = DeletePictureUseCase(repository)
        val saveBitmapUseCase = SaveBitmapUseCase(repository)
        val savePictureInfoUseCase = SavePictureInfoUseCase(repository)
        viewModel = ViewModelProvider(
            this,
            HomeViewModelFactory(useCase, delUseCase, saveBitmapUseCase, savePictureInfoUseCase)
        )[HomeFragmentViewModel::class.java]

        viewModel.picturesList.observe(viewLifecycleOwner) {
            picturesListAdapter.submitList(it.data)
        }
    }

    private fun setupRecyclerView() {
        recyclerView = binding.galleryRv
        picturesListAdapter = PicturesListAdapter()
        recyclerView.apply {
            adapter = picturesListAdapter
            layoutManager = GridLayoutManager(activity, 2)
        }
        setupClickListener()
    }

    private fun setupClickListener() {
        picturesListAdapter.onPictureItemClickListenerSave = { pic, bitmap ->
            pic.isFavorite = true
            viewModel.savePicture(pic)
            viewModel.saveBitmap(bitmap, pic.photoUrl)
        }

        picturesListAdapter.onPictureItemClickListenerDelete = {
            it.isFavorite = false
            viewModel.deletePicture(it)
        }

        picturesListAdapter.onPictureItemClick = {
            val action = HomeFragmentDirections.actionHomeFragmentToFragmentDetail(it)
            findNavController().navigate(action)
        }
    }
}