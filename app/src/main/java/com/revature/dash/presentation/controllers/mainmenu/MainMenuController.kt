package com.revature.dash.presentation.controllers.mainmenu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.mosby3.MviController
import com.revature.dash.R
import com.revature.dash.databinding.ControllerMainmenuBinding
import com.revature.dash.domain.routine.RunRoutine
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder

class MainMenuController:MviController<MainMenuView,MainMenuPresenter>(),MainMenuView {

    private lateinit var presenter:MainMenuPresenter
    private val adapter : GroupAdapter<GroupieViewHolder> = GroupAdapter()

    private lateinit var description:TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var image:ImageView
    private lateinit var recyclerView: RecyclerView
    private lateinit var desplayCard:CardView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup,
        savedViewState: Bundle?,
    ): View {
        val view = inflater.inflate(R.layout.controller_mainmenu,container,false)

        setupController(view)

        return view
    }

    private fun setupController(view: View) {
        presenter = MainMenuPresenter(RunRoutine())
        val binding = ControllerMainmenuBinding.bind(view)
        description = binding.textDescriptionMainmenu
        progressBar = binding.progressBar
        image = binding.imageMainmenu
        desplayCard = binding.cardDescription

        recyclerView = binding.recyclerMainmenu
        recyclerView.layoutManager = LinearLayoutManager(
            view.context,
            LinearLayoutManager.HORIZONTAL,
            false)

        recyclerView.adapter = adapter

    }

    override fun createPresenter() = presenter

    override fun render(state: MainMenuVS) {
        when(state){
            is MainMenuVS.Display -> renderDisplay(state)
            is MainMenuVS.Loading -> renderLoading()
        }
    }
    private fun renderDisplay(state:MainMenuVS.Display){

        description.text = state.selectedDay.description
        adapter.clear()
        adapter.addAll(state.runList.map {
            RunRecyclerItem(it)
        })
        isLoading(false)
    }
    private fun renderLoading(){
        isLoading(true)
    }
    private fun isLoading(loading:Boolean){
        if(loading){
            description.visibility = View.GONE
            progressBar.visibility = View.VISIBLE
            image.visibility = View.GONE
            recyclerView.visibility = View.GONE
            desplayCard.visibility = View.GONE
        } else{
            description.visibility = View.VISIBLE
            progressBar.visibility = View.GONE
            image.visibility = View.VISIBLE
            recyclerView.visibility = View.VISIBLE
            desplayCard.visibility = View.VISIBLE
        }

    }

}