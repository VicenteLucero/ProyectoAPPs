package db.models

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.support.annotation.NonNull


@Entity(tableName = "sports")
class Sport(
    @NonNull @ColumnInfo(name="name") val name: String,
    @NonNull @ColumnInfo(name= "description") val description: String
) {
    @PrimaryKey(autoGenerate = true) val id: Int=0
}