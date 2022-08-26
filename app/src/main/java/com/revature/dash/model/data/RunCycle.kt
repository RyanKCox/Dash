package com.revature.dash.model.data

import java.text.SimpleDateFormat

class RunCycle(
){
    lateinit var cycle:List<Pair<String,Long>>
    lateinit var description:String
    var isCompleted = false

    fun Builder(warmupTime:Long,numCycles:Int,runTime:Long,walkTime:Long):RunCycle{
        val tempCycle = RunCycle()
        val tempList = mutableListOf(
            Pair("WarmUp",warmupTime)
        )
        (0..numCycles).forEach { _ ->
            tempList.add(Pair("Run",runTime))
            tempList.add(Pair("Walk",walkTime))
        }
        tempCycle.cycle = tempList

        var tempDescription = ""
        if(warmupTime > 0L)
            tempDescription += "Start with a WarmUp walk for ${ SimpleDateFormat("mm:ss").format(warmupTime)} minutes. "
        tempDescription += "Alternate between running for ${ SimpleDateFormat("mm:ss").format(runTime)} " +
                "minutes and walking for ${ SimpleDateFormat("mm:ss").format(runTime)} minutes."

        tempCycle.description = tempDescription
        tempCycle.isCompleted = false

        return tempCycle
    }

    fun getTotalTime():Long{
        var time = 0L
        cycle.forEach {
            time += it.second
        }
        return time
    }
}
