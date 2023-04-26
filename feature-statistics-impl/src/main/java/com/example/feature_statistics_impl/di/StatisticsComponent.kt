package com.example.feature_statistics_impl.di

import com.example.feature_statistics_impl.presentation.StatisticsFragment
import com.example.feature_statistics_impl.presentation.StatisticsViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component
interface StatisticsComponent {

    @Component.Builder
    interface Builder {
        fun build(): StatisticsComponent
    }

    fun inject(statisticsFragment: StatisticsFragment)

    fun statisticsViewModel(): StatisticsViewModel.Factory
}