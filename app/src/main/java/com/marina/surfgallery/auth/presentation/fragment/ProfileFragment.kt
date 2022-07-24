package com.marina.surfgallery.auth.presentation.fragment

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import com.marina.surfgallery.R
import com.marina.surfgallery.auth.data.repository.auth.AuthRepositoryImpl
import com.marina.surfgallery.auth.domain.use_case.profile.GetUserInfoUseCase
import com.marina.surfgallery.auth.domain.use_case.profile.LogoutUseCase
import com.marina.surfgallery.auth.presentation.ProfileViewModelFactory
import com.marina.surfgallery.auth.presentation.entity.User
import com.marina.surfgallery.auth.presentation.view_model.ProfileViewModel
import com.marina.surfgallery.common.AppDatabase
import com.marina.surfgallery.common.Constants
import com.marina.surfgallery.common.Resource
import com.marina.surfgallery.common.SharedPrefsHelper
import com.marina.surfgallery.core.data.local.file.SavePictureInFile
import com.marina.surfgallery.databinding.FragmentProfileBinding

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var viewModel: ProfileViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProfileBinding.bind(view)
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav_bar).visibility =
            View.VISIBLE

        val sp = requireContext().getSharedPreferences(Constants.SHARED_PREFS_NAME, Context.MODE_PRIVATE)
        val ds = SharedPrefsHelper(sp)
        val saver = SavePictureInFile(requireActivity().application)
        val database = AppDatabase.getInstance(requireActivity().application)
        val repository = AuthRepositoryImpl(ds, database, saver)
        val getUserInfoUseCase = GetUserInfoUseCase(repository)
        val logoutUseCase = LogoutUseCase(repository)
        viewModel = ViewModelProvider(
            this,
            ProfileViewModelFactory(getUserInfoUseCase, logoutUseCase)
        )[ProfileViewModel::class.java]

        viewModel.userInfo.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    it.data?.let { it1 -> setUserInfo(it1) }
                }
                else -> {}
            }
        }

        viewModel.isLogout.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    this.findNavController().navigate(R.id.action_profileFragment_to_loginFragment)
                    binding.enterButton.isLoading = false
                }

                is Resource.Error -> {
                    showSnackbar(it.message!!)
                    binding.enterButton.isLoading = false
                }

                is Resource.Loading -> {
                    binding.enterButton.isLoading = true

                }
            }
        }

        binding.enterButton.setOnClickListener {
            viewModel.logout()
        }
    }

    private fun showSnackbar(info: String) {
        Snackbar.make(
            binding.root,
            info,
            Snackbar.LENGTH_LONG
        ).setAnchorView(binding.enterButton).show()
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