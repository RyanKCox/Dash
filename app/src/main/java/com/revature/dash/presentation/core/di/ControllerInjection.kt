package com.revature.dash.presentation.core.di

import android.app.Activity
import com.bluelinelabs.conductor.Controller
import dagger.android.AndroidInjector
import dagger.internal.Preconditions


object ControllerInjection {
    fun inject(controller: Controller) {
        Preconditions.checkNotNull(controller, "controller")
        val hasDispatchingControllerInjector = findHasControllerInjector(controller)
        val controllerInjector: AndroidInjector<Controller> =
            hasDispatchingControllerInjector.controllerInjector()
        Preconditions.checkNotNull(controllerInjector, "%s.controllerInjector() returned null",
            hasDispatchingControllerInjector.javaClass.canonicalName)
        controllerInjector.inject(controller)
    }

    private fun findHasControllerInjector(controller: Controller): HasControllerInjector {
        var parentController: Controller? = controller
        do {
            if (parentController != null) {
                if (parentController.parentController.also { parentController = it } == null) {
                    val activity: Activity = controller.activity!!
                    if (activity is HasControllerInjector) {
                        return activity
                    }
                    if (activity.application is HasControllerInjector) {
                        return activity.application as HasControllerInjector
                    }
                    throw IllegalArgumentException(
                        String.format("No injector was found for %s",
                            controller.javaClass.canonicalName))
                }
            }
        } while (parentController !is HasControllerInjector)
        return parentController as HasControllerInjector
    }
}