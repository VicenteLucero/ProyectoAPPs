package com.example.proyecto.activities

import android.app.Activity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;
import android.util.Log
import android.widget.Toast
import com.example.proyecto.EmailValidator
import com.example.proyecto.R
import com.example.proyecto.db.AppDatabase
import com.example.proyecto.db.dao.UserDao
import com.example.proyecto.db.models.User

import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_main.*

import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_register.emailEditText
import kotlinx.android.synthetic.main.activity_register.passwordEditText
import kotlinx.android.synthetic.main.activity_register.registerButton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.Exception

class RegisterActivity : AppCompatActivity() {

    private var db: AppDatabase? = null
    private var UserDao: UserDao? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        setListeners()


    }

    private fun setListeners() {
        registerButton.setOnClickListener{
            val userEmail = emailEditText.text.toString()
            val name = nameEditText.text.toString()
            val lastName = lastNameEditText.text.toString()
            val userPassword = passwordEditText.text.toString()
            val userPassword2 = passwordEditText2.text.toString()
            when {
                // Check Email
                !EmailValidator.isValidEmail(userEmail) ->
                    Toast.makeText(this, R.string.emailError, Toast.LENGTH_LONG).show()
                // Check Password
                userPassword.isNullOrEmpty() ->
                    Toast.makeText(this, R.string.passwordError, Toast.LENGTH_LONG).show()
                // Check if both passwords are the same
                userPassword != userPassword2 ->
                    Toast.makeText(this, R.string.passwordDifError, Toast.LENGTH_LONG).show()
                // Check name
                name.isNullOrEmpty() ->
                    Toast.makeText(this, R.string.nameError, Toast.LENGTH_LONG).show()
                // Check Password
                lastName.isNullOrEmpty() ->
                    Toast.makeText(this, R.string.lastNameError, Toast.LENGTH_LONG).show()

                // Go to MainActivity
                else -> {
                   val newUser = createNewUserObject()
                    storeNewUser(newUser)
                    // Add data to invoking intent
                    intent.apply {
                        putExtra("EMAIL", userEmail)
                        putExtra("PASSWORD", userPassword)
                    }
                    // Set response
                    setResult(Activity.RESULT_OK, intent)
                    finish()
                }
            }
        }
    }

    private fun createNewUserObject(): User {
        val userEmail = emailEditText.text.toString()
        val name = nameEditText.text.toString()
        val lastName = lastNameEditText.text.toString()
        val userPassword = passwordEditText.text.toString()

        return User(name, lastName, userEmail, userPassword, 0.0, 0.0,0, null)
    }

    private fun storeNewUser(user: User){

        val userDao = AppDatabase.getDatabase(context = this).userDao()
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


