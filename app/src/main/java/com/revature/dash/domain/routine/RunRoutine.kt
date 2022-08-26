package com.revature.dash.domain.routine

import com.revature.dash.model.data.RunCycle
import com.revature.dash.model.data.RunDay

class RunRoutine{

    private val defaultRunList = listOf(
        RunDay(RunCycle().Builder(5*60000,8,60000,(1.5*60000).toLong()),false),
        RunDay(RunCycle().Builder(5*60000,8,60000,(1.5*60000).toLong()),false),
        RunDay(RunCycle().Builder(5*60000,8,60000,(1.5*60000).toLong()),false),
        RunDay(RunCycle().Builder(5*60000,6,(1.5*60000).toLong(),(2*60000)),false),
        RunDay(RunCycle().Builder(5*60000,6,(1.5*60000).toLong(),(2*60000)),false),
        RunDay(RunCycle().Builder(5*60000,6,(1.5*60000).toLong(),(2*60000)),false),
        RunDay(RunCycle().Builder(5*60000,8,(2*60000),(2*60000)),false),
        RunDay(RunCycle().Builder(5*60000,8,(2*60000),(2*60000)),false),
        RunDay(RunCycle().Builder(5*60000,8,(2*60000),(2*60000)),false),
        RunDay(RunCycle().Builder(5*60000,8,(5*60000),(5*60000)),false),
        RunDay(RunCycle().Builder(5*60000,8,(5*60000),(5*60000)),false),
        RunDay(RunCycle().Builder(5*60000,8,(5*60000),(5*60000)),false)
    )

    private var selectedRun = getNextRunDay()


    fun getSelectedRunDay():RunDay{
        return selectedRun
    }
    fun getSelectedRunCycle():RunCycle{
        return selectedRun.runCycle
    }
    fun setSelectedRunDayByIndex(index:Int){
        selectedRun = defaultRunList[index]
    }

    fun getRoutine() = defaultRunList
    fun getNextRunDay():RunDay{
        defaultRunList.forEach { runDay ->
            if(!runDay.completed)
                return runDay
        }
        return defaultRunList.last()
    }
}
