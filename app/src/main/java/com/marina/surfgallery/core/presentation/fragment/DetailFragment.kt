package com.marina.surfgallery.core.presentation.fragment

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.marina.surfgallery.R
import com.marina.surfgallery.core.presentation.view_model.DetailFragmentViewModel
import com.marina.surfgallery.databinding.FragmentDetailBinding

class DetailFragment : Fragment(R.layout.fragment_detail) {

    val args: DetailFragmentArgs by navArgs()

    private lateinit var binding: FragmentDetailBinding
    private val viewModel: DetailFragmentViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDetailBinding.bind(view)
        viewModel.setPicture(args.pictureItem)
        subscribeOnViewModel()
    }

    private fun subscribeOnViewModel() {
        viewModel.picture.observe(viewLifecycleOwner) {
            binding.postFragmentTitle.text = it.title
            binding.postFragmentText.text = it.content
            binding.postFragmentDate.text = it.publicationDate.toString()
            loadImage(it.photoUrl, binding.postFragmentImage, requireContext())
        }
    }

    private fun loadImage(url: String, imageView: ImageView, context: Context) {
        Glide.with(context)
            .load(url)
            .into(imageView)
    }
}