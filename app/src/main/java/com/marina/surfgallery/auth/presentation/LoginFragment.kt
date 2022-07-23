package com.marina.surfgallery.auth.presentation

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.marina.surfgallery.R
import com.marina.surfgallery.auth.data.repository.AuthRepositoryImpl
import com.marina.surfgallery.auth.domain.use_case.request.LoginUseCase
import com.marina.surfgallery.auth.domain.use_case.validation.ValidateLoginUseCase
import com.marina.surfgallery.auth.domain.use_case.validation.ValidatePasswordUseCase
import com.marina.surfgallery.auth.presentation.entity.FieldsState
import com.marina.surfgallery.common.SharedPrefsHelper
import com.marina.surfgallery.core.presentation.fragment.SearchFragment
import com.marina.surfgallery.databinding.FragmentLoginBinding

class LoginFragment : Fragment(R.layout.fragment_login) {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var viewModel: LoginFragmentViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBinding(view)
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav_bar).visibility =
            View.GONE

        val passwordUseCase = ValidatePasswordUseCase()
        val loginUseCase = ValidateLoginUseCase()
        val sp = requireContext().getSharedPreferences("1234567890", Context.MODE_PRIVATE)
        val dataSourceHelper = SharedPrefsHelper(sp)
        val repository = AuthRepositoryImpl(dataSourceHelper)
        val userInfoUseCase = LoginUseCase(repository)
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(loginUseCase, passwordUseCase, userInfoUseCase)
        )[LoginFragmentViewModel::class.java]

        subscribeOnLiveData()
        setOnClickListener()
    }

    private fun initBinding(view: View) {
        binding = FragmentLoginBinding.bind(view)
        binding.loginTil.errorIconDrawable = null
        binding.passwordTil.errorIconDrawable = null
    }

    private fun subscribeOnLiveData() {
        viewModel.loginState.observe(viewLifecycleOwner) {
            when (it) {
                is FieldsState.LoginError -> {
                    binding.loginTil.error = it.message
                }
                else -> {}
            }
        }

        viewModel.passwordState.observe(viewLifecycleOwner) {
            when (it) {
                is FieldsState.PasswordError -> {
                    binding.passwordTil.error = it.message
                }
                else -> {}
            }
        }

        viewModel.isValidToken.observe(viewLifecycleOwner) {
            if (it) {
                findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
            }
        }
    }

    private fun setOnClickListener() {
        binding.enterButton.setOnClickListener {
            val login = binding.loginEdt.text.toString()
            val password = binding.passwordEdt.text.toString()
            viewModel.checkLoginAndPassword(login, password)
        }
    }

}