package com.revature.dash.presentation.controllers.mainmenu

import com.bluelinelabs.conductor.Router
import com.bluelinelabs.conductor.RouterTransaction
import com.bluelinelabs.conductor.changehandler.FadeChangeHandler
import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import com.hannesdorfmann.mosby3.mvp.MvpView
import com.revature.dash.domain.routine.IRunRoutine
import com.revature.dash.model.data.RunDay
import com.revature.dash.presentation.controllers.run.RunController
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class MainMenuPresenter @Inject constructor(
    private val runRepo: IRunRoutine
):MviBasePresenter<MainMenuView,MainMenuVS>() {

    private val routineLoadedSubject = PublishSubject.create<Boolean>()


    override fun bindIntents() {

        val data = runRepo.initializeRoutine()
            .doOnSubscribe { MainMenuVS.Loading }
            .doOnComplete {
                routineLoadedSubject.onNext(true)
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .ofType(MainMenuVS::class.java)

        val loadedIntent = intent { routineLoadedSubject }
            .map {
                MainMenuVS.Display(
                    runRepo.getSelectedRunDay()!!,
                    runRepo.getRoutine()
                )
            }
            .ofType(MainMenuVS::class.java)

        val itemClicked = intent { it.runItemClick() }
            .map {
                runRepo.setSelectedRunDayByIndex(it)
                MainMenuVS.Display(
                    runRepo.getSelectedRunDay()!!,
                    runRepo.getRoutine())
            }
            .ofType(MainMenuVS::class.java)

        val startClicked = intent { it.startRunClick() }
            .doOnNext{ router->
                router.pushController(RouterTransaction.with(RunController())
                    .pushChangeHandler(FadeChangeHandler())
                    .popChangeHandler(FadeChangeHandler()))
            }
            .ofType(MainMenuVS::class.java)

        val viewState = loadedIntent
            .mergeWith(data)
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
        val selectedRunDay:RunDay,
        val runList:List<RunDay>):MainMenuVS()
}