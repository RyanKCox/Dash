package com.revature.dash.model.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "routine")
data class RunDay(
    @PrimaryKey(autoGenerate = true)
    val id:Long = 0,

    val runCycle: RunCycle = RunCycle(),
    var completed:Boolean = false
)