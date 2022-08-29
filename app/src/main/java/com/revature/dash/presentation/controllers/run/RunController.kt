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
import com.revature.dash.domain.routine.IRunRoutine
import com.revature.dash.domain.routine.RunRoutine
import com.revature.dash.presentation.MainActivity
import java.text.SimpleDateFormat
import javax.inject.Inject

class RunController :MviController<RunView,RunPresenter>(),RunView{

    private lateinit var presenter: RunPresenter
//    @Inject
//    lateinit var runRepo: IRunRoutine
//    var runRepo = (activity as MainActivity).runRoutine


    private lateinit var image:ImageView
    private lateinit var description:TextView
    private lateinit var startButton: Button
    private lateinit var timerLabel:TextView
    private lateinit var timer:TextView

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
        presenter = RunPresenter((activity as MainActivity).runRoutine)

        val binder = ControllerRunScreenBinding.bind(view)
        image = binder.imageRun
        description = binder.textDescriptionRun
        startButton = binder.buttonStart
        timerLabel = binder.textTimerLabel
        timer = binder.textTimer
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

        description.text = state.runDay.runCycle.description
        startButton.text = if(state.isStarted) "Pause" else "Start"
        timer.text = SimpleDateFormat("mm:ss").format(state.timeLeft)
        timerLabel.text = "Timer: ${SimpleDateFormat("mm:ss").format(state.runDay.runCycle.getTotalTime())}"

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