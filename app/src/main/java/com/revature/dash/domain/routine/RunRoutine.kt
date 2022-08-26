package com.revature.dash.domain.routine

import com.revature.dash.model.data.RunCycle
import com.revature.dash.model.data.RunDay
import javax.inject.Inject
import javax.inject.Singleton

interface IRunRoutine {
    val defaultRunList: List<RunDay>
    var selectedRun: RunDay
    fun getSelectedRunDay(): RunDay
    fun getSelectedRunCycle(): RunCycle
    fun setSelectedRunDayByIndex(index: Int)
    fun getRoutine(): List<RunDay>
    fun getNextRunDay(): RunDay
}
@Singleton
class RunRoutine @Inject constructor() : IRunRoutine {

    override val defaultRunList = listOf(
        RunDay(RunCycle().Builder(5*60000,8,60000,(1.5*60000).toLong()),false),
        RunDay(RunCycle().Builder(5*60000,8,60000,(1.5*60000).toLong()),false),
        RunDay(RunCycle().Builder(5*60000,8,60000,(1.5*60000).toLong()),false),
        RunDay(RunCycle().Builder(5*60000,6,(1.5*60000).toLong(),(2*60000)),false),
        RunDay(RunCycle().Builder(5*60000,6,(1.5*60000).toLong(),(2*60000)),false),
        RunDay(RunCycle().Builder(5*60000,6,(1.5*60000).toLong(),(2*60000)),false),
        RunDay(RunCycle().Builder(5*60000,5,(2*60000),(2*60000)),false),
        RunDay(RunCycle().Builder(5*60000,5,(2*60000),(2*60000)),false),
        RunDay(RunCycle().Builder(5*60000,5,(2*60000),(2*60000)),false),
        RunDay(RunCycle().Builder(5*60000,2,(5*60000),(5*60000)),false),
        RunDay(RunCycle().Builder(5*60000,2,(5*60000),(5*60000)),false),
        RunDay(RunCycle().Builder(5*60000,2,(5*60000),(5*60000)),false)
    )

    override var selectedRun = getNextRunDay()


    override fun getSelectedRunDay():RunDay{
        return selectedRun
    }
    override fun getSelectedRunCycle():RunCycle{
        return selectedRun.runCycle
    }
    override fun setSelectedRunDayByIndex(index:Int){
        selectedRun = defaultRunList[index]
    }

    override fun getRoutine() = defaultRunList
    override fun getNextRunDay():RunDay{
        defaultRunList.forEach { runDay ->
            if(!runDay.completed)
                return runDay
        }
        return defaultRunList.last()
    }
}
