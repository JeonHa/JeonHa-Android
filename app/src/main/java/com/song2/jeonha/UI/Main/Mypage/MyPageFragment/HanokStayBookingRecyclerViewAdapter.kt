package com.song2.jeonha.UI.Main.Mypage.MyPageFragment

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.song2.jeonha.UI.Main.Mypage.MyPageFragment.GetMyBookingList.HanokData
import com.song2.jeonha.R
import com.song2.jeonha.UI.Class.ClassDetailActivity
import com.song2.jeonha.UI.Hanok.HanokDetailActivity
import org.jetbrains.anko.startActivity

class HanokStayBookingRecyclerViewAdapter(var ctx: Context, var dataList: ArrayList<HanokData?>) : RecyclerView.Adapter<HanokStayBookingRecyclerViewAdapter.Holder>() {

    override fun onCreateViewHolder(viewgroup: ViewGroup, position: Int): Holder {

        var view : View = LayoutInflater.from(ctx).inflate(R.layout.rv_item_stay_contents, viewgroup, false)

        return Holder(view)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.tv_rv_stay_classname.text=dataList[position]!!.type
        holder.tv_rv_stay_class_place_name.text = dataList[position]!!.name
        holder.tv_rv_stay_summery_place.text = dataList[position]!!.place
        holder.tv_rv_stay_class_address.text = dataList[position]!!.address
        holder.rl_item_stay_all.setOnClickListener {
            ctx.startActivity<HanokDetailActivity>("idx" to dataList[position]!!.hanokIdx)//detailed 페이지로 idx 값 넘겨야함

        }

        Glide.with(ctx)
            .load(dataList[position]!!.thumnail)
            .into(holder.iv_rv_stay_img)
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var iv_rv_stay_img = itemView.findViewById(R.id.iv_rv_stay_img) as ImageView
        var tv_rv_stay_classname = itemView.findViewById(R.id.tv_rv_stay_classname) as TextView
        var tv_rv_stay_class_place_name = itemView.findViewById(R.id.tv_rv_stay_class_place_name) as TextView
        var rl_item_stay_all= itemView.findViewById(R.id.rl_item_stay_all) as RelativeLayout
        var tv_rv_stay_summery_place = itemView.findViewById(R.id.tv_rv_stay_summery_place) as TextView
        var tv_rv_stay_class_address = itemView.findViewById(R.id.tv_rv_stay_class_address) as TextView
    }

}