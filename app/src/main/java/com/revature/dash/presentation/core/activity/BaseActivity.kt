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

abstract class BaseActivity : AppCompatActivity() {

    private lateinit var binding:ViewBinding

    protected lateinit var router: Router

    /**
     * Provide the binding to the Activity
     */
    abstract fun getViewBinding():ViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = getViewBinding()
        setContentView(binding.root)
        Log.d("BaseActivity","OnCreate setup")

        onCreated(binding,savedInstanceState)
        Log.d("BaseActivity","OnCreated finished")
    }

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