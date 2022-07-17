package com.marina.surfgallery.core.presentation

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.marina.surfgallery.R
import com.marina.surfgallery.common.SharedPrefsHelper
import com.marina.surfgallery.databinding.FragmentHomeBinding
import com.marina.surfgallery.core.data.repository.PictureRepositoryImpl
import com.marina.surfgallery.core.domain.use_case.GetAllPicturesUseCase
import com.marina.surfgallery.core.presentation.recycler_view.PicturesListAdapter

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
        val repository = PictureRepositoryImpl(dataSource)
        val useCase = GetAllPicturesUseCase(repository)
        viewModel = ViewModelProvider(
            this,
            HomeViewModelFactory(useCase)
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
    }
}