package com.example.core_network.di

import com.example.common.di.CommonApi
import com.example.common.di.scopes.ApplicationScope
import dagger.Component

@Component(
    dependencies = [
        NetworkDependencies::class
    ],
    modules = [
        NetworkModule::class,
        NetworkBindsModule::class
    ]
)
@ApplicationScope
abstract class NetworkComponent : NetworkApi {

    @Component(
        dependencies = [
            CommonApi::class
        ]
    )
    interface NetworkDependenciesComponent : NetworkDependencies
}