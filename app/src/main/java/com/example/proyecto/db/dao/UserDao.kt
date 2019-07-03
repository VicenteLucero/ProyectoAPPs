package com.example.proyecto.db.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.example.proyecto.db.models.User

@Dao
interface UserDao {
    @Query("SELECT * FROM users WHERE email LIKE :email AND password LIKE :password")
    fun getUser(email: String, password: String): User

    @Query("SELECT evaluations FROM users WHERE id LIKE :id")
    fun getPoints(id: Int): Int

    @Query("SELECT  longitude FROM users WHERE id LIKE :id")
    fun getUserLongitude(id: Int): List<Double>

    @Query("SELECT  latitude FROM users WHERE id LIKE :id")
    fun getUserLatitude(id: Int): List<Double>

    @Query( "SELECT photo FROM users WHERE id LIKE :id")
    fun getProfilePhoto(id: Int): String

    @Insert(onConflict = OnConflictStrategy.FAIL)
    fun insertAll(vararg user: User)

    @Update(onConflict = OnConflictStrategy.FAIL)
    fun update(vararg user: User)

    @Delete
    fun deleteUser(user: User)
}