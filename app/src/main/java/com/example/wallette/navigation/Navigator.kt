package com.example.wallette.navigation

import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import com.example.feature_statistics_impl.StatisticsRouter

class Navigator() : StatisticsRouter {

    private var navController: NavController? = null
    private var activity: AppCompatActivity? = null

    fun attachNavController(navController: NavController, activity: AppCompatActivity) {
        this.navController = navController
        this.activity = activity
    }

    override fun openTransactions() {
        //TODO("Not yet implemented")
    }
}