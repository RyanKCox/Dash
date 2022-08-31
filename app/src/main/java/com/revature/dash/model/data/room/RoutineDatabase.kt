package com.revature.dash.model.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.revature.dash.model.data.RunDay

@Database(entities = [RunDay::class], version = 1)
@TypeConverters(RoomConverters::class)
abstract class RoutineDatabase : RoomDatabase() {
    abstract fun getRoutineDao():RoutineDao
}