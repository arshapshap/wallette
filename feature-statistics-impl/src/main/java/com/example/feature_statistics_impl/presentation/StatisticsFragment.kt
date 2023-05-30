package com.example.feature_statistics_impl.presentation

import android.widget.Toast
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.common.presentation.base.BaseFragment
import com.example.common.presentation.base.BaseViewModel
import com.example.common.di.FeatureUtils
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
                Toast.makeText(context, "Balance button clicked", Toast.LENGTH_SHORT).show()
            }
            balanceButtonLayout.changeViewButton.setOnClickListener {
                Toast.makeText(context, "Change view button clicked", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun subscribe() {
        //TODO("Not yet implemented")
    }
}