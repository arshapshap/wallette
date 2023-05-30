package com.example.feature_statistics_impl.di

import com.example.common.di.scopes.StatisticsScope
import com.example.feature_statistics_impl.StatisticsRouter
import com.example.feature_statistics_impl.presentation.StatisticsFragment
import com.example.feature_statistics_impl.presentation.StatisticsViewModel
import dagger.BindsInstance
import dagger.Component

@StatisticsScope
@Component
interface StatisticsComponent : StatisticsFeatureApi {

    @Component.Builder
    interface Builder {
        fun build(): StatisticsComponent

        @BindsInstance
        fun router(router: StatisticsRouter): Builder
    }



    fun inject(statisticsFragment: StatisticsFragment)

    fun statisticsViewModel(): StatisticsViewModel.Factory
}