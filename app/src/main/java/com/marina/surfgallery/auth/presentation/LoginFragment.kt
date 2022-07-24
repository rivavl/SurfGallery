package com.marina.surfgallery.auth.presentation

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import com.marina.surfgallery.R
import com.marina.surfgallery.auth.data.repository.AuthRepositoryImpl
import com.marina.surfgallery.auth.domain.use_case.request.LoginUseCase
import com.marina.surfgallery.auth.domain.use_case.validation.ValidateLoginUseCase
import com.marina.surfgallery.auth.domain.use_case.validation.ValidatePasswordUseCase
import com.marina.surfgallery.auth.presentation.entity.FieldsState
import com.marina.surfgallery.common.SharedPrefsHelper
import com.marina.surfgallery.databinding.FragmentLoginBinding
import com.redmadrobot.inputmask.MaskedTextChangedListener
import com.redmadrobot.inputmask.MaskedTextChangedListener.Companion.installOn

class LoginFragment : Fragment(R.layout.fragment_login) {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var viewModel: LoginFragmentViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBinding(view)
        initLoginMask()
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

    private fun initLoginMask() {
        installOn(
            binding.loginEdt,
            PHONE_MASK,
            object : MaskedTextChangedListener.ValueListener {
                override fun onTextChanged(
                    maskFilled: Boolean,
                    extractedValue: String,
                    formattedValue: String
                ) {
                    viewModel.setLogin(extractedValue)
                }
            }
        )
    }

    private fun initBinding(view: View) {
        binding = FragmentLoginBinding.bind(view)
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

        viewModel.error.observe(viewLifecycleOwner) {
            showSnackbar(it)
        }

        viewModel.isLoading.observe(viewLifecycleOwner) {
            binding.enterButton.isLoading = it
        }
    }

    private fun setOnClickListener() {
        binding.enterButton.setOnClickListener {
            val password = binding.passwordEdt.text.toString()
            viewModel.checkLoginData(password)
        }
    }

    private fun showSnackbar(info: String) {
        Snackbar.make(
            binding.root,
            info,
            Snackbar.LENGTH_LONG
        ).setAnchorView(binding.enterButton).show()
    }

    companion object {
        private const val PHONE_MASK = "+7 ([000]) [000] [00] [00]"
    }
}