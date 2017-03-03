package com.twobbble.view.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import android.widget.TextView
import com.twobbble.R
import com.twobbble.application.App

/**
 * Created by liuzipeng on 16/3/5.
 */
class ToolbarSpinnerAdapter(val context: Context, val items: Array<String>) : BaseAdapter() {
    override fun getDropDownView(position: Int, convertView: View?,
                                 parent: ViewGroup): View {
        var convertView = convertView

        if (convertView == null) {
            val inflater = LayoutInflater.from(context)
            convertView = inflater.inflate(
                    android.R.layout.simple_spinner_dropdown_item, parent, false)
        }

        val tv = convertView!!
                .findViewById(android.R.id.text1) as TextView
        tv.text = items[position]
        tv.setTextColor(context.resources.getColor(R.color.primary_text))
        return convertView
    }

    override fun getCount(): Int = items.size

    override fun getItem(i: Int): Any {
        return i
    }

    override fun getItemId(i: Int): Long {
        return i.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        if (convertView == null) {
            val inflater = LayoutInflater.from(context)
            convertView = inflater.inflate(android.R.layout.simple_spinner_dropdown_item, parent, false)
        }
        //此处text1是Spinner默认的用来显示文字的TextView
        val tv = convertView!!.findViewById(android.R.id.text1) as TextView
        tv.text = items[position]
        tv.setTextColor(Color.WHITE)
        return convertView
    }
}
