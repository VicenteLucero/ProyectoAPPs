package com.example.proyecto

import android.content.Context
import android.content.SharedPreferences

class CredentialsManager private constructor(context: Context) {
    private val context = context
    init{

    }
    companion object : SingletonHolder<CredentialsManager, Context>(::CredentialsManager)

    private fun getCredentialsSharedPreferences() : SharedPreferences {
        return context.getSharedPreferences(
            context.getString(R.string.credentials_shared_preference_file),
            Context.MODE_PRIVATE)
    }

    fun saveUser(userEmail: String, userPassword: String){
        val sharedPref = getCredentialsSharedPreferences()
        with (sharedPref.edit()){
            putString("EMAIL", userEmail)
            putString("PASS", userPassword)
            commit()
        }
    }

    fun loadUser(): Pair<String, String>? {
        val sharedPref = getCredentialsSharedPreferences()
        val email = sharedPref.getString("EMAIL", "")
        val password = sharedPref.getString("PASS", "")

        return when {
            email.isNullOrEmpty() || password.isNullOrEmpty() -> null
            else -> Pair(email,password)
        }
    }

    fun deleteUser() {
        val sharedPref = getCredentialsSharedPreferences()
        with (sharedPref.edit()){
            putString("EMAIL", "")
            putString("PASS", "")
            commit()
        }
    }



}