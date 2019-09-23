package com.song2.jeonha.Main

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.song2.jeonha.R
import de.hdodenhof.circleimageview.CircleImageView


class TitleListRecyclerViewAdapter(var ctx: Context, var dataList: ArrayList<ProgramData>) : RecyclerView.Adapter<TitleListRecyclerViewAdapter.Holder>() {

    override fun onCreateViewHolder(viewgroup: ViewGroup, position: Int): Holder {

        var view : View = LayoutInflater.from(ctx).inflate(R.layout.item_rv_main_program, viewgroup, false)

        return Holder(view)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {

        holder.programTitle.text = dataList[position].program_name
        holder.container.setOnClickListener {
            //detailed 페이지로
        }

        Glide.with(ctx)
            .load(dataList[position].program_img)
            .into(holder.programImg)
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var programImg = itemView.findViewById(R.id.cv_item_main_program_list_img) as CircleImageView
        var programTitle = itemView.findViewById(R.id.cv_item_main_program_list_title) as TextView
        var container = itemView.findViewById(R.id.rv_item_main_program_list_container) as RelativeLayout
    }

}