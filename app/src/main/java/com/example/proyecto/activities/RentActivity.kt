package com.example.proyecto.activities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey.CASCADE
import android.arch.persistence.room.PrimaryKey
import android.graphics.BitmapFactory
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.NonNull
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.proyecto.CredentialsManager
import com.example.proyecto.R
import com.example.proyecto.db.AppDatabase
import com.example.proyecto.db.models.Post
import com.example.proyecto.db.models.Schedules
import com.example.proyecto.db.models.User
import com.example.proyecto.db.models.User_rent
import kotlinx.android.synthetic.main.activity_rent.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File
import java.util.logging.Logger.global

class RentActivity : AppCompatActivity() {

    private var currentScheduleId: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rent)

        getCurrentScheduleValues()
        setViewContent()
        setListeners()
    }

    private fun getCurrentScheduleValues() {
        currentScheduleId = intent.getIntExtra("SCHEDULE_ID", 0)
    }


    private fun setViewContent() {
        GlobalScope.launch(Dispatchers.IO) {
            val currentUserEmail = CredentialsManager.getInstance(baseContext).loadUser()!!.first
            val appDatabase = AppDatabase.getDatabase(baseContext)
            val scheduleDao = appDatabase.scheduleDao()
            val User_rentDao = appDatabase.user_rentDao()
            val selectedSchedule = scheduleDao.getOneSchedule(currentScheduleId)


            launch(Dispatchers.Main) {
                fieldTextView.text = selectedSchedule.field.toString()
                dateTextView.text = selectedSchedule.hour.toString()

            }
        }
    }

    private fun setListeners() {
        setCreatePostButtonListener(sendPostButton)
    }

        private fun setCreatePostButtonListener(view: View) {
        view.setOnClickListener {
            // userEmail could be cached at the CredentialsManager when logging in
            val currentUserEmail = CredentialsManager.getInstance(baseContext).loadUser()!!.first
            val playersText = playersEditText.text.toString()
            val descriptionText = descriptionEditText.text.toString()
            val sportText = sportEditText.text.toString()



            val newUserRent = User_rent(currentUserEmail, currentScheduleId, sportText, playersText.toInt())
            GlobalScope.launch(Dispatchers.IO) {
                try {
                    AppDatabase.getDatabase(baseContext).user_rentDao().insertAll(newUserRent)
                    launch(Dispatchers.Main) {
                        Toast.makeText(baseContext, "Rented!", Toast.LENGTH_LONG).show()
                    }
                } catch (e: Exception) {
                    launch(Dispatchers.Main) {
                        Log.d("ERROR", "Error storing rent ${e.message}")
                        Toast.makeText(baseContext, "Error storing rent ${e.message}", Toast.LENGTH_LONG).show()
                    }
                }


                try {
                    val userRentId = AppDatabase.getDatabase(baseContext).user_rentDao().getScheduleRent(currentScheduleId).id
                    val newPost = Post(userRentId, "Max", descriptionText, playersText.toInt())
                    AppDatabase.getDatabase(baseContext).postDao().insertPost(newPost)

                    launch(Dispatchers.Main) {
                        Toast.makeText(baseContext, "Posted and Rented!", Toast.LENGTH_LONG).show()
                    }
                } catch (e: Exception) {
                    launch(Dispatchers.Main) {
                        Log.d("ERROR", "Error storing post ${e.message}")
                        Toast.makeText(baseContext, "Error storing post ${e.message}", Toast.LENGTH_LONG).show()
                    }
                }
            }





        }
    }



}
