package com.song2.jeonha.Class.adapter

import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.song2.jeonha.Class.ClassDetailActivity
import com.song2.jeonha.Class.data.ClassData
import com.song2.jeonha.R
import org.jetbrains.anko.startActivity


class ClassListRecyclerViewAdapter(var ctx: Context, var dataList: ArrayList<ClassData>) : RecyclerView.Adapter<ClassListRecyclerViewAdapter.Holder>() {

    override fun onCreateViewHolder(viewgroup: ViewGroup, position: Int): Holder {

        var view : View = LayoutInflater.from(ctx).inflate(R.layout.item_rv_class, viewgroup, false)

        return Holder(view)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {

        holder.classTitle.text = dataList[position].class_title
        //holder.classDay.text
        holder.container.setOnClickListener {
            ctx.startActivity<ClassDetailActivity>("idx" to dataList[position].id)
        }

        var test = ""
        for (i in 0..dataList[position].day!!.size-1){
            if(i !=0)
                test +="·"

            test += dataList[position].day!![i]
        }

        holder.classDay.text = test

        Glide.with(ctx)
            .load(dataList[position].class_img)
            .into(holder.classImg)

        holder.classImg.setColorFilter(Color.parseColor("#BDBDBD"), PorterDuff.Mode.MULTIPLY)

    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var classImg = itemView.findViewById(R.id.iv_item_class_list_img) as ImageView
        var classDay = itemView.findViewById(R.id.tv_item_class_list_day) as TextView
        var classTitle = itemView.findViewById(R.id.tv_item_class_list_title) as TextView
        var container = itemView.findViewById(R.id.rl_item_class_container) as RelativeLayout
    }

}