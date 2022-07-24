package com.marina.surfgallery.auth.presentation.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import com.marina.surfgallery.R
import com.marina.surfgallery.app.App
import com.marina.surfgallery.auth.domain.use_case.validation.ValidateLoginUseCase
import com.marina.surfgallery.auth.domain.use_case.validation.ValidatePasswordUseCase
import com.marina.surfgallery.auth.presentation.ViewModelFactoryAuth
import com.marina.surfgallery.auth.presentation.entity.FieldsState
import com.marina.surfgallery.auth.presentation.view_model.LoginFragmentViewModel
import com.marina.surfgallery.common.util.Constants
import com.marina.surfgallery.databinding.FragmentLoginBinding
import com.redmadrobot.inputmask.MaskedTextChangedListener
import com.redmadrobot.inputmask.MaskedTextChangedListener.Companion.installOn
import javax.inject.Inject

class LoginFragment : Fragment(R.layout.fragment_login) {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var viewModel: LoginFragmentViewModel

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

        viewModel = ViewModelProvider(this, viewModelFactory)[LoginFragmentViewModel::class.java]
        initBinding(view)
        initLoginMask()
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav_bar).visibility =
            View.GONE

        subscribeOnLiveData()
        setOnClickListener()
        if (requireContext().getSharedPreferences(
                Constants.SHARED_PREFS_NAME,
                AppCompatActivity.MODE_PRIVATE
            ).all.isNotEmpty()
        ) {
            launchFragment(R.id.action_loginFragment_to_homeFragment)
        }
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


    private fun launchFragment(id: Int) {
        findNavController().navigate(id)
    }

    companion object {
        private const val PHONE_MASK = "+7 ([000]) [000] [00] [00]"
    }
}