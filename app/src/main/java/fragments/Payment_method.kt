package fragments


import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Looper
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.proyecto.CredentialsManager

import com.example.proyecto.R
import com.example.proyecto.RequestCode
import com.example.proyecto.activities.AddMethods
import com.example.proyecto.activities.LoginActivity
import com.example.proyecto.activities.MainActivity
import com.example.proyecto.adapter.PaymentMethodAdapter
import com.example.proyecto.db.AppDatabase
import com.google.android.gms.location.LocationServices
import kotlinx.android.synthetic.main.fragment_payment_method.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class Payment_method : Fragment() {

    companion object {
        private const val GO_TO_ADD_FROM_PAYMETHOD_FRAGMENT: Int = 2000
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_payment_method, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadMethods()
        setListeners()
    }

    private fun setListeners() {
        addButton.setOnClickListener {
            startActivityForResult(Intent(context!!, AddMethods::class.java), GO_TO_ADD_FROM_PAYMETHOD_FRAGMENT)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            RequestCode.GO_TO_LOGIN_FROM_MAIN_ACTIVITY -> {
                if(resultCode == Activity.RESULT_OK){
                        loadMethods()
                }
            }
        }
    }

    private fun loadMethods(){
        val methodDao = AppDatabase.getDatabase(context!!).payMethodDao()
        val userDao = AppDatabase.getDatabase(context!!).userDao()
        GlobalScope.launch(Dispatchers.IO){
           val current = CredentialsManager.getInstance(context!!).loadUser()!!
           val user = userDao.getUser(current.first, current.second)
           val methods = methodDao.getUserMethods(user.id)
           launch(Dispatchers.Main){
               if (methods != null){
                   val itemsAdapter = PaymentMethodAdapter(context!!, ArrayList(methods))
                   paymentsListView.adapter = itemsAdapter
               }
           }
        }
    }


}
