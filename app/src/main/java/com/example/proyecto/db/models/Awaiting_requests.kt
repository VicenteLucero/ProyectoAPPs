package com.example.proyecto.db.models

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.ForeignKey.CASCADE
import android.arch.persistence.room.PrimaryKey
import android.support.annotation.NonNull


@Entity(tableName = "requests", foreignKeys = [
    ForeignKey(entity=Post::class, parentColumns = arrayOf("id"), childColumns = arrayOf("post"), onDelete = CASCADE),
    ForeignKey(entity =User::class, parentColumns = arrayOf("id"), childColumns = arrayOf("requester"), onDelete = CASCADE)
])
data class Awaiting_requests (
    @NonNull @ColumnInfo(name="requester") val requester: Int,
    @NonNull @ColumnInfo(name="post") val post: Int,
    @NonNull @ColumnInfo(name="message") val message: String
){
    @PrimaryKey(autoGenerate = true) var id: Int=0
}