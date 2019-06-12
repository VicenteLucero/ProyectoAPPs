package db.models

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.ForeignKey.CASCADE
import android.arch.persistence.room.PrimaryKey
import android.support.annotation.NonNull


@Entity(tableName = "users", foreignKeys = [ForeignKey(entity=Evaluate_user::class, parentColumns = arrayOf("points"), childColumns = arrayOf("´points"), onDelete = CASCADE)])
data class User(
    @NonNull @ColumnInfo(name = "name") val name: String,
    @NonNull @ColumnInfo(name = "last_name") val last_name: String,
    @NonNull @ColumnInfo(name = "email") val email: String,
    @NonNull @ColumnInfo(name = "password") val password: String,
    @NonNull @ColumnInfo(name = "points") val points: Int,
    @ColumnInfo(name = "latitude") val latitude: Double,
    @ColumnInfo(name = "longitude") val longitude: Double,
    @ColumnInfo(name = "photo") val photo: String?
) {
    @PrimaryKey(autoGenerate=true) var id: Int = 0
}