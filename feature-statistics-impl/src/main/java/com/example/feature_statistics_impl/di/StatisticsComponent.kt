package com.example.feature_statistics_impl.di

import com.example.common.di.scopes.StatisticsScope
import com.example.feature_statistics_impl.data.di.DataModule
import com.example.feature_statistics_impl.StatisticsRouter
import com.example.feature_statistics_impl.presentation.screen.statistics.StatisticsFragment
import com.example.feature_statistics_impl.presentation.screen.statistics.StatisticsViewModel
import com.example.feature_statistics_impl.presentation.screen.transactionsList.TransactionsFragment
import com.example.feature_statistics_impl.presentation.screen.transactionsList.TransactionsViewModel
import dagger.BindsInstance
import dagger.Component

@StatisticsScope
@Component(
    modules = [DataModule::class, StatisticsBindsModule::class]
)
interface StatisticsComponent : StatisticsFeatureApi {

    @Component.Builder
    interface Builder {
        fun build(): StatisticsComponent

        @BindsInstance
        fun router(router: StatisticsRouter): Builder
    }

    fun inject(statisticsFragment: StatisticsFragment)

    fun statisticsViewModel(): StatisticsViewModel.Factory

    fun inject(transactionsFragment: TransactionsFragment)

    fun transactionsViewModel(): TransactionsViewModel.Factory
}