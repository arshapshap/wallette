package com.example.feature_statistics_impl.di

import com.example.common.di.FeatureApiHolder
import com.example.common.di.FeatureContainer
import com.example.common.di.scopes.ApplicationScope
import com.example.di.DataApi
import com.example.feature_statistics_impl.presentation.StatisticsRouter
import javax.inject.Inject

@ApplicationScope
class StatisticsFeatureHolder @Inject constructor(
    featureContainer: FeatureContainer,
    private val statisticsRouter: StatisticsRouter
) : FeatureApiHolder(featureContainer) {

    override fun initializeDependencies(): Any {
        val statisticsDependencies = DaggerStatisticsComponent_StatisticsDependenciesComponent.builder()
            .dataApi(getFeature(DataApi::class.java))
            .build()
        return DaggerStatisticsComponent.builder()
            .router(statisticsRouter)
            .withDependencies(statisticsDependencies)
            .build()
    }
}