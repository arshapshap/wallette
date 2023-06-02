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

    private val _stateLiveData = MutableLiveData<Data?>()
    val stateLiveData: LiveData<Data?>
        get() = _stateLiveData

    private val isDataValid: Boolean
        get() {
            val editingTag = _stateLiveData.value?.tag ?: return false
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
        _stateLiveData.postValue(
            Data(
                tag = editingTag
            )
        )
    }

    fun save(name: String) {
        editName(name)

        if (!isDataValid)
            return

        val editingTag = _stateLiveData.value?.tag!!
        viewModelScope.launch {
            if (tag == null)
                interactor.createTag(
                    Tag(
                        id = "",
                        name = editingTag.name,
                        color = convertColorIntToHexString(editingTag.color!!)
                    )
                )
            else
                interactor.editTag(
                    Tag(
                        id = tag.id,
                        name = editingTag.name,
                        color = convertColorIntToHexString(editingTag.color!!)
                    )
                )
            router.openTags()
        }
    }

    fun selectColor(@ColorInt color: Int) {
        val tag = _stateLiveData.value?.tag?.copy(
            color = color
        ) ?: return
        updateTag(tag)
    }

    private fun editName(name: String) {
        val tag = _stateLiveData.value?.tag?.copy(
            name = name
        ) ?: return
        updateTag(tag)
    }

    private fun updateTag(tag: EditingTag) {
        _stateLiveData.value = _stateLiveData.value?.copy(tag = tag)
    }

    private fun convertColorIntToHexString(@ColorInt color: Int): String {
        return "#" + Integer.toHexString(color and 0x00ffffff)
    }

    @AssistedFactory
    interface Factory {

        fun create(@Assisted tag: Tag?): SingleTagViewModel
    }

    data class Data(
        val tag: EditingTag
    )
}