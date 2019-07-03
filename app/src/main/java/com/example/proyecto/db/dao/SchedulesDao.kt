package com.example.proyecto.db.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import android.arch.persistence.room.Update
import com.example.proyecto.db.models.Schedules


@Dao
interface SchedulesDao {
    @Query("SELECT * FROM schedule WHERE field LIKE :field")
    fun getSchedule(field:Int): List<Schedules>

    @Insert
    fun insertAll(vararg schedules: Schedules)

    @Update
    fun updateSchedule(schedules: Schedules)


}