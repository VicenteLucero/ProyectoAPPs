package com.example.proyecto.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.example.proyecto.db.dao.*
import com.example.proyecto.db.models.*

@Database(entities = [User::class,
    Post::class,
    Sport::class,
    User_rent::class,
    Field::class,
    Evaluate_user::class,
    Awaiting_requests::class],
    version = 1, exportSchema = false)
public abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun postDao(): PostDao
    abstract fun sportDao(): SportDao
    abstract fun user_rentDao(): User_rentDao
    abstract fun fieldDao(): FieldDao
    abstract fun evaluate_userDao(): Evaluate_userDao
    abstract fun awaiting_requestsDao(): Awaiting_requestsDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase{
            val tempInstance = INSTANCE
            if (tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "AppDatabase"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}