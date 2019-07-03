package com.example.proyecto.adapter
import android.content.Context
import android.content.Intent
import android.support.v4.content.ContextCompat.startActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.TextView

import com.example.proyecto.R
import com.example.proyecto.activities.RentActivity


import com.example.proyecto.db.models.Schedules
import kotlinx.android.synthetic.main.list_item_schedule.view.*
import org.w3c.dom.Text
import kotlin.coroutines.coroutineContext



class ScheduleAdapter(
    val context: Context,
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

    fun getScheduleItemId(position: Int): Int{
        return dataSource[position].id
    }


    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        // Get view for row item
        val rowView = inflater.inflate(R.layout.list_item_schedule, parent, false)

        if (!dataSource[position].rented) {
            rowView.findViewById<TextView>(R.id.fieldNameTextView).text = "Available"
            rowView.findViewById<TextView>(R.id.hourTextView).text = dataSource[position].hour.toString()
            rowView.goRentButton.setOnClickListener {
                context.startActivity(
                    Intent(context, RentActivity::class.java).
                        putExtra("SCHEDULE_ID", dataSource[position].id))
            }



        }
        else{
            rowView.findViewById<TextView>(R.id.fieldNameTextView).text = "Rented"
            rowView.findViewById<TextView>(R.id.hourTextView).text = dataSource[position].hour.toString()

        }
        return rowView


    }
}