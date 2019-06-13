package com.example.proyecto.db.models

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.ForeignKey.CASCADE
import android.arch.persistence.room.PrimaryKey
import android.support.annotation.NonNull


@Entity(tableName = "evaluations", foreignKeys = [ForeignKey(entity=User::class, parentColumns = arrayOf("id"), childColumns = arrayOf("user"), onDelete = CASCADE),
                                                    ForeignKey(entity=User_rent::class, parentColumns = arrayOf("id"), childColumns = arrayOf("event"), onDelete = CASCADE)])
data class Evaluate_user (
    @NonNull @ColumnInfo(name= "user") val user: Int,
    @NonNull @ColumnInfo(name= "event") val event: Int,
    @NonNull @ColumnInfo(name= "points") val points: Int
){
    @PrimaryKey(autoGenerate = true) var id: Int=0
}