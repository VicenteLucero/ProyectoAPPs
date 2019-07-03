package com.example.proyecto.activities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey.CASCADE
import android.arch.persistence.room.PrimaryKey
import android.content.Intent
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
import com.example.proyecto.db.models.*
import kotlinx.android.synthetic.main.activity_rent.*
import kotlinx.android.synthetic.main.activity_request.*
import kotlinx.android.synthetic.main.list_item_post.*
import kotlinx.android.synthetic.main.list_item_post.titleTextView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File
import java.util.logging.Logger.global

class RequestActivity : AppCompatActivity() {

    private var postId: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_request)

        getCurrentPostValues()
        setViewContent()
        setListeners()
    }

    private fun getCurrentPostValues() {
        postId = intent.getIntExtra("POST_ID", 0)
    }


    private fun setViewContent() {
        GlobalScope.launch(Dispatchers.IO) {
            val currentUserEmail = CredentialsManager.getInstance(baseContext).loadUser()!!.first

            val appDatabase = AppDatabase.getDatabase(baseContext)
            val scheduleDao = appDatabase.scheduleDao()
            val User_rentDao = appDatabase.user_rentDao()
            val postDao = appDatabase.postDao()
            val userDao = appDatabase.userDao()
            val selectedPost = postDao.getPost(postId)
            val currentUserID = userDao.getUserByEmail(currentUserEmail).id


                launch(Dispatchers.Main) {
                titleTextView.text = selectedPost.title
                requiredTextView.text = selectedPost.required.toString()

            }
        }
    }

    private fun setListeners() {
        setCreatePostButtonListener(sendRequestButton)
    }

    private fun setCreatePostButtonListener(view: View) {
        view.setOnClickListener {
            // userEmail could be cached at the CredentialsManager when logging in
            val currentUserEmail = CredentialsManager.getInstance(baseContext).loadUser()!!.first
            val messageText = messageEditText.text.toString()
            GlobalScope.launch(Dispatchers.IO) {
                try {
                    AppDatabase.getDatabase(baseContext).awaiting_requestsDao().insertAll(Awaiting_requests(AppDatabase.getDatabase(baseContext).userDao().getUserByEmail(currentUserEmail).id, postId, messageText))
                    launch(Dispatchers.Main) {
                        Toast.makeText(baseContext, "Sent!", Toast.LENGTH_LONG).show()
                    }
                } catch (e: Exception) {
                    launch(Dispatchers.Main) {
                        Log.d("ERROR", "Error storing request ${e.message}")
                        Toast.makeText(baseContext, "Error storing request ${e.message}", Toast.LENGTH_LONG).show()
                    }
                }

                val intent = Intent(applicationContext, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)
                finish()
            }





        }
    }



}