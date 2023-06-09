package com.example.feature_settings.presentation.screen.singleTag

import android.graphics.Color
import androidx.annotation.ColorInt
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.common.domain.models.Tag
import com.example.common.presentation.base.BaseViewModel
import com.example.feature_settings.domain.SettingsInteractor
import com.example.feature_settings.presentation.SettingsRouter
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch

class SingleTagViewModel @AssistedInject constructor(
    @Assisted private val tag: Tag?,
    private val interactor: SettingsInteractor,
    private val router: SettingsRouter
) : BaseViewModel() {

    private val _startTagLiveData = MutableLiveData<Tag?>(tag)
    val startTagLiveData: LiveData<Tag?>
        get() = _startTagLiveData

    private val _editingTagLiveData = MutableLiveData<EditingTag>()
    val editingTagLiveData: LiveData<EditingTag>
        get() = _editingTagLiveData

    private val isDataValid: Boolean
        get() {
            val editingTag = _editingTagLiveData.value ?: return false
            return editingTag.name != ""
                    && editingTag.color != null
        }

    init {
        val editingTag = if (tag == null) EditingTag()
            else EditingTag(
                id = tag.id,
                name = tag.name,
                color = Color.parseColor(tag.color)
            )
        _editingTagLiveData.value = editingTag
    }

    fun save() {
        if (!isDataValid)
            return

        val editingTag = _editingTagLiveData.value!!
        val newTag = Tag(
            id = tag?.id ?: 0,
            name = editingTag.name,
            color = convertColorIntToHexString(editingTag.color!!)
        )
        viewModelScope.launch {
            if (tag == null)
                interactor.createTag(newTag)
            else
                interactor.editTag(newTag)
        }
        router.close()
    }

    fun delete() {
        if  (tag == null) return
        viewModelScope.launch {
            interactor.deleteTag(tag)
        }
        router.close()
    }

    fun selectColor(@ColorInt color: Int) {
        val tag = _editingTagLiveData.value?.copy(color = color) ?: return
        updateTag(tag)
    }

    fun editName(name: String) {
        val tag = _editingTagLiveData.value?.copy(name = name) ?: return
        updateTag(tag)
    }

    private fun updateTag(tag: EditingTag) {
        _editingTagLiveData.value = tag
    }

    private fun convertColorIntToHexString(@ColorInt color: Int): String {
        return "#" + Integer.toHexString(color and 0x00ffffff)
    }

    @AssistedFactory
    interface Factory {

        fun create(@Assisted tag: Tag?): SingleTagViewModel
    }
}