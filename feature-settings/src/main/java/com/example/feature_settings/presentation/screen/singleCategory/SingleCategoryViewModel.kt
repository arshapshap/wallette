package com.example.feature_settings.presentation.screen.singleCategory

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.common.domain.models.Category
import com.example.common.domain.models.enums.CategoryIcon
import com.example.common.domain.models.enums.TransactionType
import com.example.common.presentation.base.BaseViewModel
import com.example.feature_settings.domain.SettingsCategoriesInteractor
import com.example.feature_settings.presentation.SettingsRouter
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch

class SingleCategoryViewModel @AssistedInject constructor(
    @Assisted private val category: Category?,
    private val interactor: SettingsCategoriesInteractor,
    private val router: SettingsRouter
) : BaseViewModel() {

    private val _startLiveData = MutableLiveData<StartData>()
    val startLiveData: LiveData<StartData>
        get() = _startLiveData

    private val _editingCategoryLiveData = MutableLiveData<EditingCategory>()
    val editingCategoryLiveData: LiveData<EditingCategory>
        get() = _editingCategoryLiveData

    private val isDataValid: Boolean
        get() {
            val editingCategory = _editingCategoryLiveData.value ?: return false
            return editingCategory.name != ""
                    && editingCategory.type != null
                    && editingCategory.icon != CategoryIcon.Empty
        }

    init {
        val editingCategory = if (category == null) EditingCategory()
            else EditingCategory(
                id = category.id,
                name = category.name,
                icon = category.icon,
                type = category.type
            )
        viewModelScope.launch {
            _startLiveData.postValue(
                StartData(
                    category = category,
                    icons = CategoryIcon.values().filter { it.name != "Empty" },
                    canBeDeleted = category != null
                            && interactor.getCategories().filter { it.type == category.type }.size > 1
                )
            )
            _editingCategoryLiveData.postValue(editingCategory)
        }
    }

    fun save() {
        if (!isDataValid) return

        val editingCategory = _editingCategoryLiveData.value!!
        val newCategory = Category(
            id = category?.id ?: 0,
            name = editingCategory.name,
            icon = editingCategory.icon,
            type = editingCategory.type!!
        )
        viewModelScope.launch {
            if (category == null)
                interactor.createCategory(newCategory)
            else
                interactor.editCategory(newCategory)
        }
        router.close()
    }

    fun delete() {
        if  (category == null) return
        viewModelScope.launch {
            interactor.deleteCategory(category)
        }
        router.close()
    }

    fun selectType(type: TransactionType) {
        val category = _editingCategoryLiveData.value?.copy(type = type) ?: return
        updateCategory(category)
    }

    fun selectIcon(icon: CategoryIcon) {
        val category = _editingCategoryLiveData.value?.copy(icon = icon) ?: return
        updateCategory(category)
    }

    fun editName(name: String) {
        val category = _editingCategoryLiveData.value?.copy(name = name) ?: return
        updateCategory(category)
    }

    private fun updateCategory(category: EditingCategory) {
        _editingCategoryLiveData.value = category
    }

    @AssistedFactory
    interface Factory {

        fun create(@Assisted category: Category?): SingleCategoryViewModel
    }

    data class StartData(
        val category: Category?,
        val icons: List<CategoryIcon>,
        val canBeDeleted: Boolean
    )
}