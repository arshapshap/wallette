package com.example.di

import com.example.common.di.CommonApi
import com.example.common.di.scopes.ApplicationScope
import com.example.core_db.di.DbApi
import dagger.Component

@Component(
    modules = [
        DataBindsModule::class,
        DataModule::class
    ],
    dependencies = [
        DataDependencies::class
    ]
)
@ApplicationScope
abstract class DataComponent : DataApi {

    @Component.Builder
    interface Builder {

        fun withDependencies(deps: DataDependencies): Builder

        fun build(): DataComponent
    }

    @Component(
        dependencies = [
            CommonApi::class,
            DbApi::class
        ]
    )
    interface DataDependenciesComponent : DataDependencies
}