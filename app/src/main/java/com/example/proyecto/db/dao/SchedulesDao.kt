package com.example.proyecto.db.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import android.arch.persistence.room.Update
import com.example.proyecto.db.models.Schedules
import com.example.proyecto.db.models.Sport


@Dao
interface SchedulesDao {
    @Query("SELECT * FROM schedule WHERE field LIKE :field")
    fun getSchedule(field:Int): List<Schedules>


    @Query("SELECT * FROM schedule WHERE id LIKE :id")
    fun getOneSchedule(id: Int): Schedules

    @Insert
    fun insertAll(vararg schedules: Schedules)

    @Update
    fun updateSchedule(schedules: Schedules)


}