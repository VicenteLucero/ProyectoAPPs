package com.example.proyecto.db

import android.arch.persistence.db.SupportSQLiteDatabase
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
    Awaiting_requests::class,
    AcceptedRequest::class,
    PaymentMethod::class,
    Schedules::class],
    version = 1, exportSchema = false)
public abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun postDao(): PostDao
    abstract fun sportDao(): SportDao
    abstract fun user_rentDao(): User_rentDao
    abstract fun fieldDao(): FieldDao
    abstract fun evaluate_userDao(): Evaluate_userDao
    abstract fun awaiting_requestsDao(): Awaiting_requestsDao
    abstract fun acceptedRequestDao(): AcceptedRequestDao
    abstract fun payMethodDao(): PaymentMethodDao
    abstract fun scheduleDao(): SchedulesDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: getDatabase(context).also { INSTANCE = it }
            }

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
                ).addCallback(object : Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        // moving to a new thread
                        ioThread {
                            //val PREPOPULATE_DATA = listOf(Field("Cancha U", 5, 10.1, 10.9, 1, 10), Field("Cancha Football", 10, 20.0,10.0, 1,11))
                            //val PREPOPULATE_DATA = listOf(Evaluate_user(1, 5, 10), Evaluate_user(2, 10, 20))

                            //getInstance(context).fieldDao()
                                //.insertAllField(PREPOPULATE_DATA)
                        }
                    }
                }).build()

                INSTANCE = instance
                return instance
            }
        }

        fun destroyInstance(){
            INSTANCE = null
        }
    }
}