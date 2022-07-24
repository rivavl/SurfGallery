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
import com.marina.surfgallery.core.presentation.entity.PictureItem
import com.marina.surfgallery.core.presentation.view_model.HomeFragmentViewModel
import com.marina.surfgallery.databinding.FragmentHomeBinding
import javax.inject.Inject

class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var picturesListAdapter: PicturesListAdapter
    private lateinit var viewModel: HomeFragmentViewModel

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
        viewModel = ViewModelProvider(this, viewModelFactory)[HomeFragmentViewModel::class.java]
        binding = FragmentHomeBinding.bind(view)
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav_bar).visibility =
            View.VISIBLE
        setupRecyclerView()

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
            createDialog(it)
        }

        picturesListAdapter.onPictureItemClick = {
            val action = HomeFragmentDirections.actionHomeFragmentToFragmentDetail(it)
            findNavController().navigate(action)
        }

        binding.searchIv.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
        }
    }

    private fun createDialog(pictureItem: PictureItem) {
        val builder = androidx.appcompat.app.AlertDialog.Builder(requireContext())
        builder.setMessage("Вы точно хотете удалить из избранного?")
            .setPositiveButton(
                "Да, точно"
            ) { _, _ ->
                viewModel.deletePicture(pictureItem)
                pictureItem.isFavorite = false
            }
            .setNegativeButton("Нет") { _, _ -> }
            .create()
            .show()
    }

}