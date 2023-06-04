package com.example.feature_statistics_impl.presentation.screen.singleTransaction

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.view.isGone
import androidx.core.widget.doAfterTextChanged
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.common.di.FeatureUtils
import com.example.common.domain.models.Transaction
import com.example.common.domain.models.TransactionType
import com.example.common.presentation.base.BaseFragment
import com.example.common.presentation.base.BaseViewModel
import com.example.common.presentation.dialogs.DatePickerFragment.Companion.showDatePickerDialog
import com.example.common.presentation.dialogs.DatePickerFragment.OnSelectDateListener
import com.example.common.presentation.extensions.*
import com.example.feature_statistics_impl.R
import com.example.feature_statistics_impl.databinding.FragmentSingleTransactionBinding
import com.example.feature_statistics_impl.di.StatisticsComponent
import com.example.feature_statistics_impl.di.StatisticsFeatureApi
import com.example.common.presentation.dialogs.PickerFragment.Companion.showPickerDialog
import com.example.common.presentation.dialogs.PickerFragment.OnSelectListener
import com.example.common.presentation.floatingButtonInterfaces.FloatingButtonListenersManager
import com.example.common.presentation.floatingButtonInterfaces.OnFloatingButtonClickListener
import com.example.feature_statistics_impl.presentation.screen.singleTransaction.tagsRecyclerView.TagsAdapter
import com.google.android.flexbox.*
import java.util.*
import kotlin.math.absoluteValue

