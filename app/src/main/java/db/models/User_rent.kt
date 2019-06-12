package db.models

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.ForeignKey.CASCADE
import android.arch.persistence.room.PrimaryKey
import android.support.annotation.NonNull

@Entity(tableName = "user_rents",
    foreignKeys = [
        ForeignKey(entity = User::class, parentColumns = arrayOf("email"), childColumns = arrayOf("ownerEmail"), onDelete = CASCADE),
        ForeignKey(entity = Field::class, parentColumns = arrayOf("sport"), childColumns = arrayOf("sportName"), onDelete = CASCADE),
        ForeignKey(entity = Field::class, parentColumns = arrayOf("name"), childColumns = arrayOf("fieldName"), onDelete = CASCADE)])
data class User_rent(
    @NonNull @ColumnInfo(name= "ownerEmail") val ownerEmail: String,
    @NonNull @ColumnInfo(name= "fieldName") val fieldName: String,
    @NonNull @ColumnInfo(name= "sportName") val sportName: String,
    @NonNull @ColumnInfo(name= "startDate") val startDate: String,
    @NonNull @ColumnInfo(name= "finishDate") val finishDate: String,
    @NonNull @ColumnInfo(name= "players") val players: Int
) {
    @PrimaryKey(autoGenerate = true) val id: Int=0
}