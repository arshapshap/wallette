package com.example.feature_auth.presentation.screen.main

import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.common.base.BaseFragment
import com.example.common.base.BaseViewModel
import com.example.common.di.FeatureUtils
import com.example.feature_auth.R
import com.example.feature_auth.databinding.FragmentAuthorizationBinding
import com.example.feature_auth.di.AuthorizationComponent
import com.example.feature_auth.di.AuthorizationFeatureApi

class AuthorizationFragment : BaseFragment<AuthorizationViewModel>(R.layout.fragment_authorization) {

    private val binding by viewBinding(FragmentAuthorizationBinding::bind)
    private val authorizationComponent: AuthorizationComponent by lazy {
        FeatureUtils.getFeature(this, AuthorizationFeatureApi::class.java)
    }

    override fun inject() {
        authorizationComponent.inject(this)
    }

    override fun createViewModel(): BaseViewModel =
        authorizationComponent.authorizationViewModel().create()

    override fun initViews() {
        //TODO("Not yet implemented")
    }

    override fun subscribe() {
        //TODO("Not yet implemented")
    }
}