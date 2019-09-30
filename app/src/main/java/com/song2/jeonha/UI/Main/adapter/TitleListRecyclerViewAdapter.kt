package com.song2.jeonha.UI.Main.adapter

import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.song2.jeonha.UI.Main.data.TitleData
import com.song2.jeonha.R
import com.song2.jeonha.UI.Main.WebViewMainActivity
import org.jetbrains.anko.startActivity


class TitleListRecyclerViewAdapter(var ctx: Context, var dataList: ArrayList<TitleData>) :
    RecyclerView.Adapter<TitleListRecyclerViewAdapter.Holder>() {

    override fun onCreateViewHolder(viewgroup: ViewGroup, position: Int): Holder {

        var view: View = LayoutInflater.from(ctx).inflate(R.layout.item_rv_main_title, viewgroup, false)

        return Holder(view)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {

        holder.titleName.text = dataList[position].title_name
        holder.titleContents.text = dataList[position].sub_contents

        holder.container.setOnClickListener {
            ctx.startActivity<WebViewMainActivity>("uriAddress" to dataList[position].uri_address)
        }

        if (position == 4) {
            holder.titleImg1.visibility = VISIBLE
            holder.titleImg2.visibility = GONE
            holder.titleImg3.visibility = GONE
            holder.titleImg4.visibility = GONE
            holder.titleImg5.visibility = GONE
        } else if (position == 0) {
            holder.titleImg1.visibility = GONE
            holder.titleImg2.visibility = VISIBLE
            holder.titleImg3.visibility = GONE
            holder.titleImg4.visibility = GONE
            holder.titleImg5.visibility = GONE
        } else if (position == 2) {
            holder.titleImg1.visibility = GONE
            holder.titleImg2.visibility = GONE
            holder.titleImg3.visibility = VISIBLE
            holder.titleImg4.visibility = GONE
            holder.titleImg5.visibility = GONE
        } else if (position == 3) {
            holder.titleImg1.visibility = GONE
            holder.titleImg2.visibility = GONE
            holder.titleImg3.visibility = GONE
            holder.titleImg4.visibility = VISIBLE
            holder.titleImg5.visibility = GONE
        }else if (position == 1) {
            holder.titleImg1.visibility = GONE
            holder.titleImg2.visibility = GONE
            holder.titleImg3.visibility = GONE
            holder.titleImg4.visibility = GONE
            holder.titleImg5.visibility = VISIBLE
        }

    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var titleImg1 = itemView.findViewById(R.id.iv_item_main_title_image1) as ImageView
        var titleImg2 = itemView.findViewById(R.id.iv_item_main_title_image2) as ImageView
        var titleImg3 = itemView.findViewById(R.id.iv_item_main_title_image3) as ImageView
        var titleImg4 = itemView.findViewById(R.id.iv_item_main_title_image4) as ImageView
        var titleImg5 = itemView.findViewById(R.id.iv_item_main_title_image5) as ImageView
        var titleName = itemView.findViewById(R.id.tv_item_main_title) as TextView
        var titleContents = itemView.findViewById(R.id.tv_item_sub_title) as TextView
        var container = itemView.findViewById(R.id.rv_item_main_title_container) as RelativeLayout
    }

}