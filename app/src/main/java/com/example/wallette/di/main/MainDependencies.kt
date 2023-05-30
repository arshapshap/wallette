package com.example.wallette.di.main

import com.example.common.data.TokenManager
import com.example.common.di.ComponentDependencies
import com.example.wallette.navigation.Navigator

interface MainDependencies : ComponentDependencies {

    fun navigator(): Navigator

    fun tokenManager(): TokenManager
}