package com.revature.dash.presentation.core.di

import com.bluelinelabs.conductor.Controller
import dagger.android.AndroidInjector


interface HasControllerInjector {
    fun controllerInjector(): AndroidInjector<Controller>
}