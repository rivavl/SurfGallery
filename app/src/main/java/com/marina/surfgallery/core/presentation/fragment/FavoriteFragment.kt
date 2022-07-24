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
import com.marina.surfgallery.app.App
import com.marina.surfgallery.core.presentation.ViewModelFactoryCore
import com.marina.surfgallery.core.presentation.adapter.PicturesListAdapter
import com.marina.surfgallery.core.presentation.view_model.FavoriteFragmentViewModel
import com.marina.surfgallery.databinding.FragmentFavoritesBinding
import javax.inject.Inject

class FavoriteFragment : Fragment(R.layout.fragment_favorites) {

    private lateinit var binding: FragmentFavoritesBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var picturesListAdapter: PicturesListAdapter
    private lateinit var viewModel: FavoriteFragmentViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelFactoryCore

    private val component by lazy {
        (requireActivity().application as App).component
    }

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory)[FavoriteFragmentViewModel::class.java]
        binding = FragmentFavoritesBinding.bind(view)
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav_bar).visibility =
            View.VISIBLE
        setupRecyclerView()

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