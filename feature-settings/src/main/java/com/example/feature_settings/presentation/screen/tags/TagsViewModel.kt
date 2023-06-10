package com.example.feature_settings.presentation.screen.tags

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.common.domain.models.Tag
import com.example.common.presentation.base.BaseViewModel
import com.example.feature_settings.domain.SettingsTagsInteractor
import com.example.feature_settings.presentation.SettingsRouter
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch

class TagsViewModel @AssistedInject constructor(
    private val interactor: SettingsTagsInteractor,
    private val router: SettingsRouter
) : BaseViewModel() {

    private val _listLiveData = MutableLiveData<List<Tag>>(listOf())
    val listLiveData: LiveData<List<Tag>>
        get() = _listLiveData

    init {
        viewModelScope.launch {
            val list = interactor.getTags()
            _listLiveData.postValue(list)
        }
    }

    fun openTagCreating() {
        router.openSingleTag()
    }

    fun openTag(tag: Tag) {
        router.openSingleTag(tag)
    }

    @AssistedFactory
    interface Factory {

        fun create(): TagsViewModel
    }
}