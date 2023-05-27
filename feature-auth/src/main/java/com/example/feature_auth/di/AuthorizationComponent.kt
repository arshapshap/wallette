package com.example.feature_auth.di

import com.example.feature_auth.presentation.screen.AuthorizationRouter
import com.example.feature_auth.presentation.screen.login.LoginFragment
import com.example.feature_auth.presentation.screen.register.RegisterFragment
import com.example.feature_auth.presentation.screen.login.LoginViewModel
import com.example.feature_auth.presentation.screen.register.RegisterViewModel
import com.example.feature_auth.presentation.screen.main.AuthorizationFragment
import com.example.feature_auth.presentation.screen.main.AuthorizationViewModel
import dagger.BindsInstance
import dagger.Component

@Component
interface AuthorizationComponent : AuthorizationFeatureApi {

    @Component.Builder
    interface Builder {
        fun build(): AuthorizationComponent

        @BindsInstance
        fun router(router: AuthorizationRouter): Builder
    }



    fun inject(authorizationFragment: AuthorizationFragment)

    fun inject(loginFragment: LoginFragment)

    fun inject(registerFragment: RegisterFragment)

    fun authorizationViewModel(): AuthorizationViewModel.Factory

    fun loginViewModel(): LoginViewModel.Factory

    fun registerViewModel(): RegisterViewModel.Factory
}