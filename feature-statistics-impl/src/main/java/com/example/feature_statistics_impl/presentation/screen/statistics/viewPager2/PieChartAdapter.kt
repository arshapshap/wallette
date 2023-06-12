package com.example.feature_statistics_impl.presentation.screen.statistics.viewPager2

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.feature_statistics_impl.databinding.ItemPieChartBinding
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry


class PieChartAdapter(
    private var dataSet: List<List<PieChartItem>> = listOf()
) : RecyclerView.Adapter<PieChartAdapter.PieChartViewHolder>() {

    @SuppressLint("NotifyDataSetChanged")
    fun setDataSet(data: List<List<PieChartItem>>) {
        dataSet = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PieChartViewHolder {
        return PieChartViewHolder(
            binding = getBinding(parent)
        )
    }

    override fun getItemCount(): Int = dataSet.size

    override fun onBindViewHolder(holder: PieChartViewHolder, position: Int) {
        holder.onBind(dataSet[position])
    }

    private fun getBinding(parent: ViewGroup): ItemPieChartBinding
            = ItemPieChartBinding.inflate(LayoutInflater.from(parent.context), parent, false)

    class PieChartViewHolder(
        private val binding: ItemPieChartBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: List<PieChartItem>) {
            with (binding.pieChart) {
                this.data = getData(data)
                description.isEnabled = false
                setDrawEntryLabels(false)
                isRotationEnabled = false
                setHoleColor(Color.TRANSPARENT)
                setTransparentCircleAlpha(0)
                legend.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
                legend.isWordWrapEnabled = true
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
    }
}