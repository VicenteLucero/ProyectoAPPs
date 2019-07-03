package com.example.proyecto.db.models

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.ForeignKey.CASCADE
import android.arch.persistence.room.PrimaryKey
import android.support.annotation.NonNull
import java.sql.Time

@Entity(tableName = "schedule", foreignKeys = [ForeignKey(entity=Field::class, parentColumns = arrayOf("id"), childColumns = arrayOf("field"), onDelete = CASCADE)])
data class Schedules(
    @NonNull @ColumnInfo(name="field") val field: Int,
    @NonNull @ColumnInfo(name="rented") val rented: Boolean = false,
    @NonNull @ColumnInfo(name="hour") val hour: Int


) {
    @PrimaryKey(autoGenerate = true) var id: Int=0
}