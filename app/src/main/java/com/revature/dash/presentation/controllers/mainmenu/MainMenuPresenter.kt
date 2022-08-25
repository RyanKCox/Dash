package com.revature.dash.presentation.controllers.mainmenu

import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import com.hannesdorfmann.mosby3.mvp.MvpView
import com.revature.dash.domain.routine.RunRoutine
import com.revature.dash.model.data.RunDay
import com.revature.dash.model.data.RunItem
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit

class MainMenuPresenter(
    private val runRepo:RunRoutine
):MviBasePresenter<MainMenuView,MainMenuVS>() {

    private var selectedDay:Int = runRepo.getNextRunPosition()

    override fun bindIntents() {

        val loading = Observable.just(MainMenuVS.Loading)
            .ofType(MainMenuVS::class.java)

        val itemClicked = intent { it.runItemClick() }
            .map {
                selectedDay = it
                MainMenuVS.Display(
                    selectedDay,
                    runRepo.getRunTypeID(runRepo.getRoutine()[selectedDay].runType),
                    runRepo.getRoutine())
            }
            .ofType(MainMenuVS::class.java)

        val data = Observable.just(MainMenuVS.Display(
            selectedDay,
            runRepo.getRunTypeID(runRepo.getRoutine()[selectedDay].runType),
            runRepo.getRoutine()))

            .delay(2,TimeUnit.SECONDS)
            .ofType(MainMenuVS::class.java)

        val viewState = loading
            .mergeWith(data)
            .mergeWith(itemClicked)
            .observeOn(AndroidSchedulers.mainThread())

        subscribeViewState(viewState){view,state->view.render(state)}
    }
}

interface MainMenuView: MvpView {

    fun runItemClick():Observable<Int>

    fun render(state:MainMenuVS)
}
sealed class MainMenuVS{
    object Loading:MainMenuVS()
    data class Display(
        val selectedDay:Int,
        val displayedRunItem:RunItem,
        val runList:List<RunDay>):MainMenuVS()
}