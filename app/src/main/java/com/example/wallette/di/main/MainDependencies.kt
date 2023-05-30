package com.example.wallette.di.main

import com.example.common.di.ComponentDependencies
import com.example.wallette.navigation.Navigator

interface MainDependencies : ComponentDependencies {
    fun navigator(): Navigator
}