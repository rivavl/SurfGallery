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
import com.marina.surfgallery.app.App
import com.marina.surfgallery.auth.presentation.ViewModelFactoryAuth
import com.marina.surfgallery.auth.presentation.entity.User
import com.marina.surfgallery.auth.presentation.view_model.ProfileFragmentViewModel
import com.marina.surfgallery.common.entity.Resource
import com.marina.surfgallery.databinding.FragmentProfileBinding
import javax.inject.Inject

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var viewModel: ProfileFragmentViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelFactoryAuth

    private val component by lazy {
        (requireActivity().application as App).component
    }

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory)[ProfileFragmentViewModel::class.java]
        binding = FragmentProfileBinding.bind(view)
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav_bar).visibility =
            View.VISIBLE

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
            createDialog()
        }
    }

    private fun createDialog() {
        val builder = androidx.appcompat.app.AlertDialog.Builder(requireContext())
        builder.setMessage("Вы точно хотете выйти из приложения?")
            .setPositiveButton(
                "Да, точно"
            ) { _, _ ->
                viewModel.logout()
            }
            .setNegativeButton("Нет") { _, _ -> }
            .create()
            .show()
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