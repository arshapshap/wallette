package com.example.wallette

import android.app.Application
import com.example.common.di.ComponentDependenciesProvider
import com.example.common.di.FeatureContainer
import com.example.common.di.HasComponentDependencies
import com.example.wallette.di.app.AppComponent
import com.example.wallette.di.app.FeatureHolderManager
import javax.inject.Inject

class App : Application(), FeatureContainer, HasComponentDependencies {

    @Inject
    lateinit var featureHolderManager: FeatureHolderManager

    @Inject
    override lateinit var dependencies: ComponentDependenciesProvider

    private lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = AppComponent.init(this)
        appComponent.inject(this)
    }

    override fun <T> getFeature(key: Class<*>): T {
        return featureHolderManager.getFeature<T>(key)!!
    }

    override fun releaseFeature(key: Class<*>) {
        featureHolderManager.releaseFeature(key)
    }
}