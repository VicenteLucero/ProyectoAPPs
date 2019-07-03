package com.example.proyecto.activities

import android.app.Activity
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.proyecto.CredentialsManager
import com.example.proyecto.R
import com.example.proyecto.db.AppDatabase
import com.example.proyecto.db.models.PaymentMethod
import kotlinx.android.synthetic.main.activity_add_methods.*
import kotlinx.android.synthetic.main.fragment_payment_method.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.Exception

class AddMethods : AppCompatActivity() {
    private var type: String = "none"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_methods)
        setListeners()
    }



    private fun setListeners() {
        addNewMethod.setOnClickListener {
            if(numberEdit == null || nameEdit == null || type == "none"){
                Toast.makeText(applicationContext, "Fields cannot be blank", Toast.LENGTH_SHORT).show()
            } else {
                val user = CredentialsManager.getInstance(this).loadUser()!!
                val number = numberEdit.text.toString()
                val num = number.toInt()
                val name = nameEdit.text.toString()
                val userDao = AppDatabase.getDatabase(this).userDao()
                val methodsDao = AppDatabase.getDatabase(this).payMethodDao()
                GlobalScope.launch(Dispatchers.IO){
                    val currentUser = userDao.getUser(user.first,user.second)
                    val methos = PaymentMethod(currentUser.id, num, type, name)
                    try {
                        methodsDao.insertAll(methos)
                        launch(Dispatchers.Main) {
                            Toast.makeText(applicationContext, "Saved successfully", Toast.LENGTH_SHORT).show()
                            setResult(Activity.RESULT_OK, intent)
                            finish()
                        }
                    } catch (e: Exception) {
                        launch(Dispatchers.Main) {
                            Toast.makeText(applicationContext, "Error storing method ${e.message}" , Toast.LENGTH_LONG).show()
                        }
                    }

                }
            }


        }
        creditCheck.setOnClickListener{
            debitCheck.isChecked = false
            paypalCheck.isChecked = false
            type = "Credit"
        }
        debitCheck.setOnClickListener{
            creditCheck.isChecked = false
            paypalCheck.isChecked = false
            type = "Debit"
        }
        paypalCheck.setOnClickListener{
            creditCheck.isChecked = false
            debitCheck.isChecked = false
            type = "Paypal"
        }
        cancelButton.setOnClickListener {
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }
}
