package com.revature.dash.presentation.controllers.run

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.hannesdorfmann.mosby3.MviController
import com.jakewharton.rxbinding2.view.clicks
import com.revature.dash.R
import com.revature.dash.databinding.ControllerRunScreenBinding
import com.revature.dash.domain.routine.RunRoutine
import io.reactivex.Observable

class RunController :MviController<RunView,RunPresenter>(),RunView{

    private lateinit var presenter: RunPresenter

    private lateinit var image:ImageView
    private lateinit var description:TextView
    private lateinit var startButton: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup,
        savedViewState: Bundle?,
    ): View {
        val view = inflater.inflate(R.layout.controller_run_screen,container,false)
        setup(view)
        return view
    }
    private fun setup(view:View){
        presenter = RunPresenter(RunRoutine())
        val binder = ControllerRunScreenBinding.bind(view)
        image = binder.imageRun
        description = binder.textDescriptionRun
        startButton = binder.buttonStart
    }

    override fun createPresenter() = presenter

    override fun toggleStart() = startButton.clicks()

    override fun render(state: RunVS) {
        when(state){
            is RunVS.DisplayRun -> renderDisplay(state)
            is RunVS.Loading -> renderLoading()
        }
    }
    private fun renderDisplay(state:RunVS.DisplayRun){

        description.text = state.runItem.description
        startButton.text = if(state.isStarted) "Pause" else "Start"

        isLoading(false)
    }
    private fun renderLoading(){

        isLoading(true)
    }
    private fun isLoading(loading:Boolean){
        if(loading){
            image.visibility = View.GONE
            description.visibility = View.GONE
            startButton.visibility = View.GONE
        }
        else{
            image.visibility = View.VISIBLE
            description.visibility = View.VISIBLE
            startButton.visibility = View.VISIBLE

        }
    }

}