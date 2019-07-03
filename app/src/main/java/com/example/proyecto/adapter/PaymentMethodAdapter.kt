package com.example.proyecto.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.proyecto.R
import com.example.proyecto.db.models.PaymentMethod
import java.text.FieldPosition
import javax.sql.CommonDataSource

class PaymentMethodAdapter(
    context: Context,
    private val dataSource: ArrayList<PaymentMethod>): BaseAdapter() {

    private val inflate: LayoutInflater
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
        val rowView = inflate.inflate(R.layout.list_item_pay_method, parent, false)
        rowView.findViewById<TextView>(R.id.typeTextView).text = dataSource[position].type
        rowView.findViewById<TextView>(R.id.cardNameTextView).text = dataSource[position].card_name
        rowView.findViewById<TextView>(R.id.cardNumberTextView).text = dataSource[position].number.toString()
        return rowView
    }

}