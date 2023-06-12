package com.example.feature_statistics_impl.presentation.screen.statistics

import android.graphics.Color
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.common.di.FeatureUtils
import com.example.common.domain.models.Transaction
import com.example.common.domain.models.enums.TransactionType
import com.example.common.presentation.base.BaseFragment
import com.example.common.presentation.base.BaseViewModel
import com.example.common.presentation.extensions.copy
import com.example.common.presentation.extensions.formatDayToString
import com.example.common.presentation.extensions.formatMonthToString
import com.example.common.presentation.extensions.formatYearToString
import com.example.feature_statistics_impl.R
import com.example.feature_statistics_impl.databinding.FragmentStatisticsBinding
import com.example.feature_statistics_impl.di.StatisticsComponent
import com.example.feature_statistics_impl.di.StatisticsFeatureApi
import com.example.feature_statistics_impl.presentation.screen.statistics.viewPager2.PieChartAdapter
import com.example.feature_statistics_impl.presentation.screen.statistics.viewPager2.PieChartItem
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayoutMediator
import java.util.*

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

            pieChartsViewPager2.adapter = PieChartAdapter()



            refreshLayout.setOnRefreshListener {
                viewModel.refresh()
            }
        }
    }

    override fun subscribe() {
        viewModel.dataLiveData.observe(viewLifecycleOwner) {
            TabLayoutMediator(binding.periodsTabLayout, binding.pieChartsViewPager2) { tab, position ->
                tab.text = getPeriodString(it[position].periodStart, it[position].periodEnd)
            }.attach()
            getPieChartAdapter().setDataSet(it.map { transactionsToPieChartItems(it.list) })
        }
        viewModel.openedPeriodIndexLiveData.observe(viewLifecycleOwner) {
            val tab = binding.periodsTabLayout.getTabAt(it)
            if (tab?.isSelected == false)
                tab.select()

            binding.periodsTabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    tab?.let { viewModel.saveOpenedPeriod(tab.position) }
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) { }

                override fun onTabReselected(tab: TabLayout.Tab?) {
                    viewModel.refresh()
                }
            })
        }
    }

    private fun transactionsToPieChartItems(transactions: List<Transaction>): List<PieChartItem> {
        var result = transactions.take(CATEGORIES_ON_CHART_NUMBER).map {
            PieChartItem(
                amount = it.amount.toFloat() * -1,
                name = getCategoryName(it),
                color = it.category?.icon?.colorInt
                    ?: Color.LTGRAY // TODO: ставить цвет по умолчанию (в зависимости от темы)
            )
        }
        if (transactions.count() > CATEGORIES_ON_CHART_NUMBER)
            result = result.plus(
                PieChartItem(
                    amount = transactions.drop(CATEGORIES_ON_CHART_NUMBER).sumOf { it.amount }.toFloat() * -1,
                    name = getString(R.string.other),
                    color = Color.GRAY // TODO: поставить цвет
                )
            )
        return result
    }

    private fun getCategoryName(transaction: Transaction): String {
        if (transaction.type != TransactionType.Transfer)
            return transaction.category?.name ?: getString(R.string.no_category)
        else
            return getString(R.string.transfer_category)
    }

    private fun getPieChartAdapter(): PieChartAdapter
        = binding.pieChartsViewPager2.adapter as PieChartAdapter

    private fun getPeriodString(start: Calendar?, end: Calendar?): String {
        if (start == null) return getString(com.example.common.R.string.all)

        if (end == null) return start.time.formatDayToString()

        if (checkIfCoversOneMonth(start, end)) return start.time.formatMonthToString()

        if (checkIfCoversOneYear(start, end)) return start.time.formatYearToString()

        val startString = start.time.formatDayToString()
        val endString = end.copy().apply {
            add(Calendar.DAY_OF_MONTH, -1)
        }.time.formatDayToString()

        return "$startString - $endString"
    }

    private fun checkIfCoversOneMonth(firstDate: Calendar, secondDate: Calendar): Boolean {
        val startYear = firstDate.get(Calendar.YEAR)
        val endYear = secondDate.get(Calendar.YEAR)
        val startMonth = firstDate.get(Calendar.MONTH)
        val endMonth = secondDate.get(Calendar.MONTH)
        val startDay = firstDate.get(Calendar.DAY_OF_MONTH)
        val endDay = secondDate.get(Calendar.DAY_OF_MONTH)

        return startYear == endYear && startMonth + 1 == endMonth &&
                startDay == 1 && endDay == 1
    }

    private fun checkIfCoversOneYear(firstDate: Calendar, secondDate: Calendar): Boolean {
        val startYear = firstDate.get(Calendar.YEAR)
        val endYear = secondDate.get(Calendar.YEAR)
        val startMonth = firstDate.get(Calendar.MONTH)
        val endMonth = secondDate.get(Calendar.MONTH)
        val startDay = firstDate.get(Calendar.DAY_OF_MONTH)
        val endDay = secondDate.get(Calendar.DAY_OF_MONTH)

        return startYear + 1 == endYear && startMonth == Calendar.JANUARY && endMonth == Calendar.JANUARY &&
                startDay == 1 && endDay == 1
    }

    companion object {
        private const val CATEGORIES_ON_CHART_NUMBER = 10
    }
}