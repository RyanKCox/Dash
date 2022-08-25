package com.revature.dash.presentation.controllers.mainmenu

import com.bluelinelabs.conductor.Router
import com.bluelinelabs.conductor.RouterTransaction
import com.bluelinelabs.conductor.changehandler.FadeChangeHandler
import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import com.hannesdorfmann.mosby3.mvp.MvpView
import com.revature.dash.domain.routine.RunRoutine
import com.revature.dash.model.data.RunDay
import com.revature.dash.model.data.RunItem
import com.revature.dash.presentation.controllers.title.TitleController
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit
import kotlin.math.round

class MainMenuPresenter(
    private val runRepo:RunRoutine
):MviBasePresenter<MainMenuView,MainMenuVS>() {

    private var selectedDay:Int = runRepo.getNextRunPosition()

    override fun bindIntents() {

        val itemClicked = intent { it.runItemClick() }
            .map {
                selectedDay = it
                MainMenuVS.Display(
                    selectedDay,
                    runRepo.getRunTypeID(runRepo.getRoutine()[selectedDay].runType),
                    runRepo.getRoutine())
            }
            .ofType(MainMenuVS::class.java)

        val startClicked = intent { it.startRunClick() }
            .doOnNext{ router->
                router.pushController(RouterTransaction.with(TitleController())
                    .pushChangeHandler(FadeChangeHandler())
                    .popChangeHandler(FadeChangeHandler()))
            }
            .ofType(MainMenuVS::class.java)

        val data = Observable.just(MainMenuVS.Display(
            selectedDay,
            runRepo.getRunTypeID(runRepo.getRoutine()[selectedDay].runType),
            runRepo.getRoutine()))

            .delay(2,TimeUnit.SECONDS)
            .ofType(MainMenuVS::class.java)

        val viewState = data
            .mergeWith(itemClicked)
            .mergeWith(startClicked)
            .observeOn(AndroidSchedulers.mainThread())

        subscribeViewState(viewState){view,state->view.render(state)}
    }
}

interface MainMenuView: MvpView {

    fun runItemClick():Observable<Int>
    fun startRunClick():Observable<Router>

    fun render(state:MainMenuVS)
}
sealed class MainMenuVS{
    object Loading:MainMenuVS()
    data class Display(
        val selectedDay:Int,
        val displayedRunItem:RunItem,
        val runList:List<RunDay>):MainMenuVS()
}