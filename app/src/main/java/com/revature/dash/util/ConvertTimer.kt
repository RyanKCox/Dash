package com.revature.dash.util

fun convertTimer(time:Long):String{
    val min = time/60000
    val sec = time %60000
    return "$min:$sec"
}