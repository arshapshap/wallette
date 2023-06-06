package com.example.feature_statistics_impl.presentation.screen.statistics

import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.common.di.FeatureUtils
import com.example.common.presentation.base.BaseFragment
import com.example.common.presentation.base.BaseViewModel
import com.example.feature_statistics_impl.R
import com.example.feature_statistics_impl.databinding.FragmentStatisticsBinding
import com.example.feature_statistics_impl.di.StatisticsComponent
import com.example.feature_statistics_impl.di.StatisticsFeatureApi

class StatisticsFragment : BaseFragment<StatisticsViewModel>(R.layout.fragment_statistics) {

    private val binding by viewBinding(FragmentStatisticsBinding::bind)
    private val statisticsComponent: StatisticsComponent by lazy {
        FeatureUtils.getFeature(this, StatisticsFeatureApi::class.java)
    }

    override fun inject() {
        statisticsComponent.inject(this)
    }

    override fun createViewModel(): BaseViewModel =
        statisticsComponent.statisticsViewModel().create()

    override fun initViews() {
        with(binding) {
            balanceButtonLayout.balanceButton.setOnClickListener {
                viewModel.openTransactions()
            }

            balanceButtonLayout.arrowUpButton.setOnClickListener {
                viewModel.openTransactions()
            }

            balanceButtonLayout.changeViewButton.setOnClickListener {
            }
        }
    }

    override fun subscribe() {
    }
}