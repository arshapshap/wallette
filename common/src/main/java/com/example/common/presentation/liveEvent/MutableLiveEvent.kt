package com.example.common.presentation.liveEvent

import android.os.Looper

class MutableLiveEvent<T> : LiveEvent<T>() {

    // based on the code from https://github.com/ueen/LiveEvent

    fun post(value: T? = null) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            this.setValue(Once(value))
        } else {
            this.postValue(Once(value))
        }
    }
}