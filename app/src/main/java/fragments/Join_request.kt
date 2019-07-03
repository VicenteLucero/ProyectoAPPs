package fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.proyecto.CredentialsManager
import com.example.proyecto.R

import com.example.proyecto.db.AppDatabase
import com.example.proyecto.db.models.Awaiting_requests
import com.example.proyecto.adapter.JoinRequestAdapter
import com.example.proyecto.db.models.Post
import kotlinx.android.synthetic.main.fragment_join_request.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class Join_request : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_join_request, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadUserRequests()
        setListOnClickListener()
    }

    private fun loadUserRequests() {
        val currentUserEmail = CredentialsManager.getInstance(context!!).loadUser()!!.first
        val awaitingRequestsDao = AppDatabase.getDatabase(context!!).awaiting_requestsDao()
        val postDao = AppDatabase.getDatabase(context!!).postDao()
        GlobalScope.launch(Dispatchers.IO) { // replaces doAsync (runs on another thread)
            val requests = awaitingRequestsDao.getAll()
            val posts = postDao.getPostsFromUser(AppDatabase.getDatabase(context!!).userDao().getUserByEmail(currentUserEmail).id)

            var myList: MutableList<Awaiting_requests> = mutableListOf<Awaiting_requests>()

            for(i in posts){
                for(j in requests){
                    if (i.id == j.post){
                        myList.add(j)
                    }
                }
            }
            launch(Dispatchers.Main) {// replaces uiThread (runs on UIThread)
                val itemsAdapter = JoinRequestAdapter(context!!, ArrayList(myList))
                requestList.adapter = itemsAdapter
            }
        }
    }

    private fun setListOnClickListener() {


    }


}
