package com.example.proyecto.db.models

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey
import android.support.annotation.NonNull

@Entity(tableName = "acceptedRequests", foreignKeys = [
    ForeignKey(entity=Post::class, parentColumns = arrayOf("id"), childColumns = arrayOf("post"), onDelete = ForeignKey.CASCADE),
    ForeignKey(entity =User::class, parentColumns = arrayOf("id"), childColumns = arrayOf("requester"), onDelete = ForeignKey.CASCADE)
])
data class AcceptedRequest (
    @NonNull @ColumnInfo(name="requester") val requester: Int,
    @NonNull @ColumnInfo(name="post") val post: Int,
    @NonNull @ColumnInfo(name="message") val message: String
){
    @PrimaryKey(autoGenerate = true) var id: Int=0
}