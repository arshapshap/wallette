package com.example.feature_settings.presentation.screen.categories

import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.common.di.FeatureUtils
import com.example.common.presentation.base.BaseFragment
import com.example.common.presentation.base.BaseViewModel
import com.example.feature_settings.R
import com.example.feature_settings.databinding.FragmentCategoriesBinding
import com.example.feature_settings.di.SettingsComponent
import com.example.feature_settings.di.SettingsFeatureApi
import com.example.feature_settings.presentation.screen.categories.recyclerView.CategoriesAdapter
import com.example.feature_settings.presentation.utils.getColorPrimary
import com.example.feature_settings.presentation.utils.setContent

class CategoriesFragment : BaseFragment<CategoriesViewModel>(R.layout.fragment_categories) {

    private val binding by viewBinding(FragmentCategoriesBinding::bind)
    private val component: SettingsComponent by lazy {
        FeatureUtils.getFeature(this, SettingsFeatureApi::class.java)
    }

    override fun inject() {
        component.inject(this)
    }

    override fun createViewModel(): BaseViewModel {
        return component.categoriesViewModel().create()
    }

    override fun initViews() {
        with (binding) {
            addCategoryLayout.setContent(
                iconRes = R.drawable.ic_plus_box,
                titleRes = R.string.add_category,
                colorInt = getColorPrimary()
            ) {
                viewModel.openCategoryCreating()
            }

            listIncomesRecyclerView.adapter = CategoriesAdapter {
                viewModel.openCategory(it)
            }
            listExpensesRecyclerView.adapter = CategoriesAdapter {
                viewModel.openCategory(it)
            }
        }
    }

    override fun subscribe() {
        viewModel.listIncomesLiveData.observe(viewLifecycleOwner) {
            (binding.listIncomesRecyclerView.adapter as CategoriesAdapter).setList(it)
        }
        viewModel.listExpensesLiveData.observe(viewLifecycleOwner) {
            (binding.listExpensesRecyclerView.adapter as CategoriesAdapter).setList(it)
        }
    }
}