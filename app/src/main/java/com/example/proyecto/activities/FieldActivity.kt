package com.example.proyecto.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import com.example.proyecto.R
import com.example.proyecto.adapter.ScheduleAdapter
import com.example.proyecto.db.AppDatabase
import com.example.proyecto.db.models.Post
import com.example.proyecto.db.models.Schedules
import com.example.proyecto.db.models.User
import kotlinx.android.synthetic.main.activity_field.*
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.Exception

class FieldActivity : AppCompatActivity() {

    private lateinit var fieldName: String
    private var latitude: Double = 0.0
    private var longitude: Double = 0.0
    private var current_id = 0
    private lateinit var itemsAdapter: ScheduleAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_field)

        fieldName = intent.getStringExtra("NAME")
        latitude = intent.getDoubleExtra("LATITUDE",0.0)
        longitude = intent.getDoubleExtra("LONGITUDE", 0.0)
        loadFieldDetails()
        setListener()
    }

    private fun setListener() {
        seeRentButton.setOnClickListener {
            rentList.visibility = View.VISIBLE
            //matchesList.visibility = View.INVISIBLE
            loadSchedules()
        }
        matchButton.setOnClickListener {
            rentList.visibility = View.INVISIBLE
            //matchesList.visibility = View.VISIBLE
            loadPosts()
        }

        rentList.setOnItemClickListener{ parent: AdapterView<*>?, view: View?, position: Int, id: Long ->
            Toast.makeText(this, "Clicked item : $position",Toast.LENGTH_SHORT).show()
            val schedule = (rentList.adapter).getItem(position) as Schedules
            startActivity(
                Intent(this, RentActivity::class.java).
                    putExtra("SCHEDULE_ID", schedule.id))
        }

    }

    private fun setListOnClickListener() {
        rentList.setOnItemClickListener { _, _, position, _ ->
            val selectedSchedule = (rentList.adapter).getItem(position) as Schedules
            startActivity(
                Intent(baseContext, RentActivity::class.java).
                    putExtra("SCHEDULE_ID", selectedSchedule.id))
        }
    }

    private fun loadSchedules() {
        val schedulesDao = AppDatabase.getDatabase(this).scheduleDao()
        GlobalScope.launch(Dispatchers.IO) {
            val schedules = schedulesDao.getSchedule(current_id)
            launch(Dispatchers.Main) {
                // replaces uiThread (runs on UIThread)
                itemsAdapter = ScheduleAdapter(this@FieldActivity, ArrayList(schedules))
                rentList.adapter = itemsAdapter

            }

        }
    }

    private fun loadPosts() {
        val postDao = AppDatabase.getDatabase(this).postDao()
        val scheduleDao = AppDatabase.getDatabase(this).scheduleDao()
        GlobalScope.launch(Dispatchers.IO){

            var myList: MutableList<Post> = mutableListOf<Post>()

            val posts = postDao.
                selectAllActive()

            val schedules = scheduleDao.getSchedule(current_id)

            for(i in posts){
                for(j in schedules){
                    if (i.event == j.id){
                        myList.add(i)
                    }
                }
            }
        }
    }

    private fun loadFieldDetails() {
        val fieldDao = AppDatabase.getDatabase(this).fieldDao()
        val sportDao = AppDatabase.getDatabase(this).sportDao()
        GlobalScope.launch(Dispatchers.IO){
            val field = fieldDao.getFieldByData(fieldName,latitude,longitude)
            current_id = field.id
            val sport = sportDao.getSport(field.sport)
            launch(Dispatchers.Main){
                fieldTitle.text=fieldName
                priceValue.text=field.priceHour.toString()
                descriptionValue.text=sport.description
            }

        }
    }


    private fun createNewScheduleObject(): User {
        val userEmail = emailEditText.text.toString()
        val name = nameEditText.text.toString()
        val lastName = lastNameEditText.text.toString()
        val userPassword = passwordEditText.text.toString()

        return User(name, lastName, userEmail, userPassword, 0.0, 0.0,0, null)
    }

    private fun storeNewUser(user: User){

        val userDao = AppDatabase.getDatabase(this).userDao()
        GlobalScope.launch(Dispatchers.IO) {  // replaces doAsync (runs on another thread)
            try {
                userDao.insertAll(user)
                launch(Dispatchers.Main) {
                    Toast.makeText(applicationContext, "Saved successfully", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                launch(Dispatchers.Main) {
                    Log.d("ERROR", "Error storing complaint ${e.message}")
                    Toast.makeText(applicationContext, "Error storing complaint ${e.message}" , Toast.LENGTH_LONG).show()
                }
            }
        }

    }


}
