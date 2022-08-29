package com.revature.dash.presentation.core.di

import android.app.Application
import com.revature.dash.presentation.DashApp
import com.revature.dash.presentation.core.di.keys.AndroidInjectorKeyRegistry
import com.revature.dash.presentation.core.di.keys.ControllerKey
import com.revature.dash.presentation.core.di.module.ActivityBuilderModule
import com.revature.dash.presentation.core.di.module.AppModule
import com.revature.dash.presentation.core.di.module.ControllerInjectionModule
import com.revature.dash.presentation.core.di.module.ControllersModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@AndroidInjectorKeyRegistry(keys = [ControllerKey::class])
@Singleton
@Component(modules = [
    AndroidInjectionModule::class,
    AppModule::class,
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