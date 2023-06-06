package com.example.core_network.di

import com.example.common.di.FeatureApiHolder
import com.example.common.di.FeatureContainer
import com.example.common.di.scopes.ApplicationScope
import javax.inject.Inject

@ApplicationScope
class NetworkHolder @Inject constructor(
    featureContainer: FeatureContainer
) : FeatureApiHolder(featureContainer) {

    override fun initializeDependencies(): Any {
        val networkDependencies = DaggerNetworkComponent_NetworkDependenciesComponent.builder()
            .commonApi(commonApi())
            .build()
        return DaggerNetworkComponent.builder()
            .networkDependencies(networkDependencies)
            .build()
    }
}