package com.example.proyecto.db.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.example.proyecto.db.models.Evaluate_user

@Dao
interface Evaluate_userDao {
    @Query("SELECT * FROM evaluations WHERE user LIKE :user")
    fun getUserEvaluations(user: Int): List<Evaluate_user>

    @Insert
    fun insertEvaluation(vararg evaluate_user: Evaluate_user)

    @Insert
    fun insertAll(evaluate_user: List<Evaluate_user>)

    @Delete
    fun deleteEvaluation(evaluate_user: Evaluate_user)
}