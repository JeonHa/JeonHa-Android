package com.song2.jeonha.UI.Class.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.song2.jeonha.UI.Class.data.ScheduleData
import com.song2.jeonha.R


class ClassBookDateRecyclerViewAdapter(var ctx: Context, var dataList: ArrayList<ScheduleData>) : RecyclerView.Adapter<ClassBookDateRecyclerViewAdapter.Holder>() {

    override fun onCreateViewHolder(viewgroup: ViewGroup, position: Int): Holder {

        var view : View = LayoutInflater.from(ctx).inflate(R.layout.item_rv_class_date, viewgroup, false)

        return Holder(view)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {

        holder.weekday.text = dataList[position].weekday
        holder.time.text = dataList[position].time
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var weekday = itemView.findViewById(R.id.tv_item_class_date_weekday) as TextView
        var time = itemView.findViewById(R.id.tv_item_class_date_time) as TextView
    }

}