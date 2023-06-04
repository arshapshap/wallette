package com.example.wallette.navigation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import com.example.common.domain.models.Account
import com.example.common.domain.models.Category
import com.example.common.domain.models.Tag
import com.example.common.domain.models.Transaction
import com.example.feature_auth.presentation.screen.AuthorizationRouter
import com.example.feature_settings.presentation.SettingsRouter
import com.example.feature_settings.presentation.screen.singleAccount.SingleAccountFragment
import com.example.feature_settings.presentation.screen.singleCategory.SingleCategoryFragment
import com.example.feature_settings.presentation.screen.singleTag.SingleTagFragment
import com.example.feature_statistics_impl.presentation.StatisticsRouter
import com.example.feature_statistics_impl.presentation.screen.singleTransaction.SingleTransactionFragment
import com.example.wallette.R
import com.example.wallette.presentation.MainRouter

class Navigator : MainRouter, StatisticsRouter, AuthorizationRouter, SettingsRouter {

    private var navController: NavController? = null
    private var activity: AppCompatActivity? = null

    fun attachNavController(navController: NavController, activity: AppCompatActivity) {
        this.navController = navController
        this.activity = activity
    }

    override fun openTransactions() {
        navController?.navigate(R.id.transactionsFragment)
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
        navController?.navigate(
            resId = R.id.accountsFragment,
            args = null,
            navOptions = NavOptions.Builder()
                .setPopUpTo(
                    destinationId = R.id.settingsFragment,
                    inclusive = false
                )
                .build()
        )
    }

    override fun openCategories() {
        navController?.navigate(
            resId = R.id.categoriesFragment,
            args = null,
            navOptions = NavOptions.Builder()
                .setPopUpTo(
                    destinationId = R.id.settingsFragment,
                    inclusive = false
                )
                .build()
        )
    }

    override fun openTags() {
        navController?.navigate(
            resId = R.id.tagsFragment,
            args = null,
            navOptions = NavOptions.Builder()
                .setPopUpTo(
                    destinationId = R.id.settingsFragment,
                    inclusive = false
                )
                .build()
        )
    }

    override fun openSingleAccount(account: Account?) {
        navController?.navigate(
            resId = R.id.singleAccountFragment,
            args = Bundle().apply {
                putSerializable(SingleAccountFragment.ACCOUNT_KEY, account)
            },
            navOptions = NavOptions.Builder()
                .setLaunchSingleTop(true)
                .build()
        )
    }

    override fun openSingleCategory(category: Category?) {
        navController?.navigate(
            resId = R.id.singleCategoryFragment,
            args = Bundle().apply {
                putSerializable(SingleCategoryFragment.CATEGORY_KEY, category)
            },
            navOptions = NavOptions.Builder()
                .setLaunchSingleTop(true)
                .build()
        )
    }

    override fun openSingleTag(tag: Tag?) {
        navController?.navigate(
            resId = R.id.singleTagFragment,
            args = Bundle().apply {
                putSerializable(SingleTagFragment.TAG_KEY, tag)
            },
            navOptions = NavOptions.Builder()
                .setLaunchSingleTop(true)
                .build()
        )
    }

    override fun openSingleTransaction(transaction: Transaction?) {
        navController?.navigate(
            resId = R.id.singleTransactionFragment,
            args = Bundle().apply {
                putSerializable(SingleTransactionFragment.TRANSACTION_KEY, transaction)
            },
            navOptions = NavOptions.Builder()
                .setLaunchSingleTop(true)
                .build()
        )
    }

    override fun openTransferCreating() {
        //TODO("Not yet implemented")
    }
}