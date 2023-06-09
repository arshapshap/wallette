package com.example.feature_auth.di

import com.example.common.data.TokenManager
import com.example.common.di.scopes.AuthorizationScope
import com.example.feature_auth.data.di.DataModule
import com.example.feature_auth.presentation.screen.AuthorizationRouter
import com.example.feature_auth.presentation.screen.login.LoginFragment
import com.example.feature_auth.presentation.screen.login.LoginViewModel
import com.example.feature_auth.presentation.screen.register.RegisterFragment
import com.example.feature_auth.presentation.screen.register.RegisterViewModel
import dagger.BindsInstance
import dagger.Component
import retrofit2.Retrofit

@AuthorizationScope
@Component(
    modules = [DataModule::class, AuthorizationBindsModule::class]
)
interface AuthorizationComponent : AuthorizationFeatureApi {

    @Component.Builder
    interface Builder {

        fun build(): AuthorizationComponent

        @BindsInstance
        fun router(router: AuthorizationRouter): Builder

        @BindsInstance
        fun retrofit(retrofit: Retrofit): Builder

        @BindsInstance
        fun tokenManager(tokenManager: TokenManager): Builder
    }

    fun inject(loginFragment: LoginFragment)

    fun inject(registerFragment: RegisterFragment)

    fun loginViewModel(): LoginViewModel.Factory

    fun registerViewModel(): RegisterViewModel.Factory
}