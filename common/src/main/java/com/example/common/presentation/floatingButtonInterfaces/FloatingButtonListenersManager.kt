package com.example.common.presentation.floatingButtonInterfaces

interface FloatingButtonListenersManager {

    fun subscribeOnFloatingButtonClick(listener: OnFloatingButtonClickListener)

    fun setDefaultOnFloatingButtonClickListener()
}