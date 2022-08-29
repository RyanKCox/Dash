package com.revature.dash.presentation.core.activity

import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.bluelinelabs.conductor.Conductor
import com.bluelinelabs.conductor.Controller
import com.bluelinelabs.conductor.Router
import com.bluelinelabs.conductor.RouterTransaction
import dagger.android.AndroidInjection
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

abstract class BaseActivity : AppCompatActivity(), HasAndroidInjector {

    private lateinit var binding:ViewBinding

    @Inject lateinit var androidInjector:DispatchingAndroidInjector<Any>


    protected lateinit var router: Router

    /**
     * Provide the binding to the Activity
     */
    abstract fun getViewBinding():ViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        binding = getViewBinding()
        setContentView(binding.root)
        Log.d("BaseActivity","OnCreate setup")

        onCreated(binding,savedInstanceState)
        Log.d("BaseActivity","OnCreated finished")
    }

    override fun androidInjector() = androidInjector

    /**
     * Called after onCreate for Setup. Use for setting up viewBinding.
     *
     * Cast binding as your activity's ViewBinding.
     */
    abstract fun onCreated(binding:ViewBinding,savedInstanceState: Bundle?)

    protected fun setupRouter(container:ViewGroup,savedInstanceState: Bundle?){
        router = Conductor.attachRouter(this,container,savedInstanceState)
    }
    protected fun setupRootController(controller: Controller){
        if(!router.hasRootController())
            router.setRoot(RouterTransaction.with(controller))
    }

    override fun onBackPressed() {
        if(!router.handleBack())
            super.onBackPressed()
    }

}