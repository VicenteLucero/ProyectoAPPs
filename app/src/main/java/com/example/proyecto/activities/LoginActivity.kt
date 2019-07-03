package com.example.proyecto.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast
import com.example.proyecto.EmailValidator
import com.example.proyecto.R
import com.example.proyecto.db.AppDatabase
import com.example.proyecto.db.models.User


import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setListeners()
    }

    private fun setListeners() {
        loginButton.setOnClickListener {
            val userEmail = emailEditText.text.toString()
            val userPassword = passwordEditText.text.toString()
            when {
                // Check Email
                    // Check Email
                    !EmailValidator.isValidEmail(userEmail) ->
                        Toast.makeText(this, R.string.emailError, Toast.LENGTH_LONG).show()
                    // Check Password
                    userPassword.isNullOrEmpty() ->
                        Toast.makeText(this, R.string.passwordError, Toast.LENGTH_LONG).show()
                else -> {
                    getUser(userEmail, userPassword, this)
                    // Add data to invoking intent

                }
            }
        }

        registerButton.setOnClickListener{
            val intent = Intent(this@LoginActivity,RegisterActivity::class.java)
            startActivity(intent)

        }
    }

    // EXTRA
    override fun onBackPressed() {
        // Do nothing as we don't want it to go back to MainActivity after SingOut
    }

    private fun getUser(email: String, pass: String, context: Context){
        GlobalScope.launch(Dispatchers.IO){
            val user: User = AppDatabase.getDatabase(baseContext).userDao().getUser(email, pass)
            if(user == null){
                launch(Dispatchers.Main){
                    Toast.makeText(context, R.string.loginError, Toast.LENGTH_LONG).show()
                }
            }
            else{
                launch(Dispatchers.Main){
                    intent.apply {
                        putExtra("EMAIL", email)
                        putExtra("PASSWORD", pass)
                    }
                    // Set response
                    setResult(Activity.RESULT_OK, intent)
                    finish()
                }
            }
        }


    }
}
