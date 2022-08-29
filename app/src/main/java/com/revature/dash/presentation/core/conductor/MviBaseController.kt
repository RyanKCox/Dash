package com.revature.dash.presentation.core.conductor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bluelinelabs.conductor.Controller
import com.hannesdorfmann.mosby3.MviController
import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import com.hannesdorfmann.mosby3.mvp.MvpView
import com.revature.dash.presentation.core.di.ControllerInjection
import com.revature.dash.presentation.core.di.HasControllerInjector
import dagger.android.DispatchingAndroidInjector
import javax.inject.Inject

abstract class MviBaseController<V:MvpView,P : MviBasePresenter<V,*>> :
    MviController<V,P>(),
    HasControllerInjector,
    MvpView
{

    @Inject lateinit var androidInjector:DispatchingAndroidInjector<Controller>
    @Inject lateinit var presenter:P

    /**
     * Sets the layout resource for the Controller
     */
    protected abstract fun getLayout():Int

    /**
     * Controller Setup, Called after view is inflated
     */
    protected abstract fun onCreated(view:View)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup,
        savedViewState: Bundle?,
    ): View {
        ControllerInjection.inject(this)
        val view = inflater.inflate(getLayout(),container,false)
        onCreated(view)
        return view
    }

    override fun createPresenter() = presenter

    override fun controllerInjector() = androidInjector

}