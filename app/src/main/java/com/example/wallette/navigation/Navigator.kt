package com.example.wallette.navigation

import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import com.example.common.domain.models.Account
import com.example.common.domain.models.Category
import com.example.common.domain.models.Tag
import com.example.feature_auth.presentation.screen.AuthorizationRouter
import com.example.feature_settings.presentation.SettingsRouter
import com.example.feature_statistics_impl.presentation.StatisticsRouter
import com.example.wallette.R

class Navigator : StatisticsRouter, AuthorizationRouter, SettingsRouter {

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
        navController?.navigate(R.id.accountsFragment)
    }

    override fun openCategories() {
        navController?.navigate(R.id.categoriesFragment)
    }

    override fun openTags() {
        navController?.navigate(R.id.tagsFragment)
    }

    override fun openAccount(account: Account) {
        //TODO("Not yet implemented")
    }

    override fun openCategory(category: Category) {
        //TODO("Not yet implemented")
    }

    override fun openTag(tag: Tag) {
        //TODO("Not yet implemented")
    }

    override fun openAccountCreating() {
        //TODO("Not yet implemented")
    }

    override fun openCategoryCreating() {
        //TODO("Not yet implemented")
    }

    override fun openTagCreating() {
        //TODO("Not yet implemented")
    }

    override fun openTransferCreating() {
        //TODO("Not yet implemented")
    }
}