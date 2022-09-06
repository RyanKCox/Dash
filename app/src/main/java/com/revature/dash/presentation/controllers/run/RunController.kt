package com.revature.dash.presentation.controllers.run

import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bluelinelabs.conductor.Router
import com.jakewharton.rxbinding2.view.clicks
import com.revature.dash.R
import com.revature.dash.databinding.ControllerRunScreenBinding
import com.revature.dash.presentation.core.conductor.MviBaseController
import io.reactivex.Observable
import java.text.SimpleDateFormat

class RunController :MviBaseController<RunView,RunPresenter>(),RunView{

    private lateinit var image:ImageView
    private lateinit var description:TextView
    private lateinit var startButton: Button
    private lateinit var doneButton:Button
    private lateinit var timerLabel:TextView
    private lateinit var timer:TextView

    override fun getLayout() = R.layout.controller_run_screen

    override fun onCreated(view: View) {
        val binder = ControllerRunScreenBinding.bind(view)
        image = binder.imageRun
        description = binder.textDescriptionRun
        startButton = binder.buttonStart
        doneButton = binder.buttonDone
        timerLabel = binder.textTimerLabel
        timer = binder.textTimer
    }
    override fun toggleStart() = startButton.clicks()
    override fun doneClick(): Observable<Router> = doneButton.clicks()
        .map {
            router
        }

    override fun render(state: RunVS) {
        when(state){
            is RunVS.DisplayRun -> renderDisplay(state)
            is RunVS.Loading -> renderLoading()
            is RunVS.Completed -> renderComplete()
        }
    }

    private fun renderDisplay(state:RunVS.DisplayRun){

        description.text = state.runDay.runCycle.description
        startButton.text = if(state.isStarted) "Pause" else "Start"
        timer.text = SimpleDateFormat("mm:ss").format(state.timeLeft)
        timerLabel.text ="You are on cycle ${state.runIndex+1} of ${state.runDay.runCycle.cycle.size}\n" +
                state.runDay.runCycle.cycle[state.runIndex].type +
                " for ${SimpleDateFormat("mm:ss").format(state.runDay.runCycle.cycle[state.runIndex].time)}"

        isLoading(false)
    }
    private fun renderComplete(){
        image.visibility = View.VISIBLE
        description.visibility = View.VISIBLE
        startButton.visibility = View.GONE
        doneButton.visibility = View.VISIBLE
        timer.visibility = View.GONE
        timerLabel.visibility = View.GONE
        startButton.text = "Done!"
    }
    private fun renderLoading(){

        isLoading(true)
    }
    private fun isLoading(loading:Boolean){
        if(loading){
            image.visibility = View.GONE
            description.visibility = View.GONE
            startButton.visibility = View.GONE
            doneButton.visibility = View.GONE

        }
        else{
            image.visibility = View.VISIBLE
            description.visibility = View.VISIBLE
            startButton.visibility = View.VISIBLE
            doneButton.visibility = View.GONE
        }
    }


}