package com.example.common.presentation.liveEvent

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer

open class LiveEvent<T>: MediatorLiveData<LiveEvent.Once<T>>() {

    // based on the code from https://github.com/ueen/LiveEvent

    fun observe(owner: LifecycleOwner, onEvent: (T) -> Unit) {
        this.value = null
        this.observe(owner, Observer {
            if (it != null && it.receive(onEvent.hashCode())) {
                onEvent(it.message!!)
            }
        })
    }

    class Once<T> (val message: T? = null) {
        private val received = mutableListOf<Int>()
        fun receive(hash: Int): Boolean {
            if (!received.contains(hash)) {
                received.add(hash)
                return true
            }
            return false
        }
    }
}