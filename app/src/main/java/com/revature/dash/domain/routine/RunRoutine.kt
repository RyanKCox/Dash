package com.revature.dash.domain.routine

import android.app.Application
import android.util.Log
import com.revature.dash.model.data.RunCycle
import com.revature.dash.model.data.RunDay
import com.revature.dash.model.data.retrofit.RoutineAPI
import com.revature.dash.model.data.room.RoutineDao
import io.reactivex.Observable
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
    fun fetchRoutine():Observable<MutableList<RunDay>>
}
@Singleton
class RunRoutine @Inject constructor(
    private val routineAPI:RoutineAPI,
    private val application: Application,
    private val routineDao: RoutineDao
) : IRunRoutine {


    override val defaultRunList: MutableList<RunDay> = mutableListOf()

    override fun fetchRoutine(): Observable<MutableList<RunDay>> {

        Log.d("RunRoutine","Fetch Routine Called")

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
