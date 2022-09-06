package com.revature.dash.domain.routine

import android.util.Log
import com.revature.dash.model.data.RunCycle
import com.revature.dash.model.data.RunDay
import com.revature.dash.model.data.retrofit.RoutineAPI
import com.revature.dash.model.data.room.RoutineDao
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

interface IRunRoutine {
    val defaultRunList: MutableList<RunDay>
    var selectedRun: RunDay?
    fun getSelectedRunDay(): RunDay?
    fun getSelectedRunCycle(): RunCycle?
    fun setSelectedRunDayByIndex(index: Int)
    fun getRoutine(): List<RunDay>
    fun getNextRunDay(): RunDay?
    fun initializeRoutine():Observable<MutableList<RunDay>>
    fun updateRunDay(updateDay: RunDay)
}
@Singleton
class RunRoutine @Inject constructor(
    private val routineAPI:RoutineAPI,
    private val routineDao: RoutineDao
) : IRunRoutine {


    override val defaultRunList: MutableList<RunDay> = mutableListOf()

    override fun initializeRoutine():Observable<MutableList<RunDay>>{
        return routineDao.fetchRoutine()
            .doOnError {
                Log.d("RunRoutine","Loading from Room Failed")
            }
            .map { roomResponse->
                if(roomResponse.isEmpty()){
                    Log.d("RunRoutine","Loading from retrofit")

                    //Load by retrofit
                    fetchRoutineFromAPI()
                        .doOnNext {
                            //for testing
//                            routineDao.insertRunDay(RunDay(runCycle = RunCycle().builder(5000,2,5000,5000)))

                            defaultRunList.forEach {
                                routineDao.insertRunDay(it)
                            }
                        }
                        .blockingFirst()
                    defaultRunList
                }else {

                    Log.d("RunRoutine", "Loading from room Size:${roomResponse.size}")
                    defaultRunList.clear()
                    defaultRunList.addAll(roomResponse)

                    selectedRun = getNextRunDay()
                    defaultRunList
                }
            }.toObservable()
    }

    private fun fetchRoutineFromAPI(): Observable<MutableList<RunDay>> {

        Log.d("RunRoutine","Fetch Routine from API Called")

        defaultRunList.clear()
        return routineAPI.getDefaultRoutine()
            .map { response->
                response.forEach { cycle ->
                    defaultRunList.add(
                        RunDay(0,
                            RunCycle().builder(
                            (cycle.warmup * 60000).toLong(),
                            cycle.numCycles.toInt(),
                            (cycle.runTime * 60000).toLong(),
                            (cycle.walkTime * 60000).toLong(),
                        ),
                            false
                        )
                    )
                }
                Log.d("RunRoutine", "Fetch Routine Finished\n List Size: ${defaultRunList.size}")
                selectedRun = getNextRunDay()
                defaultRunList

        }
    }

    override fun updateRunDay(updateDay:RunDay){
        Single.fromCallable {
            routineDao.insertRunDay(updateDay)
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }

    override var selectedRun = getNextRunDay()


    override fun getSelectedRunDay():RunDay?{
        return selectedRun
    }
    override fun getSelectedRunCycle():RunCycle?{
        return selectedRun?.runCycle
    }
    override fun setSelectedRunDayByIndex(index:Int){
        selectedRun = defaultRunList[index]
    }

    override fun getRoutine() = defaultRunList
    override fun getNextRunDay():RunDay?{
        if(defaultRunList.isEmpty())
            return null

        defaultRunList.forEach { runDay ->
            if(!runDay.completed)
                return runDay
        }
        return defaultRunList.last()
    }
}
