package com.example.feature_auth.presentation.screen.login

import android.content.res.ColorStateList
import android.os.Build
import android.os.Build.VERSION_CODES
import android.widget.EditText
import androidx.annotation.ColorInt
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.common.base.BaseFragment
import com.example.common.base.BaseViewModel
import com.example.common.di.FeatureUtils
import com.example.feature_auth.R
import com.example.feature_auth.databinding.FragmentLoginBinding
import com.example.feature_auth.di.AuthorizationComponent
import com.example.feature_auth.di.AuthorizationFeatureApi

class LoginFragment : BaseFragment<LoginViewModel>(R.layout.fragment_login) {

    private val binding by viewBinding(FragmentLoginBinding::bind)
    private val authorizationComponent: AuthorizationComponent by lazy {
        FeatureUtils.getFeature(this, AuthorizationFeatureApi::class.java)
    }

    override fun inject() {
        authorizationComponent.inject(this)
    }

    override fun createViewModel(): BaseViewModel =
        authorizationComponent.loginViewModel().create()

    override fun initViews() {
        with (binding) {
            openRegisterPageButton.setOnClickListener {
                viewModel.openRegisterPage()
            }
            loginButton.setOnClickListener {
                val email = emailEditText.text.toString().trim()
                val password = passwordEditText.text.toString().trim()

                viewModel.tryLogin(
                    email = email,
                    password = password)
            }
            emailEditText.addTextChangedListener {
                binding.errorTextView.isVisible = false
            }
            passwordEditText.addTextChangedListener {
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

    private fun setInputColor(editText: EditText, @ColorInt color: Int) {
        editText.setHintTextColor(color)
        editText.backgroundTintList = ColorStateList.valueOf(color)
        if (Build.VERSION.SDK_INT >= VERSION_CODES.M)
            editText.compoundDrawableTintList = ColorStateList.valueOf(color)
    }
}