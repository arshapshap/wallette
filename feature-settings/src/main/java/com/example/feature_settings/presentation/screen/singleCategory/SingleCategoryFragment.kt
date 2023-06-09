package com.example.feature_settings.presentation.screen.singleCategory

import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.common.di.FeatureUtils
import com.example.common.domain.models.Category
import com.example.common.domain.models.enums.CategoryIcon
import com.example.common.domain.models.enums.TransactionType
import com.example.common.presentation.base.BaseFragment
import com.example.common.presentation.base.BaseViewModel
import com.example.common.presentation.extensions.*
import com.example.feature_settings.R
import com.example.feature_settings.databinding.FragmentSingleCategoryBinding
import com.example.feature_settings.di.SettingsComponent
import com.example.feature_settings.di.SettingsFeatureApi
import com.example.feature_settings.presentation.screen.common.IconsAdapter
import com.example.common.presentation.extensions.getColorPrimary
import com.example.common.presentation.floatingButtonInterfaces.FloatingButtonListenersManager
import com.example.common.presentation.floatingButtonInterfaces.OnFloatingButtonClickListener
import com.google.android.flexbox.*

class SingleCategoryFragment :
    BaseFragment<SingleCategoryViewModel>(R.layout.fragment_single_category), OnFloatingButtonClickListener {

    companion object {

        const val CATEGORY_KEY = "category_key"
    }

    private val binding by viewBinding(FragmentSingleCategoryBinding::bind)
    private val component: SettingsComponent by lazy {
        FeatureUtils.getFeature(this, SettingsFeatureApi::class.java)
    }

    override fun inject() {
        component.inject(this)
    }

    override fun onStart() {
        super.onStart()
        (requireActivity() as? FloatingButtonListenersManager)?.subscribeOnFloatingButtonClick(this)
    }

    override fun onStop() {
        super.onStop()
        (requireActivity() as? FloatingButtonListenersManager)?.setDefaultOnFloatingButtonClickListener()
    }

    @Suppress("DEPRECATION")
    override fun createViewModel(): BaseViewModel {
        return component.singleCategoryViewModel()
            .create(arguments?.getSerializable(CATEGORY_KEY) as? Category)
    }

    override fun initViews() {
        with (binding.categoryIconsRecyclerView) {
            adapter = IconsAdapter(colorSelected = getColorPrimary()) {
                viewModel.selectIcon(it as CategoryIcon)
            }
            layoutManager = getFlexboxLayoutManager()
        }

        binding.categoryTypeRadio.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.expensesRadioButton -> viewModel.selectType(TransactionType.Expense)
                R.id.incomesRadioButton -> viewModel.selectType(TransactionType.Income)
            }
        }

        binding.categoryNameEditText.doAfterTextChanged {
            viewModel.editName(it.toString())
        }
    }

    override fun subscribe() {
        viewModel.startLiveData.observe(viewLifecycleOwner) {
            with (binding) {
                categoryNameEditText.setText(it?.category?.name)
                getIconsAdapter()?.setList(it?.icons ?: listOf())

                it?.category?.type?.let {
                    when (it) {
                        TransactionType.Expense -> categoryTypeRadio.check(R.id.expensesRadioButton)
                        TransactionType.Income -> categoryTypeRadio.check(R.id.incomesRadioButton)
                        else -> return@let
                    }
                    expensesRadioButton.isEnabled = false
                    incomesRadioButton.isEnabled = false
                }

                if (it.canBeDeleted) {
                    deleteImageButton.isVisible = true
                    deleteImageButton.setOnClickListener {
                        viewModel.delete()
                    }
                }
            }
        }
        viewModel.editingCategoryLiveData.observe(viewLifecycleOwner) {
            with (binding) {
                val iconIndex = viewModel.startLiveData.value?.icons?.indexOf(it.icon)
                if (iconIndex != null) {
                    getIconsAdapter()?.setSelected(iconIndex)
                    categoryIconsRecyclerView.scrollToPosition(iconIndex)
                }
            }
        }
    }

    override fun onFloatingButtonClick() {
        viewModel.save()
    }

    private fun getIconsAdapter()
        = (binding.categoryIconsRecyclerView.adapter as? IconsAdapter)

    private fun getFlexboxLayoutManager(): FlexboxLayoutManager {
        return FlexboxLayoutManager(context).apply {
            justifyContent = JustifyContent.CENTER
            alignItems = AlignItems.CENTER
            flexDirection = FlexDirection.ROW
            flexWrap = FlexWrap.WRAP
        }
    }
}