package com.revature.dash.presentation.core.di

import android.app.Application
import android.os.Build
import com.revature.dash.presentation.DashApp
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    AppModule::class
])
interface AppComponent :AndroidInjector<DashApp>{

    @Component.Factory
    interface Factory{
        fun create(@BindsInstance app:Application):AppComponent
    }
}