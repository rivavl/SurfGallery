package com.marina.surfgallery.profile.presentation

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.marina.surfgallery.R
import com.marina.surfgallery.core.util.Resource
import com.marina.surfgallery.core.data.SharedPrefsHelper
import com.marina.surfgallery.databinding.FragmentProfileBinding
import com.marina.surfgallery.profile.ProfileViewModelFactory
import com.marina.surfgallery.profile.data.repository.ProfileRepositoryImpl
import com.marina.surfgallery.profile.domain.use_case.GetUserInfoUseCase
import com.marina.surfgallery.profile.presentation.entity.User

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var viewModel: ProfileViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProfileBinding.bind(view)
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav_bar).visibility =
            View.VISIBLE

        val sp = requireContext().getSharedPreferences("1234567890", Context.MODE_PRIVATE)
        val ds = SharedPrefsHelper(sp)
        val repository = ProfileRepositoryImpl(ds)
        val getUserInfoUseCase = GetUserInfoUseCase(repository)
        viewModel = ViewModelProvider(
            this,
            ProfileViewModelFactory(getUserInfoUseCase)
        )[ProfileViewModel::class.java]

        viewModel.userInfo.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    it.data?.let { it1 -> setUserInfo(it1) }
                }
            }
        }
    }

    private fun setUserInfo(userInfo: User) {
        with(binding) {
            profileFragmentName.text = userInfo.firstName
            profileFragmentSurname.text = userInfo.lastName
            profileFragmentQuote.text = userInfo.quote
            profileFragmentCity.text = userInfo.city
            profileFragmentPhone.text = userInfo.phone
            profileFragmentEmail.text = userInfo.email
            loadImage(userInfo.photo, profileFragmentImage, requireContext())
        }
    }

    private fun loadImage(url: String, imageView: ImageView, context: Context) {
        Glide.with(context)
            .load(url)
            .into(imageView)
    }
}