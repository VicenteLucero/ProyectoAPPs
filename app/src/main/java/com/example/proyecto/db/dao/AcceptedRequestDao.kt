package com.example.proyecto.db.dao

import android.arch.persistence.room.*
import com.example.proyecto.db.models.AcceptedRequest

@Dao
interface AcceptedRequestDao {
    @Query("SELECT * FROM acceptedRequests")
    fun getAll(): List<AcceptedRequest>

    @Query("SELECT * FROM acceptedRequests WHERE requester LIKE :id")
    fun getUserAccepted(id: Int): List<AcceptedRequest>

    @Insert
    fun insertAll(vararg acceptedRequest: AcceptedRequest)

    @Delete
    fun delete(acceptedRequest: AcceptedRequest)
}