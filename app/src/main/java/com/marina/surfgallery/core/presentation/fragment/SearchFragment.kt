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
import com.marina.surfgallery.app.App
import com.marina.surfgallery.core.presentation.ViewModelFactoryCore
import com.marina.surfgallery.core.presentation.adapter.PicturesListAdapter
import com.marina.surfgallery.core.presentation.util.SearchResult
import com.marina.surfgallery.core.presentation.view_model.SearchFragmentViewModel
import com.marina.surfgallery.databinding.FragmentSearchBinding
import javax.inject.Inject

class SearchFragment : Fragment(R.layout.fragment_search) {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var picturesListAdapter: PicturesListAdapter
    private lateinit var viewModel: SearchFragmentViewModel

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
        viewModel = ViewModelProvider(this, viewModelFactory)[SearchFragmentViewModel::class.java]
        binding = FragmentSearchBinding.bind(view)
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav_bar).visibility =
            View.VISIBLE
        setupRecyclerView()

        viewModel.picturesList.observe(viewLifecycleOwner) {
            Log.e("setOnQueryTextListener observe", it.toString())
            when (it) {
                is SearchResult.Success -> {
                    picturesListAdapter.submitList(it.data)
                    noResults(false)
                    enterYourQuery(false)
                    setRVVisibility(true)
                }
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