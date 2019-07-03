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
import com.example.proyecto.activities.RequestActivity
import com.example.proyecto.db.models.Post


import com.example.proyecto.db.models.Schedules
import kotlinx.android.synthetic.main.list_item_post.view.*
import kotlinx.android.synthetic.main.list_item_schedule.view.*


class PostAdapter(
    val context: Context,
    private val dataSource: ArrayList<Post>) : BaseAdapter() {

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
        val rowView = inflater.inflate(R.layout.list_item_post, parent, false)

        if (dataSource[position].required > 0) {

            rowView.findViewById<TextView>(R.id.scheduleTextView).text = dataSource[position].event.toString()
            rowView.findViewById<TextView>(R.id.titleTextView).text = dataSource[position].title
            rowView.findViewById<TextView>(R.id.playersMissing).text = dataSource[position].required.toString()

            rowView.goRequestButton.setOnClickListener {
                context.startActivity(
                    Intent(context, RequestActivity::class.java).
                        putExtra("POST_ID", dataSource[position].id))
            }



        }
        else{
            rowView.findViewById<TextView>(R.id.titleTextView).text = "Rented"
            rowView.findViewById<TextView>(R.id.scheduleTextView).text = dataSource[position].event.toString()

        }
        return rowView


    }
}