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
    private var complete = false
    private var cycleIndex = 0
    private var cycleTime = runDay.runCycle.cycle[cycleIndex].time

    override fun bindIntents() {

        val toggleStart = intent { it.toggleStart() }
            .map {
                isStarted = !isStarted


                if(countDownTimer == null){
//                    countDownTimer = object: CountDownTimer(runDay.runCycle.cycle[cycleIndex].time,1000){
//                        override fun onTick(p0: Long) {
//                            cycleTime = p0
//                            timerSubject.onNext(p0)
//                        }
//
//                        override fun onFinish() {
//                            timerSubject.onNext(0)
//                            advanceTimer(runDay.runCycle.cycle[cycleIndex].time)
//                        }
//                    }
                    createTimer(runDay.runCycle.cycle[cycleIndex].time)
//                    countDownTimer!!.start()
                }
                else{
                    if(isStarted){
                        createTimer(cycleTime)
//                        countDownTimer!!.start()
                    }
                    else{
                        countDownTimer!!.cancel()
                    }
                }

                RunVS.DisplayRun(
                    isStarted,
                    cycleTime,
                    cycleIndex,
                    runDay)
            }
            .ofType(RunVS::class.java)

        val updateIntent = intent { timerSubject }
            .map {
                if(complete){
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
                    complete = true

                    if(!runDay.completed){
                        runDay.completed = complete
                        runRepo.updateRunDay(runDay)
                    }
                    timerSubject.onNext(0)
                }
            }

        }.start()
        return true
    }
    private fun createTimer(time:Long){
        countDownTimer = object :CountDownTimer(time,1000){
            override fun onTick(p0: Long) {
                cycleTime = p0
                timerSubject.onNext(p0)
            }

            override fun onFinish() {
                timerSubject.onNext(0)
                if(cycleIndex < runDay.runCycle.cycle.lastIndex){
                    cycleIndex++
                    createTimer(runDay.runCycle.cycle[cycleIndex].time)

                }
                else{
                    complete = true
                    if(!runDay.completed){
                        runDay.completed = complete
                        runRepo.updateRunDay(runDay)
                    }
                    timerSubject.onNext(0)
                }
            }

        }.start()
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
