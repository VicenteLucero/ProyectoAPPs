package com.example.proyecto.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import android.widget.Toast
import com.example.proyecto.R
import com.example.proyecto.db.AppDatabase
import com.example.proyecto.db.models.AcceptedRequest
import com.example.proyecto.db.models.Awaiting_requests
import com.example.proyecto.db.models.User_rent
import fragments.MyRequests
import kotlinx.android.synthetic.main.list_item_join_request.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MyRentsAdapter (
    val context: Context,
    private val dataSource: ArrayList<User_rent>) : BaseAdapter() {

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
            val rowView = inflater.inflate(R.layout.list_item_my_rents, parent, false)
            val sportDao = AppDatabase.getDatabase(context).sportDao()
            val fieldDao = AppDatabase.getDatabase(context).fieldDao()
            GlobalScope.launch(Dispatchers.IO) {
                val field = fieldDao.getField(dataSource[position].fieldName)
                val fieldName = field.name
                val sport = sportDao.getSport(field.sport).name

                launch(Dispatchers.Main) {
                    rowView.findViewById<TextView>(R.id.userNameTextView).text = fieldName
                    rowView.findViewById<TextView>(R.id.pointsTextView).text = sport
                    rowView.findViewById<TextView>(R.id.eventTextView).text = dataSource[position].players.toString()
                }
            }

            return rowView
        }
    }