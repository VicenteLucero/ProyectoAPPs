package com.example.proyecto.db.models

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.support.annotation.NonNull


@Entity(tableName = "users")
data class User(
    @NonNull @ColumnInfo(name = "name") var name: String,
    @NonNull @ColumnInfo(name = "last_name") var last_name: String,
    @NonNull @ColumnInfo(name = "email") var email: String,
    @NonNull @ColumnInfo(name = "password") var password: String,
    @ColumnInfo(name = "latitude") var latitude: Double,
    @ColumnInfo(name = "longitude") var longitude: Double,
    @ColumnInfo(name = "evaluations") var evaluations: Int,
    @ColumnInfo(name = "photo") var photo: String?
) {
    @PrimaryKey(autoGenerate=true) var id: Int = 0
}