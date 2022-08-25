package com.revature.dash.domain.routine

import com.revature.dash.model.data.RunDay
import com.revature.dash.model.data.RunItem

class RunRoutine{
    private val runRepo = listOf(
        beginnerRun, intermediateRun, advancedRun, expertRun
    )

    private val defaultRunList = listOf(
        RunDay(0,false),
        RunDay(0,false),
        RunDay(0,false),
        RunDay(1,false),
        RunDay(1,false),
        RunDay(1,false),
        RunDay(2,false),
        RunDay(2,false),
        RunDay(2,false),
        RunDay(3,false),
        RunDay(3,false),
        RunDay(3,false)
    )

    fun getRoutine() = defaultRunList

    fun getNextRunID():Int{
        defaultRunList.forEachIndexed { index, runDay ->
            if(!runDay.completed)
                return index
        }
        return defaultRunList.lastIndex
    }
    fun getRunByID(id:Int):RunItem{
        return runRepo[id]
    }
//    fun getRunByDay(runDay: RunDay):RunItem{
//        return runRepo[runDay.runItemID]
//    }
}

val beginnerRun = RunItem(
    0,
    "Five minute warm-up walk followed by alternating between 60 second jog and 90 second brisk walk."
)
val intermediateRun = RunItem(
    1,
    "Five minute warm-up walk followed by alternating between 90 second jog and 2 minute brisk walk."
)
val advancedRun = RunItem(
    2,
    "Five minute warm-up walk followed by alternating between 2 minute jog and 2 minute brisk walk."
)
val expertRun = RunItem(
    3,
    "Five minute warm-up walk followed by alternating between 5 minute jog and 5 minute brisk walk."
)