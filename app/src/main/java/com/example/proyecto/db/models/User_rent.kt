package com.example.proyecto.db.models

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.ForeignKey.CASCADE
import android.arch.persistence.room.PrimaryKey
import android.support.annotation.NonNull

@Entity(tableName = "user_rents",
    foreignKeys = [
        ForeignKey(entity = User::class, parentColumns = arrayOf("id"), childColumns = arrayOf("owner"), onDelete = CASCADE),
        ForeignKey(entity = Schedules::class, parentColumns = arrayOf("id"), childColumns = arrayOf("schedule"), onDelete = CASCADE)])


data class User_rent(
    @NonNull @ColumnInfo(name= "owner") val ownerEmail: String,
    @NonNull @ColumnInfo(name= "schedule") val fieldName: Int,
    @NonNull @ColumnInfo(name= "sport") val sportName: String,
    @NonNull @ColumnInfo(name= "players") val players: Int
) {
    @PrimaryKey(autoGenerate = true) var id: Int=0
}