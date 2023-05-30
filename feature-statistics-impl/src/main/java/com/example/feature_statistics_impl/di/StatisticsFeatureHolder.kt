package com.example.feature_statistics_impl.di

import com.example.common.di.FeatureApiHolder
import com.example.common.di.FeatureContainer
import com.example.common.di.scopes.ApplicationScope
import com.example.feature_statistics_impl.StatisticsRouter
import javax.inject.Inject

@ApplicationScope
class StatisticsFeatureHolder @Inject constructor(
    featureContainer: FeatureContainer,
    private val statisticsRouter: StatisticsRouter
) : FeatureApiHolder(featureContainer) {

    override fun initializeDependencies(): Any {
//        val userFeatureDependencies = DaggerStatisticsFeatureComponent_StatisticsFeatureDependenciesComponent.builder()
//            .build()
        return DaggerStatisticsComponent.builder()
            //.withDependencies(userFeatureDependencies)
            .router(statisticsRouter)
            .build()
    }
}