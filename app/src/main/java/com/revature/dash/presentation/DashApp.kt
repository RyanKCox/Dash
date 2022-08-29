package com.revature.dash.presentation

import android.app.Application
import com.bluelinelabs.conductor.Controller
import com.revature.dash.presentation.core.di.DaggerAppComponent
import com.revature.dash.presentation.core.di.HasControllerInjector
import dagger.android.DispatchingAndroidInjector
import javax.inject.Inject

class DashApp :Application(),HasControllerInjector{

    @Inject
    lateinit var controllerInjector:DispatchingAndroidInjector<Controller>

    override fun onCreate() {
        super.onCreate()
        DaggerAppComponent.factory().create(this).inject(this)
    }

    override fun controllerInjector() = controllerInjector

}