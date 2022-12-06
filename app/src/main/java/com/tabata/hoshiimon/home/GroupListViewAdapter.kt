package com.tabata.hoshiimon.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tabata.hoshiimon.R
import com.tabata.hoshiimon.database.Group

class GroupListViewAdapter(private val dataSet: List<Group>):
    RecyclerView.Adapter<GroupListViewAdapter.ViewHolder>() {

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val groupName: TextView

        init {
            groupName = view.findViewById(R.id.name_list)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_cell, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.groupName.text = dataSet[position].groupName

        viewHolder.itemView.setOnClickListener {
            listener.onItemClick(dataSet[position])
        }
    }

    override fun getItemCount() = dataSet.size

    private lateinit var listener: OnItemClickListener

    interface OnItemClickListener {
        fun onItemClick(group: Group)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }
}