package com.example.proyecto.db.dao

import android.arch.persistence.room.*
import com.example.proyecto.db.models.Sport

@Dao
interface SportDao {
    @Query("SELECT * FROM sports")
    fun getAllSports(): List<Sport>

    @Query("SELECT description FROM sports WHERE name LIKE :name")
    fun getDescription(name: String): String

    @Insert
    fun insertAll(vararg sport: Sport)

    @Update
    fun updateSport(sport: Sport)

    @Delete
    fun deleteSport(sport: Sport)
}