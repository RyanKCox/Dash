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
    override fun bindIntents() {

        val loading = Observable.just(MainMenuVS.Loading)
            .ofType(MainMenuVS::class.java)

        val data = Observable.just(MainMenuVS.Display(runRepo.getRunByID(runRepo.getNextRunID()),runRepo.getRoutine()))
            .delay(2,TimeUnit.SECONDS)
            .ofType(MainMenuVS::class.java)

        val viewState = loading
            .mergeWith(data)
            .observeOn(AndroidSchedulers.mainThread())

        subscribeViewState(viewState){view,state->view.render(state)}
    }
}

interface MainMenuView: MvpView {

    fun render(state:MainMenuVS)
}
sealed class MainMenuVS{
    object Loading:MainMenuVS()
    data class Display(
        val selectedDay:RunItem,
        val runList:List<RunDay>):MainMenuVS()
}