package com.example.proyecto.db.models

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.ForeignKey.CASCADE
import android.arch.persistence.room.PrimaryKey
import android.support.annotation.NonNull


@Entity(tableName = "users", foreignKeys = [ForeignKey(entity=Evaluate_user::class, parentColumns = arrayOf("id"), childColumns = arrayOf("evaluations"), onDelete = CASCADE)])
data class User(
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "last_name") val last_name: String,
    @NonNull @ColumnInfo(name = "email") val email: String,
    @NonNull @ColumnInfo(name = "password") val password: String,
    @ColumnInfo(name = "latitude") val latitude: Double,
    @ColumnInfo(name = "longitude") val longitude: Double,
    @ColumnInfo(name = "evaluations") val evaluations: Int,
    @ColumnInfo(name = "photo") val photo: String?
) {
    @PrimaryKey(autoGenerate=true) var id: Int = 0
}