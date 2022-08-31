package com.revature.dash.model.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.revature.dash.model.data.RunDay
import io.reactivex.Observable

@Dao
interface RoutineDao {
    @Query("SELECT * FROM routine")
    fun fetchRoutine(): Observable<List<RunDay>>

//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    fun insertRunDay(runDay: RunDay): Long

    @Query("DELETE FROM routine WHERE id=:id")
    fun deleteRunDay(id: Long)
}