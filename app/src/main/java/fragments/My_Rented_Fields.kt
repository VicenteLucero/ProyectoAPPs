package fragments


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.proyecto.CredentialsManager

import com.example.proyecto.R
import com.example.proyecto.adapter.MyRentsAdapter
import com.example.proyecto.db.AppDatabase
import kotlinx.android.synthetic.main.fragment_my__rented__fields.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.DisposableHandle
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class My_Rented_Fields : Fragment() {

    private lateinit var current_user : Pair<String, String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_my__rented__fields, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadRents()
    }

    private fun loadRents() {
        current_user = CredentialsManager.getInstance(context!!).loadUser()!!
        val user_rentDao = AppDatabase.getDatabase(context!!).user_rentDao()
        val userDao = AppDatabase.getDatabase(context!!).userDao()
        GlobalScope.launch(Dispatchers.IO){
            val user = userDao.getUser(current_user.first, current_user.second).id
            val myRents = user_rentDao.getCurrentUserRents(user)
            launch(Dispatchers.Main){
                val itemsAdapter = MyRentsAdapter(context!!, ArrayList(myRents))
                myRentList.adapter = itemsAdapter
            }
        }
    }


}
