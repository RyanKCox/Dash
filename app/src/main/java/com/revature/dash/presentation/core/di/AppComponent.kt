package com.revature.dash.presentation.core.di

import android.app.Application
import com.revature.dash.presentation.DashApp
import com.revature.dash.presentation.core.di.keys.AndroidInjectorKeyRegistry
import com.revature.dash.presentation.core.di.keys.ControllerKey
import com.revature.dash.presentation.core.di.module.*
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@AndroidInjectorKeyRegistry(keys = [ControllerKey::class])
@Singleton
@Component(modules = [
    AndroidInjectionModule::class,
    AppModule::class,
    RepositoryModule::class,
    ActivityBuilderModule::class,
    ControllersModule::class,
    ControllerInjectionModule::class
])
interface AppComponent{
    fun inject(app:DashApp)

    @Component.Factory
    interface Factory{
        fun create(@BindsInstance app:Application):AppComponent
    }
}