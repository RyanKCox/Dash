package com.revature.dash.model.data

import java.text.SimpleDateFormat

data class RunItem(
    val type:String,
    val time:Long
)

class RunCycle {
    lateinit var cycle:List<RunItem/*Pair<String,Long>*/>
    lateinit var description:String
    private var isCompleted = false

    fun builder(warmupTime:Long, numCycles:Int, runTime:Long, walkTime:Long):RunCycle{
        val tempCycle = RunCycle()
        val tempList = mutableListOf(
            RunItem("WarmUp",warmupTime)
        )
        (1..numCycles).forEach { _ ->
            tempList.add(RunItem("Run",runTime))
            tempList.add(RunItem("Walk",walkTime))
        }
        tempCycle.cycle = tempList

        var tempDescription = ""
        val totalTime = warmupTime + (numCycles * runTime) + (numCycles * walkTime)
        if(warmupTime > 0L)
            tempDescription += "Start with a WarmUp walk for ${ SimpleDateFormat("mm:ss").format(warmupTime)} minutes. "
        tempDescription += "Alternate between running for ${ SimpleDateFormat("mm:ss").format(runTime)} " +
                "minutes and walking for ${ SimpleDateFormat("mm:ss").format(walkTime)} minutes. " +
                "For a total of ${SimpleDateFormat("mm:ss").format(totalTime)} minutes"

        tempCycle.description = tempDescription
        tempCycle.isCompleted = false

        return tempCycle
    }

    fun getTotalTime():Long{
        var time = 0L
        cycle.forEach {
            time += it.time
        }
        return time
    }
}
