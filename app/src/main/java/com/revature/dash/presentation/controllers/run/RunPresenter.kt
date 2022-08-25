package com.revature.dash.presentation.controllers.run

import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import com.hannesdorfmann.mosby3.mvp.MvpView
import com.revature.dash.domain.routine.RunRoutine
import com.revature.dash.model.data.RunItem
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers

class RunPresenter(
    private val runRepo:RunRoutine
):MviBasePresenter<RunView,RunVS>() {

    private var isStarted = false

    override fun bindIntents() {

        val toggleStart = intent { it.toggleStart() }
            .map {
                isStarted = !isStarted
                RunVS.DisplayRun(isStarted,runRepo.getRunTypeID(0))
            }
            .ofType(RunVS::class.java)

        val data = Observable.just(RunVS.DisplayRun(isStarted,runRepo.getRunTypeID(0)))
            .ofType(RunVS::class.java)

        val viewState = data
            .mergeWith(toggleStart)
            .observeOn(AndroidSchedulers.mainThread())

        subscribeViewState(viewState){view,state-> view.render(state)}
    }

}
interface RunView:MvpView{

    fun toggleStart(): Observable<Unit>

    fun render(state:RunVS)
}
sealed class RunVS{
    object Loading:RunVS()
    data class DisplayRun(
        val isStarted:Boolean,
        val runItem: RunItem):RunVS()
}
