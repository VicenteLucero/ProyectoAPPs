package com.example.proyecto.adapter
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.TextView

import com.example.proyecto.R


import com.example.proyecto.db.models.Schedules


class ScheduleAdapter(
    context: Context,
    private val dataSource: ArrayList<Schedules>) : BaseAdapter() {

    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

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
        val rowView = inflater.inflate(R.layout.list_item_schedule, parent, false)

        if (!dataSource[position].rented) {

            rowView.findViewById<TextView>(R.id.fieldNameTextView).text = dataSource[position].field.toString()
            rowView.findViewById<TextView>(R.id.hourTextView).text = dataSource[position].hour.toString()



        }
        else{
            rowView.findViewById<TextView>(R.id.fieldNameTextView).text = "Rented"
            rowView.findViewById<TextView>(R.id.hourTextView).text = dataSource[position].hour.toString()

        }
        return rowView


    }
}