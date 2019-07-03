package com.example.proyecto.db.dao

import android.arch.persistence.room.*
import com.example.proyecto.db.models.Field

@Dao
interface FieldDao {
    @Query("SELECT * FROM fields")
    fun getAllFields(): List<Field>

    @Query("SELECT * FROM fields WHERE id LIKE :id")
    fun getField(id: Int): Field

    @Query("SELECT * FROM fields WHERE name LIKE :name AND latitude LIKE :latitude AND longitude LIKE :longitude")
    fun getFieldByData(name: String, latitude: Double, longitude: Double): Field

    @Query( "SELECT * FROM fields WHERE sport LIKE :sport")
    fun getBySport(sport: String): List<Field>

    @Query("SELECT * FROM fields WHERE name LIKE :name")
    fun getByName(name: String): List<Field>

    @Insert
    fun insertAllField(field: List<Field>)

    @Insert
    fun insertField(vararg field: Field)

    @Update
    fun updateField(field: Field)

    @Delete
    fun deleteField(field: Field)


}