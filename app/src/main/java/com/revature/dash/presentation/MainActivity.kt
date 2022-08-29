package com.revature.dash.presentation

import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.revature.dash.databinding.ActivityMainBinding
import com.revature.dash.domain.routine.IRunRoutine
import com.revature.dash.presentation.controllers.title.TitleController
import com.revature.dash.presentation.core.activity.BaseActivity
import javax.inject.Inject

class MainActivity : BaseActivity() {

    private lateinit var controllerContainer:ViewGroup
    @Inject
    lateinit var runRoutine: IRunRoutine

    override fun getViewBinding() = ActivityMainBinding.inflate(layoutInflater)

    override fun onCreated(binding: ViewBinding,savedInstanceState: Bundle?) {
        when(binding){
            is ActivityMainBinding->{
                controllerContainer = binding.controllerContainer
                setupRouter(controllerContainer,savedInstanceState)
                setupRootController(TitleController())
                Log.d("MainActivity","Binding Successful")
            }
            else->{
                Log.d("MainActivity","Binding failed, not an ActivityMainBinding")
            }
        }
    }
}