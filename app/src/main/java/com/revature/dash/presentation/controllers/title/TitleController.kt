package com.revature.dash.presentation.controllers.title

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import com.bluelinelabs.conductor.Router
import com.hannesdorfmann.mosby3.MviController
import com.jakewharton.rxbinding2.view.clicks
import com.revature.dash.R
import com.revature.dash.databinding.ControllerTitleScreenBinding
import io.reactivex.Observable

class TitleController :MviController<TitleView,TitlePresenter>(),TitleView{

    private lateinit var presenter:TitlePresenter

    private lateinit var enterButton:Button
    private lateinit var progressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup,
        savedViewState: Bundle?,
    ): View {
        val view = inflater.inflate(R.layout.controller_title_screen,container,false)

        setupController(view)

        return view
    }
    private fun setupController(view:View){
        presenter = TitlePresenter()
        val binding = ControllerTitleScreenBinding.bind(view)
        enterButton = binding.button
        progressBar = binding.progressBar
    }

    override fun createPresenter() = presenter

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