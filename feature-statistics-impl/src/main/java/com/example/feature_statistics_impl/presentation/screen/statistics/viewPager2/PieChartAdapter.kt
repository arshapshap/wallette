package com.example.feature_statistics_impl.presentation.screen.statistics.viewPager2

import android.annotation.SuppressLint
import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.ColorInt
import androidx.core.text.toSpannable
import androidx.recyclerview.widget.RecyclerView
import com.example.common.domain.models.enums.Currency
import com.example.common.presentation.extensions.formatAsBalance
import com.example.feature_statistics_impl.databinding.ItemPieChartBinding
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry


class PieChartAdapter(
    private var dataSet: List<PieChartPeriod> = listOf(),
    private val currency: Currency,
    @ColorInt private val legendTextColor: Int,
    @ColorInt private val incomeTextColor: Int,
    @ColorInt private val expenseTextColor: Int
) : RecyclerView.Adapter<PieChartAdapter.PieChartViewHolder>() {

    @SuppressLint("NotifyDataSetChanged")
    fun setDataSet(data: List<PieChartPeriod>) {
        dataSet = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PieChartViewHolder {
        return PieChartViewHolder(
            binding = getBinding(parent),
            currency = currency,
            legendTextColor = legendTextColor,
            incomeTextColor = incomeTextColor,
            expenseTextColor = expenseTextColor
        )
    }

    override fun getItemCount(): Int = dataSet.size

    override fun onBindViewHolder(holder: PieChartViewHolder, position: Int) {
        holder.onBind(dataSet[position])
    }

    private fun getBinding(parent: ViewGroup): ItemPieChartBinding
            = ItemPieChartBinding.inflate(LayoutInflater.from(parent.context), parent, false)

    class PieChartViewHolder(
        private val binding: ItemPieChartBinding,
        private val currency: Currency,
        @ColorInt private val legendTextColor: Int,
        @ColorInt private val incomeTextColor: Int,
        @ColorInt private val expenseTextColor: Int
    ): RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: PieChartPeriod) {
            with (binding.pieChart) {
                this.data = getData(data.items)
                description.isEnabled = false
                setDrawEntryLabels(false)
                isRotationEnabled = false
                setHoleColor(Color.TRANSPARENT)
                legend.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
                legend.isWordWrapEnabled = true
                legend.textColor = legendTextColor
                legend.form = Legend.LegendForm.CIRCLE

                centerText = getCenterText(data.income, data.expense, currency)
                setCenterTextSize(20f)
            }
        }

        private fun getData(data: List<PieChartItem>): PieData {
            val pieEntries = data
                .map {
                    PieEntry(it.amount, it.name)
                }
            val dataSet = PieDataSet(pieEntries, "")
            dataSet.setDrawValues(false)
            dataSet.colors = data.map { it.color }
            return PieData(dataSet)
        }

        private fun getCenterText(income: Double, expense: Double, currency: Currency): Spannable {
            val builder = SpannableStringBuilder()

            val str1 = SpannableString(income.formatAsBalance(currency))
            str1.setSpan(ForegroundColorSpan(incomeTextColor), 0, str1.length, 0)
            builder.appendLine(str1)

            val str2 = SpannableString(expense.formatAsBalance(currency))
            str2.setSpan(ForegroundColorSpan(expenseTextColor), 0, str2.length, 0)
            builder.append(str2)

            return builder.toSpannable()
        }
    }
}