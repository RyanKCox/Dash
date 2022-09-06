package com.revature.dash.presentation.controllers.run

import android.os.CountDownTimer
import com.bluelinelabs.conductor.Router
import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import com.hannesdorfmann.mosby3.mvp.MvpView
import com.revature.dash.domain.routine.IRunRoutine
import com.revature.dash.model.data.RunDay
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class RunPresenter @Inject constructor(
    private val runRepo: IRunRoutine
):MviBasePresenter<RunView,RunVS>() {

    private var isStarted = false
    private var countDownTimer:CountDownTimer? = null
    private val timerSubject = PublishSubject.create<Long>()
    private val runDay = runRepo.getSelectedRunDay()!!
    private var cycleIndex = 0

    override fun bindIntents() {

        val toggleStart = intent { it.toggleStart() }
            .map {
                isStarted = !isStarted


                if(countDownTimer == null){
                    countDownTimer = object: CountDownTimer(runDay.runCycle.cycle[cycleIndex].time,1000){
                        override fun onTick(p0: Long) {
                            timerSubject.onNext(p0)
                        }

                        override fun onFinish() {
                            timerSubject.onNext(0)
                            advanceTimer(runDay.runCycle.cycle[cycleIndex].time)
                        }
                    }
                        .start()
                }
                else{
                    if(isStarted){
                        countDownTimer!!.start()
                    }
                }

                RunVS.DisplayRun(isStarted,runDay.runCycle.getTotalTime(),
                    cycleIndex,runDay)
            }
            .ofType(RunVS::class.java)

        val updateIntent = intent { timerSubject }
            .map {
                if(runDay.completed){
                    RunVS.Completed
                }
                else {
                    RunVS.DisplayRun(
                        isStarted,
                        it,
                        cycleIndex,
                        runDay)
                }
            }
            .ofType(RunVS::class.java)

        val doneIntent = intent { it.doneClick() }
            .doOnNext{ router->
                router.popCurrentController()
            }
            .ofType(RunVS::class.java)

        val data = Observable.just(RunVS.DisplayRun(
            isStarted,
            runDay.runCycle.cycle[cycleIndex].time,
            cycleIndex,
            runDay))
            .ofType(RunVS::class.java)

        val viewState = data
            .mergeWith(toggleStart)
            .mergeWith(updateIntent)
            .mergeWith(doneIntent)
            .observeOn(AndroidSchedulers.mainThread())

        subscribeViewState(viewState){view,state-> view.render(state)}
    }

    private fun advanceTimer(time:Long):Boolean{
        cycleIndex++

        if(cycleIndex >= runDay.runCycle.cycle.size)
            return false

        countDownTimer = object :CountDownTimer(time,1000){
            override fun onTick(p0: Long) {
                timerSubject.onNext(p0)
            }

            override fun onFinish() {
                if(!advanceTimer(runDay.runCycle.cycle[cycleIndex].time)) {
                    runDay.completed = true
                    runRepo.updateRunDay(runDay)
                    timerSubject.onNext(0)
                }
            }

        }.start()
        return true

    }
}
interface RunView:MvpView{

    fun toggleStart(): Observable<Unit>
    fun doneClick():Observable<Router>

    fun render(state:RunVS)
}
sealed class RunVS{
    object Loading:RunVS()
    data class DisplayRun(
        val isStarted:Boolean,
        val timeLeft:Long,
        val runIndex:Int,
        val runDay: RunDay
    ):RunVS()
    object Completed:RunVS()
}
