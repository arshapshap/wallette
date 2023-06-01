package com.example.wallette.navigation

import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import com.example.feature_auth.presentation.screen.AuthorizationRouter
import com.example.feature_settings.presentation.SettingsRouter
import com.example.feature_statistics_impl.presentation.StatisticsRouter
import com.example.wallette.R

class Navigator() : StatisticsRouter, AuthorizationRouter, SettingsRouter {

    private var navController: NavController? = null
    private var activity: AppCompatActivity? = null

    fun attachNavController(navController: NavController, activity: AppCompatActivity) {
        this.navController = navController
        this.activity = activity
    }

    override fun openTransactions() {
        navController?.navigate(
            resId = R.id.transactionsFragment,
            args = null,
            navOptions = getNavOptions()
        )
    }

    override fun openTransaction(id: String) {
        //TODO("Not yet implemented")
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