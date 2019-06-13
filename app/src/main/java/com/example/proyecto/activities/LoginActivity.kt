package com.example.proyecto.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast
import com.example.proyecto.EmailValidator
import com.example.proyecto.R
import com.example.proyecto.db.AppDatabase


import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setListeners()
    }

    private fun setListeners() {
        val userDao = AppDatabase.getDatabase(context = this).userDao()


        loginButton.setOnClickListener {
            val userEmail = emailEditText.text.toString()
            val userPassword = passwordEditText.text.toString()

            val es = userDao.getUser(userEmail, userPassword)
            when {
                // Check Email
                !EmailValidator.isValidEmail(userEmail) ->
                    Toast.makeText(this, R.string.emailError, Toast.LENGTH_LONG).show()
                // Check Password
                userPassword.isNullOrEmpty() ->
                    Toast.makeText(this, R.string.passwordError, Toast.LENGTH_LONG).show()
                //check if user in db
                es == null ->
                    Toast.makeText(this, R.string.loginError, Toast.LENGTH_LONG).show()

                // Go to MainActivity


                else -> {
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

        registerButton.setOnClickListener{
            val intent = Intent(this@LoginActivity,RegisterActivity::class.java);

        }
    }

    // EXTRA
    override fun onBackPressed() {
        // Do nothing as we don't want it to go back to MainActivity after SingOut
    }
}
