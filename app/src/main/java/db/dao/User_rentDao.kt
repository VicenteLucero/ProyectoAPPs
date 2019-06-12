package db.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import db.models.User_rent

@Dao
interface User_rentDao {
    @Query("SELECT * FROM user_rents WHERE ownerEmail LIKE :email")
    fun getCurrentUserRents(email: String): List<User_rent>

    //CUANDO LOS DUEÑOS DE LOS CAMPOS QUIERAN VER LOS ARRIENDOS Y SUS DETALLES
    @Query("SELECT * FROM user_rents WHERE fieldName LIKE :fieldName")
    fun getFieldRents(fieldName: String)


}