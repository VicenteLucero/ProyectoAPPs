package db.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import db.models.User

@Dao
interface UserDao {
    @Query("SELECT * FROM users WHERE email LIKE :email AND password LIKE :password")
    fun getUser(email: String, password: String): User?

    @Query("SELECT points FROM users WHERE id LIKE :id")
    fun getPoints(id: Int): List<Int>

    @Query("SELECT longitude , latitude FROM users WHERE id LIKE :id")
    fun getUserLocation(id: Int): List<Double>

    @Query( "SELECT photo FROM users WHERE id LIKE :id")
    fun getProfilePhoto(id: Int): String?

    @Insert
    fun insertAll(vararg user: User)
}