package com.revature.dash.model.data

data class RunItem(
    val id:Int,
    val description:String,
    val warmup:Warmup,
    val routine:List<RunCycle>
){
    fun getTotalTime():Long{
        var totalTime = warmup.time
        routine.forEach {
            totalTime += it.runTime + it.walkTime
        }
        return totalTime
    }
    fun getDescriptionWithTime():String{
        return description + " Total Time ${(getTotalTime()/60000)} minutes."
    }
}