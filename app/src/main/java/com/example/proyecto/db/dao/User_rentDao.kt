package com.example.proyecto.db.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.example.proyecto.db.models.User_rent

@Dao
interface User_rentDao {
    @Query("SELECT * FROM user_rents WHERE owner LIKE :owner")
    fun getCurrentUserRents(owner: Int): List<User_rent>

    //CUANDO LOS DUEÑOS DE LOS CAMPOS QUIERAN VER LOS ARRIENDOS Y SUS DETALLES
    @Query("SELECT * FROM user_rents WHERE field LIKE :field")
    fun getFieldRents(field: Int): List<User_rent>

    @Insert
    fun insertAll(vararg user_rent: User_rent )

    @Delete
    fun deleteRent(user_rent: User_rent)
}