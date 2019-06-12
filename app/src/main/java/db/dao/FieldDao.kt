package db.dao

import android.arch.persistence.room.*
import db.models.Field

@Dao
interface FieldDao {
    @Query("SELECT * FROM fields")
    fun getAllFields(): List<Field>

    @Query( "SELECT * FROM fields WHERE sport LIKE :sport")
    fun getBySport(sport: String): List<Field>

    @Query("SELECT * FROM fields WHERE name LIKE :name")
    fun getByName(name: String)

    @Insert
    fun insertField(vararg field: Field)

    @Update
    fun updateField(field: Field)

    @Delete
    fun deleteField(field: Field)
}