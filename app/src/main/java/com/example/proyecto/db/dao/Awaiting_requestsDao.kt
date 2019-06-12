package com.example.proyecto.db.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.example.proyecto.db.models.Awaiting_requests

@Dao
interface Awaiting_requestsDao {
    @Query("SELECT * FROM requests WHERE post LIKE :post_id")
    fun getPostrequests(post_id: Int): List<Awaiting_requests>

    @Insert
    fun insertAll(vararg awaiting_requests: Awaiting_requests)

    @Delete
    fun deleteRequest(awaiting_requests: Awaiting_requests)
}