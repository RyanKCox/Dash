package com.revature.dash.model.data.retrofit

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class CycleResponse(
    @SerializedName("warmup")
    @Expose
    val warmup:Float,
    @SerializedName("cycles")
    @Expose
    val numCycles:Float,
    @SerializedName("run_time")
    @Expose
    val runTime:Float,
    @SerializedName("rest_time")
    @Expose
    val walkTime:Float
)
