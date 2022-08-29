package com.revature.dash.presentation.controllers.mainmenu

import android.text.method.ScrollingMovementMethod
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bluelinelabs.conductor.Router
import com.jakewharton.rxbinding2.view.clicks
import com.revature.dash.R
import com.revature.dash.databinding.ControllerMainmenuBinding
import com.revature.dash.presentation.core.conductor.MviBaseController
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class MainMenuController:MviBaseController<MainMenuView,MainMenuPresenter>(),MainMenuView {

    private val adapter : GroupAdapter<GroupieViewHolder> = GroupAdapter()

    private lateinit var description:TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var image:ImageView
    private lateinit var recyclerView: RecyclerView
    private lateinit var displayCard:CardView
    private lateinit var startButton: Button

    private var clickTest = PublishSubject.create<Int>()

    override fun getLayout() = R.layout.controller_mainmenu

    override fun onCreated(view: View) {
        val binding = ControllerMainmenuBinding.bind(view)
        description = binding.textDescriptionMainmenu
        progressBar = binding.progressBar
        image = binding.imageMainmenu
        displayCard = binding.cardDescription
        startButton = binding.buttonStartrun

        recyclerView = binding.recyclerMainmenu
        recyclerView.layoutManager = LinearLayoutManager(
            view.context,
            LinearLayoutManager.HORIZONTAL,
            false)

        recyclerView.adapter = adapter

        adapter.setOnItemClickListener { item, _ ->
            if(item is RunRecyclerItem){
                clickTest.onNext(adapter.getAdapterPosition(item))
            }
        }
    }

    override fun runItemClick(): Observable<Int> =
        clickTest

    override fun startRunClick(): Observable<Router> = startButton.clicks().map {
        router
    }

    override fun render(state: MainMenuVS) {
        when(state){
            is MainMenuVS.Display -> renderDisplay(state)
            is MainMenuVS.Loading -> renderLoading()
        }
    }
    private fun renderDisplay(state:MainMenuVS.Display){

        description.text = state.selectedRunDay.runCycle.description
        description.movementMethod = ScrollingMovementMethod()
        adapter.clear()

        state.runList.forEach { runDay ->
            val selected = runDay == state.selectedRunDay

            adapter.add(RunRecyclerItem(runDay,selected))
        }
        isLoading(false)
    }
    private fun renderLoading(){
        isLoading(true)
    }
    private fun isLoading(loading:Boolean){
        if(loading){
            displayCard.visibility = View.GONE
            progressBar.visibility = View.VISIBLE
        } else{
            displayCard.visibility = View.VISIBLE
            progressBar.visibility = View.GONE
        }

    }

}