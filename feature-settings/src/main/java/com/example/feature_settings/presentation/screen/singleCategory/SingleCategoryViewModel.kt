package com.example.feature_settings.presentation.screen.singleCategory

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.common.domain.models.*
import com.example.common.presentation.base.BaseViewModel
import com.example.feature_settings.domain.SettingsInteractor
import com.example.feature_settings.presentation.SettingsRouter
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch

class SingleCategoryViewModel @AssistedInject constructor(
    @Assisted private val category: Category?,
    private val interactor: SettingsInteractor,
    private val router: SettingsRouter
) : BaseViewModel() {

    private val _stateLiveData = MutableLiveData<Data?>()
    val stateLiveData: LiveData<Data?>
        get() = _stateLiveData

    init {
        val editingCategory = if (category == null) EditingCategory()
            else EditingCategory(
                id = category.id,
                name = category.name,
                icon = category.icon,
                type = category.type
            )
        _stateLiveData.postValue(
            Data(
                category = editingCategory,
                icons = CategoryIcon.values().filter { it.name != "Empty" }
            )
        )
    }

    fun save() {
        val editingCategory = _stateLiveData.value?.category ?: return // TODO: выдавать ошибку?
        viewModelScope.launch {
            if (category == null)
                interactor.createCategory(
                    Category(
                        id = "",
                        name = editingCategory.name,
                        icon = editingCategory.icon,
                        type = editingCategory.type ?: return@launch
                    )
                )
            else
                interactor.editCategory(
                    Category(
                        id = category.id,
                        name = editingCategory.name,
                        icon = editingCategory.icon,
                        type = editingCategory.type ?: return@launch
                    )
                )
            router.openCategories()
        }
    }

    fun selectType(type: TransactionType) {
        val category = _stateLiveData.value?.category?.copy(
            type = type
        ) ?: return
        _stateLiveData.postValue(
            _stateLiveData.value?.copy(
                category = category
            )
        )
    }

    fun selectIcon(icon: CategoryIcon) {
        val category = _stateLiveData.value?.category?.copy(
           icon = icon
        ) ?: return
        _stateLiveData.postValue(
            _stateLiveData.value?.copy(
                category = category
            )
        )
    }

    @AssistedFactory
    interface Factory {

        fun create(@Assisted category: Category?): SingleCategoryViewModel
    }

    data class Data(
        val category: EditingCategory,
        val icons: List<CategoryIcon>
    )
}