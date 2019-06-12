package db.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import db.models.User_rent

@Dao
interface User_rentDao {
    @Query("SELECT * FROM user_rents WHERE ownerEmail LIKE :email")
    fun getCurrentUserRents(email: String): List<User_rent>

    //CUANDO LOS DUEÑOS DE LOS CAMPOS QUIERAN VER LOS ARRIENDOS Y SUS DETALLES
    @Query("SELECT * FROM user_rents WHERE fieldName LIKE :fieldName")
    fun getFieldRents(fieldName: String)

    @Insert
    fun insertAll(vararg user_rent: User_rent )

    @Delete
    fun deleteRent(user_rent: User_rent)
}