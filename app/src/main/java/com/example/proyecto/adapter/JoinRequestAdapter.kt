package com.example.proyecto.adapter

import android.content.Context
import android.content.Intent
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import android.widget.Toast

import com.example.proyecto.R
import com.example.proyecto.activities.MainActivity
import com.example.proyecto.db.AppDatabase
import com.example.proyecto.db.models.AcceptedRequest


import com.example.proyecto.db.models.Awaiting_requests
import fragments.Join_request
import kotlinx.android.synthetic.main.list_item_join_request.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class JoinRequestAdapter(
    val context: Context,
    private val dataSource: ArrayList<Awaiting_requests>) : BaseAdapter() {

    private val inflater: LayoutInflater
            = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int {
        return dataSource.size
    }

    override fun getItem(position: Int): Any {
        return dataSource[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        // Get view for row item
        val rowView = inflater.inflate(R.layout.list_item_join_request, parent, false)

        GlobalScope.launch(Dispatchers.IO) { // replaces doAsync (runs on another thread)
            rowView.findViewById<TextView>(R.id.userNameTextView).text =AppDatabase.getDatabase(context).userDao().getUserById(dataSource[position].requester).name
            rowView.findViewById<TextView>(R.id.pointsTextView).text = AppDatabase.getDatabase(context).postDao().getPost(dataSource[position].post).title

        }

        rowView.findViewById<TextView>(R.id.eventTextView).text = dataSource[position].message


        rowView.acceptButton.setOnClickListener {
            GlobalScope.launch(Dispatchers.IO) {
                try {
                    AppDatabase.getDatabase(context).acceptedRequestDao().insertAll(AcceptedRequest(dataSource[position].requester, dataSource[position].id,dataSource[position].message))
                    AppDatabase.getDatabase(context).awaiting_requestsDao().deleteRequest(dataSource[position])
                    launch(Dispatchers.Main) {
                        Toast.makeText(context, "Rented!", Toast.LENGTH_LONG).show()
                    }
                    val intent = Intent(context, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    context.startActivity(intent)

                } catch (e: Exception) {
                    launch(Dispatchers.Main) {
                        Log.d("ERROR", "Error storing rent ${e.message}")
                        Toast.makeText(context, "Error storing rent ${e.message}", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }


        return rowView
    }
}