class SingleTransactionFragment : BaseFragment<SingleTransactionViewModel>(R.layout.fragment_single_transaction),
    OnSelectListener, OnSelectDateListener, OnFloatingButtonClickListener {

    companion object {

        const val TRANSACTION_KEY = "transaction_key"
        private const val ACCOUNT_PICKER_TAG = "account"
        private const val ACCOUNT_DESTINATION_PICKER_TAG = "account_destination"
        private const val CATEGORY_PICKER_TAG = "category"
        private const val DATE_PICKER_TAG = "date"
    }

    private val binding by viewBinding(FragmentSingleTransactionBinding::bind)
    private val component: StatisticsComponent by lazy {
        FeatureUtils.getFeature(this, StatisticsFeatureApi::class.java)
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
        return component.singleTransactionViewModel()
            .create(arguments?.getSerializable(TRANSACTION_KEY) as? Transaction)
    }

    override fun initViews() {
        with (binding.selectCategoryButton) {
            setStrokeVisibility(false)
            setRightArrowVisible(true)
            setImage(com.example.common.R.drawable.ic_category)
            setTitle(R.string.category)
        }

        with (binding.selectAccountDestinationButton) {
            setStrokeVisibility(false)
            setRightArrowVisible(true)
            setImage(com.example.common.R.drawable.ic_account)
            setTitle(R.string.account_destination)
        }

        with (binding.selectAccountButton) {
            setStrokeVisibility(false)
            setRightArrowVisible(true)
            setImage(com.example.common.R.drawable.ic_account)
            setTitle(R.string.account)
        }

        with (binding.selectDateButton) {
            setStrokeVisibility(false)
            setRightArrowVisible(true)
            setImage(com.example.common.R.drawable.ic_date)
            setTitle(R.string.date)
        }

        with (binding.tagsRecyclerView) {
            adapter = TagsAdapter {
                viewModel.removeTag(it)
            }
            layoutManager = getFlexboxLayoutManager()
        }

        with (binding.tagsAutoCompleteTextView) {
            val adapter = TagsAutocompleteAdapter(requireContext(), listOf())
            setAdapter(adapter)
            setOnItemClickListener { _, _, position, _ ->
                viewModel.addTag(position)
                setText("")
                hideKeyboard(this)
            }
        }

        with (binding) {
            amountEditText.doAfterTextChanged {
                viewModel.editAmount(it.toString())
            }
            descriptionEditText.doAfterTextChanged {
                viewModel.editDescription(it.toString())
            }
        }
    }

    override fun subscribe() {
        viewModel.transactionLiveData.observe(viewLifecycleOwner) {
            with (binding) {
                it?.let {
                    incomeTextView.isGone = it.type != TransactionType.Income
                    expenseTextView.isGone = it.type != TransactionType.Expense
                    transferTextView.isGone = it.type != TransactionType.Transfer

                    incomeTextView.setOnClickListener { viewModel.selectType(TransactionType.Income) }
                    expenseTextView.setOnClickListener { viewModel.selectType(TransactionType.Expense) }
                    transferTextView.setOnClickListener { viewModel.selectType(TransactionType.Transfer) }

                    getTagsAdapter()?.setList(it.tags)
                    amountEditText.setText(it.amount.absoluteValue.toString())
                    descriptionEditText.setText(it.description)
                } ?: run {
                    expenseTextView.alpha = 1f
                    incomeTextView.setOnClickListener {
                        viewModel.selectType(TransactionType.Income)
                    }
                    expenseTextView.setOnClickListener {
                        viewModel.selectType(TransactionType.Expense)
                    }
                    transferTextView.setOnClickListener {
                        viewModel.selectType(TransactionType.Transfer)
                    }
                }
            }
        }
        viewModel.onTagsRequestedLiveEvent.observe(viewLifecycleOwner) {
            getTagsAutocompleteAdapter()?.updateSuggestions(it)
        }
        viewModel.onTagRemovedLiveEvent.observe(viewLifecycleOwner) {
            getTagsAdapter()?.removeItem(it)
        }
        viewModel.onTagAddedLiveEvent.observe(viewLifecycleOwner) {
            getTagsAdapter()?.addItem(it)
        }
        viewModel.editingTransactionLiveData.observe(viewLifecycleOwner) {
            with (binding) {
                incomeTextView.alpha = if (it.type == TransactionType.Income) 1f else 0.5f
                expenseTextView.alpha = if (it.type == TransactionType.Expense) 1f else 0.5f
                transferTextView.alpha = if (it.type == TransactionType.Transfer) 1f else 0.5f

                with (selectAccountButton) {
                    it.account?.let {
                        setValue(it.name)
                        setImage(it.icon.drawableRes)
                    }
                    setTitle(if (it.type != TransactionType.Transfer) R.string.account else R.string.account_source)
                    setOnClickListener {
                        viewModel.getAccounts()
                    }
                }

                with(selectDateButton) {
                    setValue(it.date.formatToString())
                    setOnClickListener {
                        showDatePickerDialog(
                            fragmentManager = childFragmentManager,
                            calendar = it.date.toCalendar(),
                            tag = DATE_PICKER_TAG
                        )
                    }
                }

                with(selectCategoryButton) {
                    setGone(it.type == TransactionType.Transfer)
                    it.category?.let {
                        setValue(it.name)
                        setImage(it.icon.drawableRes)
                    } ?: run {
                        setValue("")
                        setImage(com.example.common.R.drawable.ic_category)
                    }
                    setOnClickListener {
                        viewModel.getCategories()
                    }
                }

                with (selectAccountDestinationButton) {
                    setGone(it.type != TransactionType.Transfer)
                    it.accountDestination?.let {
                        setValue(it.name)
                        setImage(it.icon.drawableRes)
                    }
                    setOnClickListener {
                        viewModel.getAccountsToTransfer()
                    }
                }
            }
        }
        viewModel.onAccountsRequestedLiveEvent.observe(viewLifecycleOwner) {
            showPickerDialog(
                fragmentManager = childFragmentManager,
                title = getString(R.string.account),
                items = it.map { it.name }.toTypedArray(),
                tag = ACCOUNT_PICKER_TAG
            )
        }
        viewModel.onAccountsToTransferRequestedLiveEvent.observe(viewLifecycleOwner) {
            showPickerDialog(
                fragmentManager = childFragmentManager,
                title = getString(R.string.account),
                items = it.map { it.name }.toTypedArray(),
                tag = ACCOUNT_DESTINATION_PICKER_TAG
            )
        }
        viewModel.onCategoriesRequestedLiveEvent.observe(viewLifecycleOwner) {
            showPickerDialog(
                fragmentManager = childFragmentManager,
                title = getString(R.string.category),
                items = it.map { it.name }.toTypedArray(),
                tag = CATEGORY_PICKER_TAG
            )
        }
    }

    override fun onFloatingButtonClick() {
        viewModel.save()
    }

    override fun onSelect(tag: String?, index: Int) {
        when (tag) {
            ACCOUNT_PICKER_TAG -> onAccountSelected(index)
            ACCOUNT_DESTINATION_PICKER_TAG -> onAccountDestinationSelected(index)
            CATEGORY_PICKER_TAG -> onCategorySelected(index)
            else -> return
        }
    }

    override fun onSelect(tag: String?, year: Int, month: Int, day: Int) {
        if (tag == DATE_PICKER_TAG) {
            onDateSelected(year, month, day)
        }
    }

    private fun onDateSelected(year: Int, month: Int, day: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, day)
        val date: Date = calendar.time
        viewModel.selectDate(date)
    }

    private fun onAccountSelected(index: Int) {
        val account = viewModel.onAccountsRequestedLiveEvent.value!!.message!![index]
        viewModel.selectAccount(account)
    }

    private fun onAccountDestinationSelected(index: Int) {
        val account = viewModel.onAccountsToTransferRequestedLiveEvent.value!!.message!![index]
        viewModel.selectAccountDestination(account)
    }

    private fun onCategorySelected(index: Int) {
        val category = viewModel.onCategoriesRequestedLiveEvent.value!!.message!![index]
        viewModel.selectCategory(category)
    }

    private fun getTagsAdapter(): TagsAdapter? {
        return binding.tagsRecyclerView.adapter as? TagsAdapter
    }

    private fun getTagsAutocompleteAdapter(): TagsAutocompleteAdapter? {
        return binding.tagsAutoCompleteTextView.adapter as? TagsAutocompleteAdapter
    }

    private fun getFlexboxLayoutManager(): FlexboxLayoutManager {
        return FlexboxLayoutManager(context).apply {
            justifyContent = JustifyContent.FLEX_START
            alignItems = AlignItems.FLEX_START
            flexDirection = FlexDirection.ROW
            flexWrap = FlexWrap.WRAP
        }
    }

    private fun hideKeyboard(view: View) {
        val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun Date.toCalendar(): Calendar {
        val calendar = Calendar.getInstance()
        calendar.time = this
        return calendar
    }
}