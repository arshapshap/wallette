package com.example.wallette.navigation

import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
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
        navController?.navigate(R.id.transactionsFragment)
    }

    override fun openTransaction(id: String) {
        //TODO("Not yet implemented")
    }

    override fun openLoginPage() {
        navController?.navigate(R.id.loginFragment)
    }

    override fun openRegisterPage() {
        navController?.navigate(R.id.registerFragment)
    }

    override fun openStatistics() {
        navController?.navigate(R.id.statisticsFragment)
    }

    override fun openAccounts() {
        //navController?.navigate(R.id.)
    }

    override fun openCategories() {
        TODO("Not yet implemented")
    }

    override fun openTags() {
        TODO("Not yet implemented")
    }
}