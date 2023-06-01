package com.example.feature_statistics_impl.presentation.screen.statistics

import com.example.common.presentation.base.BaseViewModel
import com.example.feature_statistics_impl.StatisticsRouter
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class StatisticsViewModel @AssistedInject constructor(
    private val router: StatisticsRouter
) : BaseViewModel() {

    fun openTransactions() {
        router.openTransactions()
    }

    @AssistedFactory
    interface Factory {

        fun create(): StatisticsViewModel
    }
}