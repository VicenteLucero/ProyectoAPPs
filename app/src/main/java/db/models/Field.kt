package db.models

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey
import android.support.annotation.NonNull

@Entity(tableName = "fields", foreignKeys =([ForeignKey(entity = Sport::class, parentColumns = arrayOf("name"), childColumns = arrayOf("sport"), onDelete = ForeignKey.CASCADE)]))
data class Field (
    @NonNull @ColumnInfo(name= "name") val name: String,
    @NonNull @ColumnInfo(name= "priceHour") val priceHour: Int,
    @NonNull @ColumnInfo(name= "latitude") val latitude: Double,
    @NonNull @ColumnInfo(name= "longitude") val longitude: Double,
    @NonNull @ColumnInfo(name= "sport") val sport: String,
    @NonNull @ColumnInfo(name= "maxPlayers") val maxPlayers: Int
){
    @PrimaryKey(autoGenerate = true) val id: Int=0
}