package com.song2.jeonha.UI.Hanok

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.song2.jeonha.UI.Hanok.data.Rooms
import com.song2.jeonha.R

class HanOkRecyclcerViewAdapter(val ctx: Context, val roomsList: ArrayList<Rooms>) : RecyclerView.Adapter<HanOkRecyclcerViewAdapter.Holder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.rv_item_ac_hanok_detail_list, parent, false)
        return Holder(view)
    }


    override fun getItemCount(): Int = roomsList.size


    override fun onBindViewHolder(holer: Holder, position: Int) {
        holer.type.text = roomsList[position].type
        holer.rooms.text = roomsList[position].rooms.toString()
        holer.persons.text = roomsList[position].persons.toString()

    }


    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val type: TextView = itemView.findViewById(R.id.tv_ac_hanok_detail_room_name) as TextView
        val rooms: TextView = itemView.findViewById(R.id.tv_ac_hanok_detail_room_num) as TextView
        val persons: TextView = itemView.findViewById(R.id.tv_ac_hanok_detail_room_people) as TextView

    }


}