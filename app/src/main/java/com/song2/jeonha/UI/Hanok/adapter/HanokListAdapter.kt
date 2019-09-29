package com.song2.jeonha.Hanok.adapter

import android.content.Context
import android.content.Intent
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.song2.jeonha.Hanok.HanokDetailActivity
import com.song2.jeonha.Hanok.data.HanokItem
import com.song2.jeonha.R

class HanokListAdapter(var ctx: Context, var dataList: ArrayList<HanokItem>) :
    RecyclerView.Adapter<HanokListAdapter.Holder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): Holder {
        val view: View = LayoutInflater.from(ctx).inflate(R.layout.rv_item_hanok_filter, p0, false)

        return Holder(view)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(p0: Holder, p1: Int) {
        p0.address.text = dataList[p1].address
        p0.type.text = dataList[p1].type
        p0.hanokName.text = dataList[p1].name
        p0.region.text = dataList[p1].place

        Glide.with(ctx).load(dataList[p1].img).into(p0.thumb)

        p0.container.setOnClickListener {
            val intent: Intent = Intent(ctx, HanokDetailActivity::class.java)
            intent.putExtra("idx", dataList[p1].hanokIdx)

            Log.d("idx::", dataList[p1].hanokIdx.toString())
            startActivity(ctx, intent, null)
        }
    }


    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var container = itemView.findViewById(R.id.container_rv_item_hanok_filter) as LinearLayout
        var thumb = itemView.findViewById(R.id.img_hanok_rv_item_thumb) as ImageView
        var type = itemView.findViewById(R.id.txt_hanok_rv_item_type) as TextView
        var region = itemView.findViewById(R.id.txt_hanok_rv_item_region) as TextView
        var hanokName = itemView.findViewById(R.id.txt_hanok_rv_item_name) as TextView
        var address = itemView.findViewById(R.id.txt_hanok_rv_item_address) as TextView
    }
}