package com.marina.surfgallery.core.presentation.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.marina.surfgallery.R
import com.marina.surfgallery.common.AppDatabase
import com.marina.surfgallery.common.Constants
import com.marina.surfgallery.common.SharedPrefsHelper
import com.marina.surfgallery.core.data.local.file.SavePictureInFile
import com.marina.surfgallery.core.data.repository.PictureRepositoryImpl
import com.marina.surfgallery.core.domain.use_case.DeletePictureUseCase
import com.marina.surfgallery.core.domain.use_case.GetFilteredPicturesUseCase
import com.marina.surfgallery.core.domain.use_case.SaveBitmapUseCase
import com.marina.surfgallery.core.domain.use_case.SavePictureInfoUseCase
import com.marina.surfgallery.core.presentation.SearchViewModelFactory
import com.marina.surfgallery.core.presentation.adapter.PicturesListAdapter
import com.marina.surfgallery.core.presentation.util.SearchResult
import com.marina.surfgallery.core.presentation.view_model.SearchFragmentViewModel
import com.marina.surfgallery.databinding.FragmentSearchBinding

class SearchFragment : Fragment(R.layout.fragment_search) {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var picturesListAdapter: PicturesListAdapter
    private lateinit var viewModel: SearchFragmentViewModel


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSearchBinding.bind(view)
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav_bar).visibility =
            View.VISIBLE
        setupRecyclerView()

        val sp =
            requireContext().getSharedPreferences(Constants.SHARED_PREFS_NAME, Context.MODE_PRIVATE)
        val dataSource = SharedPrefsHelper(sp)
        val saver = SavePictureInFile(requireActivity().application)
        val database = AppDatabase.getInstance(requireActivity().application)
        val repository = PictureRepositoryImpl(database, dataSource, saver)
        val delUseCase = DeletePictureUseCase(repository)
        val saveBitmapUseCase = SaveBitmapUseCase(repository)
        val savePictureInfoUseCase = SavePictureInfoUseCase(repository)
        val filtered = GetFilteredPicturesUseCase(repository)
        viewModel = ViewModelProvider(
            this,
            SearchViewModelFactory(filtered, saveBitmapUseCase, savePictureInfoUseCase, delUseCase)
        )[SearchFragmentViewModel::class.java]

        viewModel.picturesList.observe(viewLifecycleOwner) {
            Log.e("setOnQueryTextListener observe", it.toString())
            when (it) {
                is SearchResult.Success -> {
                    picturesListAdapter.submitList(it.data)
                    noResults(false)
                    enterYourQuery(false)
                    setRVVisibility(true)
                }
                is SearchResult.Loading -> {}
                is SearchResult.Error -> {}
                is SearchResult.NoResults -> {
                    noResults(true)
                    enterYourQuery(false)
                    setRVVisibility(false)
                }
                is SearchResult.IsEmpty -> {
                    noResults(false)
                    enterYourQuery(true)
                    setRVVisibility(false)
                }
                else -> {}
            }
        }
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                Log.e("setOnQueryTextListener query", query.toString())
                if (query?.isNotEmpty() == true) {
                    viewModel.getPictures(query)
                    binding.searchRv.visibility = View.VISIBLE
                    binding.searchFragmentEnterQuery.visibility = View.GONE
                } else {
                    viewModel.clearPics()
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                Log.e("setOnQueryTextListener newText", newText.toString())
                if (newText?.isNotEmpty() == true) {
                    viewModel.getPictures(newText)
                    binding.searchRv.visibility = View.VISIBLE
                    binding.searchFragmentEnterQuery.visibility = View.GONE
                } else {
                    viewModel.clearPics()
                }
                return false
            }
        })
    }

    private fun enterYourQuery(isVisible: Boolean) {
        binding.searchFragmentEnterQuery.isVisible = isVisible
    }

    private fun setRVVisibility(isVisible: Boolean) {
        binding.searchRv.isVisible = isVisible
    }

    private fun noResults(isVisible: Boolean) {
        binding.searchFragmentNoResults.isVisible = isVisible
    }

    private fun setupRecyclerView() {
        recyclerView = binding.searchRv
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
    }
}