package com.example.proyecto.db.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.example.proyecto.db.models.PaymentMethod


@Dao
interface PaymentMethodDao {
    @Query("SELECT * FROM paymethods")
    fun getAllMethods(): List<PaymentMethod>

    @Query("SELECT * FROM paymethods WHERE owner LIKE :id")
    fun getUserMethods(id: Int): List<PaymentMethod>

    @Insert
    fun insertAll(vararg paymentMethod: PaymentMethod)

    @Delete
    fun deleteMethod(paymentMethod: PaymentMethod)
}