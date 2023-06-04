package com.example.di

import com.example.common.di.FeatureApiHolder
import com.example.common.di.FeatureContainer
import com.example.common.di.scopes.ApplicationScope
import com.example.core_db.di.DbApi
import javax.inject.Inject

@ApplicationScope
class DataHolder @Inject constructor(
    featureContainer: FeatureContainer
) : FeatureApiHolder(featureContainer) {

    override fun initializeDependencies(): Any {
        val dataDependencies = DaggerDataComponent_DataDependenciesComponent.builder()
            .commonApi(commonApi())
            .dbApi(getFeature(DbApi::class.java))
            .build()
        return DaggerDataComponent.builder()
            .withDependencies(dataDependencies)
            .build()
    }
}