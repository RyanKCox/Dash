package com.revature.dash.presentation.controllers.run

import android.os.CountDownTimer
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
    private val publishSubject = PublishSubject.create<Long>()

    override fun bindIntents() {

        val toggleStart = intent { it.toggleStart() }
            .map {
                isStarted = !isStarted


                if(countDownTimer == null){
                    countDownTimer = object: CountDownTimer(runRepo.getSelectedRunDay().runCycle.getTotalTime(),1000){
                        override fun onTick(p0: Long) {
                            publishSubject.onNext(p0)
                        }

                        override fun onFinish() {
                            publishSubject.onNext(0)
                        }
                    }
                        .start()
                }
                else{
                    if(isStarted){
                        countDownTimer!!.start()
                    }
                }

                RunVS.DisplayRun(isStarted,runRepo.getSelectedRunDay().runCycle.getTotalTime(),runRepo.getSelectedRunDay())
            }
            .ofType(RunVS::class.java)

        val updateIntent = intent { publishSubject }
            .map {
                RunVS.DisplayRun(isStarted,it,runRepo.getSelectedRunDay())
            }
            .ofType(RunVS::class.java)

        val data = Observable.just(RunVS.DisplayRun(isStarted,runRepo.getSelectedRunDay().runCycle.getTotalTime(),runRepo.getSelectedRunDay(),))
            .ofType(RunVS::class.java)

        val viewState = data
            .mergeWith(toggleStart)
            .mergeWith(updateIntent)
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
        val timeLeft:Long,
        val runDay: RunDay
    ):RunVS()
}
