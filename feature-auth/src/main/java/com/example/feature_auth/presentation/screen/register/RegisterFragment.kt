package com.example.feature_auth.presentation.screen.register

import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.common.base.BaseFragment
import com.example.common.base.BaseViewModel
import com.example.common.di.FeatureUtils
import com.example.feature_auth.R
import com.example.feature_auth.databinding.FragmentRegisterBinding
import com.example.feature_auth.di.AuthorizationComponent
import com.example.feature_auth.di.AuthorizationFeatureApi

class RegisterFragment : BaseFragment<RegisterViewModel>(R.layout.fragment_register) {

    private val binding by viewBinding(FragmentRegisterBinding::bind)
    private val authorizationComponent: AuthorizationComponent by lazy {
        FeatureUtils.getFeature(this, AuthorizationFeatureApi::class.java)
    }

    override fun inject() {
        authorizationComponent.inject(this)
    }

    override fun createViewModel(): BaseViewModel =
        authorizationComponent.registerViewModel().create()

    override fun initViews() {
        with (binding) {
            openLoginPageButton.setOnClickListener {
                viewModel.openLoginPage()
            }

            registerButton.setOnClickListener {
                val email = emailEditText.text.toString().trim()
                val password = passwordEditText.text.toString().trim()
                val passwordConfirm = confirmPasswordEditText.text.toString().trim()
                viewModel.tryRegister(
                    email = email,
                    password = password,
                    passwordConfirm = passwordConfirm
                )
            }

            emailEditText.addTextChangedListener {
                binding.errorTextView.isVisible = false
            }

            passwordEditText.addTextChangedListener {
                binding.errorTextView.isVisible = false
            }

            confirmPasswordEditText.addTextChangedListener {
                binding.errorTextView.isVisible = false
            }
        }
    }

    override fun subscribe() {
        with (viewModel) {
            errorMessageLiveData.observe(viewLifecycleOwner) {
                binding.errorTextView.isVisible = it != null
                binding.errorTextView.text = it
            }
        }
    }
}