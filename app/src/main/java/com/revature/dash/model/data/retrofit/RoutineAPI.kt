package com.revature.dash.model.data.retrofit

import io.reactivex.Observable
import retrofit2.http.GET

interface RoutineAPI {
    @GET("/routine")
    fun getDefaultRoutine(): Observable<List<CycleResponse>>
}