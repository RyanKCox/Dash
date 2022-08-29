package com.revature.dash.presentation.controllers.title

import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import com.bluelinelabs.conductor.Router
import com.jakewharton.rxbinding2.view.clicks
import com.revature.dash.R
import com.revature.dash.databinding.ControllerTitleScreenBinding
import com.revature.dash.presentation.core.conductor.MviBaseController
import io.reactivex.Observable

class TitleController :MviBaseController<TitleView,TitlePresenter>(),TitleView{

    private lateinit var enterButton:Button
    private lateinit var progressBar: ProgressBar

    override fun getLayout() = R.layout.controller_title_screen

    override fun onCreated(view: View) {
        val binding = ControllerTitleScreenBinding.bind(view)
        enterButton = binding.button
        progressBar = binding.progressBar
    }

    override fun enterIntent(): Observable<Router> = enterButton.clicks().map {
        router
    }

    override fun render(state: TitleVS) {
        when(state){
            TitleVS.Display -> {
                renderDisplay()
            }
            TitleVS.Loading -> {
                renderLoading()
            }
        }
    }

    private fun renderDisplay(){
        progressBar.visibility = View.GONE
        enterButton.visibility = View.VISIBLE
    }

    private fun renderLoading(){
        progressBar.visibility = View.VISIBLE
        enterButton.visibility = View.GONE
    }


}