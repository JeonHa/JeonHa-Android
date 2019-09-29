package com.song2.jeonha.Main.Mypage.MyPageFragment

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.song2.jeonha.Main.Mypage.MyPageFragment.GetMyBookingList.ClassData
import com.song2.jeonha.R

class ClassBookingRecyclerViewAdapter(var ctx: Context, var dataList: ArrayList<ClassData?>) : RecyclerView.Adapter<ClassBookingRecyclerViewAdapter.Holder>() {

    override fun onCreateViewHolder(viewgroup: ViewGroup, position: Int): Holder {

        var view : View = LayoutInflater.from(ctx).inflate(R.layout.rv_item_class_contents, viewgroup, false)

        return Holder(view)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.tv_class_classname.text=dataList[position]!!.name
        holder.tv_class_day.text = dataList[position]!!.weekday
        holder.rl_item_class_all_contents.setOnClickListener {
        //detailed 페이지로 idx 값 넘겨야함
        }

        Glide.with(ctx)
            .load(dataList[position]!!.thumnail)
            .into(holder.iv_rv_class_img)
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var iv_rv_class_img = itemView.findViewById(R.id.iv_rv_class_img) as ImageView
        var tv_class_day = itemView.findViewById(R.id.tv_class_day) as TextView
        var tv_class_classname = itemView.findViewById(R.id.tv_class_classname) as TextView
        var rl_item_class_all_contents= itemView.findViewById(R.id.rl_item_class_all_contents) as RelativeLayout
    }

}