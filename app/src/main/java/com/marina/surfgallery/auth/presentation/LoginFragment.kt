package com.marina.surfgallery.auth.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.marina.surfgallery.R
import com.marina.surfgallery.auth.domain.use_case.ValidateLoginUseCase
import com.marina.surfgallery.auth.domain.use_case.ValidatePasswordUseCase
import com.marina.surfgallery.auth.presentation.entity.FieldsState
import com.marina.surfgallery.databinding.FragmentLoginBinding

class LoginFragment : Fragment(R.layout.fragment_login) {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var viewModel: LoginFragmentViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBinding(view)

        val passwordUseCase = ValidatePasswordUseCase()
        val loginUseCase = ValidateLoginUseCase()
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(loginUseCase, passwordUseCase)
        )[LoginFragmentViewModel::class.java]

        subscribeOnLiveData()
        setOnClickListener()
    }

    private fun initBinding(view: View) {
        binding = FragmentLoginBinding.bind(view)
        binding.textLayoutLogin.errorIconDrawable = null
        binding.textLayoutPassword.errorIconDrawable = null
    }

    private fun subscribeOnLiveData() {
        viewModel.loginState.observe(viewLifecycleOwner) {
            when (it) {
                is FieldsState.LoginError -> {
                    binding.textLayoutLogin.error = it.message
                }
                else -> {}
            }
        }

        viewModel.passwordState.observe(viewLifecycleOwner) {
            when (it) {
                is FieldsState.PasswordError -> {
                    binding.textLayoutPassword.error = it.message
                }
                else -> {}
            }
        }
    }

    private fun setOnClickListener() {
        binding.enterButton.setOnClickListener {
            val login = binding.etLogin.text.toString()
            val password = binding.etPassword.text.toString()
            viewModel.checkLoginAndPassword(login, password)
        }
    }

}