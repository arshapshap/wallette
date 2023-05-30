package com.example.wallette.navigation

import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import com.example.feature_auth.presentation.screen.AuthorizationRouter
import com.example.feature_statistics_impl.StatisticsRouter
import com.example.wallette.R

class Navigator() : StatisticsRouter, AuthorizationRouter {

    private var navController: NavController? = null
    private var activity: AppCompatActivity? = null

    fun attachNavController(navController: NavController, activity: AppCompatActivity) {
        this.navController = navController
        this.activity = activity
    }

    override fun openTransactions() {
        navController?.navigate(R.id.loginFragment)
    }

    override fun openLoginPage() {
        navController?.navigate(
            resId = R.id.loginFragment,
            args = null,
            navOptions = getNavOptions())
    }

    override fun openRegisterPage() {
        navController?.navigate(
            resId = R.id.registerFragment,
            args = null,
            navOptions = getNavOptions())
    }

    override fun openStatistics() {
        navController?.navigate(
            resId = R.id.statisticsFragment,
            args = null,
            navOptions = getNavOptions())
    }

    private fun getNavOptions()
        = NavOptions.Builder()
        //.setEnterAnim(androidx.navigation.ui.R.animator.nav_default_enter_anim)
        .build()
}