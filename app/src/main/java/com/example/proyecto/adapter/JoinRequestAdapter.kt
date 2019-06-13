package com.example.proyecto.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

import com.example.proyecto.R


import com.example.proyecto.db.models.Awaiting_requests


class JoinRequestAdapter(
    context: Context,
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
        rowView.findViewById<TextView>(R.id.userNameTextView).text = dataSource[position].requester.toString()
        rowView.findViewById<TextView>(R.id.pointsTextView).text = dataSource[position].post.toString()
        rowView.findViewById<TextView>(R.id.eventTextView).text = dataSource[position].message
        return rowView
    }
}