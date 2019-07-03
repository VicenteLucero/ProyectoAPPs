package com.example.proyecto.db.dao

import android.arch.persistence.room.*
import com.example.proyecto.db.models.Sport

@Dao
interface SportDao {
    @Query("SELECT * FROM sports")
    fun getAllSports(): List<Sport>

    @Query("SELECT * FROM sports WHERE id LIKE :id")
    fun getSport(id: Int): Sport

    @Insert
    fun insertAll(vararg sport: Sport)

    @Update
    fun updateSport(sport: Sport)

    @Delete
    fun deleteSport(sport: Sport)
}