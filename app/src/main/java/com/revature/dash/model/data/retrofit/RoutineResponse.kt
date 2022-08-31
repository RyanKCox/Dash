package com.revature.dash.model.data.retrofit

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.revature.dash.model.data.RunDay

data class RoutineResponse(
    @SerializedName("routine")
    var routine:List<CycleResponse>
)