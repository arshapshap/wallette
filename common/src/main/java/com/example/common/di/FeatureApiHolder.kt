package com.example.common.di

import java.util.concurrent.locks.ReentrantLock

abstract class FeatureApiHolder(
    private val mFeatureContainer: FeatureContainer
) {
    private val mFeatureLocker = ReentrantLock()

    private var mFeatureApi: Any? = null

    @Suppress("UNCHECKED_CAST")
    fun <T> getFeatureApi(): T {
        mFeatureLocker.lock()
        if (mFeatureApi == null) {
            mFeatureApi = initializeDependencies()
        }
        mFeatureLocker.unlock()
        return mFeatureApi as T
    }

    fun releaseFeatureApi() {
        mFeatureLocker.lock()
        mFeatureApi = null
        destroyDependencies()
        mFeatureLocker.unlock()
    }

    fun commonApi(): CommonApi {
        return mFeatureContainer.commonApi()
    }

    protected fun <T> getFeature(key: Class<T>): T {
        return mFeatureContainer.getFeature<T>(key) ?: throw RuntimeException()
    }

    protected abstract fun initializeDependencies(): Any

    protected fun destroyDependencies() {
    }
}