package com.tabata.hoshiimon.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tabata.hoshiimon.R
import com.tabata.hoshiimon.database.Item

class ItemListViewAdapter(private val dataSet: List<Item>):
    RecyclerView.Adapter<ItemListViewAdapter.ViewHolder>() {

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val itemName: TextView

        init {
            itemName = view.findViewById(R.id.name_list)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_cell, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemName.text = dataSet[position].itemName
    }

    override fun getItemCount() = dataSet.size
}