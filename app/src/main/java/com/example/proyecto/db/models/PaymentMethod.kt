package com.example.proyecto.db.models

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.ForeignKey.CASCADE
import android.arch.persistence.room.PrimaryKey
import android.support.annotation.NonNull


@Entity(tableName="paymethods", foreignKeys = [
    ForeignKey(entity = User::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("owner"),
        onDelete = CASCADE)])

data class PaymentMethod(
    @NonNull @ColumnInfo(name="owner") val owner: Int,
    @NonNull @ColumnInfo(name="number") val number: Int,
    @NonNull @ColumnInfo(name="type") val type: String,
    @NonNull @ColumnInfo(name="card_name") val card_name: String
) {
    @PrimaryKey(autoGenerate = true) var id: Int = 0
}