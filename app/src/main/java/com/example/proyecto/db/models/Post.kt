package com.example.proyecto.db.models

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.ForeignKey.CASCADE
import android.arch.persistence.room.PrimaryKey
import android.support.annotation.NonNull
import java.sql.Time

@Entity(tableName = "posts", foreignKeys = [
    ForeignKey(entity= User_rent::class, parentColumns = arrayOf("id"), childColumns = arrayOf("event"), onDelete = CASCADE)])
data class Post(
    @NonNull @ColumnInfo(name= "event") val event: Int,
    @NonNull @ColumnInfo(name= "owner") val owner: String,
    @NonNull @ColumnInfo(name= "title") val title: String,
    @NonNull @ColumnInfo(name= "required") val required: Int
) {
    @PrimaryKey(autoGenerate = true) var id: Int=0
}