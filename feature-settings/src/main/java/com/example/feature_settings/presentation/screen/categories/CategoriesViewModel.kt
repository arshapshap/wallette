package com.example.feature_settings.presentation.screen.categories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.common.domain.models.Category
import com.example.common.domain.models.TransactionType
import com.example.common.presentation.base.BaseViewModel
import com.example.feature_settings.domain.SettingsInteractor
import com.example.feature_settings.presentation.SettingsRouter
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch

class CategoriesViewModel @AssistedInject constructor(
    private val interactor: SettingsInteractor,
    private val router: SettingsRouter
) : BaseViewModel() {

    private val _listIncomesLiveData = MutableLiveData<List<Category>>(listOf())
    val listIncomesLiveData: LiveData<List<Category>>
        get() = _listIncomesLiveData

    private val _listExpensesLiveData = MutableLiveData<List<Category>>(listOf())
    val listExpensesLiveData: LiveData<List<Category>>
        get() = _listExpensesLiveData

    init {
        viewModelScope.launch {
            val list = interactor.getCategories()
            _listIncomesLiveData.postValue(list.filter { it.type == TransactionType.Income })
            _listExpensesLiveData.postValue(list.filter { it.type == TransactionType.Expense })
        }
    }

    fun openCategoryCreating() {
        router.openSingleCategory()
    }

    fun openCategory(category: Category) {
        router.openSingleCategory(category)
    }

    @AssistedFactory
    interface Factory {

        fun create(): CategoriesViewModel
    }
}