package com.revature.dash.model.data.room

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.revature.dash.model.data.RunCycle

class RoomConverters {
    val gson = Gson()

    @TypeConverter
    fun stringToRunCycleList(data:String?):List<RunCycle?>?{
        return if(data == null)
            emptyList<RunCycle>()
        else
        {
            val type = object : TypeToken<List<RunCycle>>(){}.type
            gson.fromJson(data,type)
        }
    }
    @TypeConverter
    fun runCycleListToString(list:List<RunCycle?>?):String?{
        return gson.toJson(list)
    }
    @TypeConverter
    fun stringToRunCycle(data:String?):RunCycle?{
        return if(data == null)
            RunCycle()
        else
        {
            val type = object :TypeToken<RunCycle>(){}.type
            gson.fromJson(data,type)
        }
    }
    @TypeConverter
    fun runCycleToString(obj:RunCycle?):String?{
        return gson.toJson(obj)
    }
}