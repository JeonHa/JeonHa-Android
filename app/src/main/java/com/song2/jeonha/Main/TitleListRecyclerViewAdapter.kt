package com.song2.jeonha.Main

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.ColorFilter
import android.graphics.PorterDuff
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.engine.prefill.BitmapPreFiller
import com.bumptech.glide.load.resource.bitmap.BitmapDrawableDecoder
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.song2.jeonha.R
import de.hdodenhof.circleimageview.CircleImageView
import jp.wasabeef.glide.transformations.BlurTransformation
import jp.wasabeef.glide.transformations.ColorFilterTransformation


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
        holder.container.setOnClickListener {
            //detailed 페이지로
        }

        var requestOptions = RequestOptions()
        requestOptions = requestOptions.transforms(RoundedCorners(32))

        Glide.with(ctx)
            .load(dataList[position].title_img)
            .apply(requestOptions)
            .into(holder.titleImg)

        holder.titleImg.setColorFilter(Color.parseColor("#BDBDBD"), PorterDuff.Mode.MULTIPLY);


    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var titleImg = itemView.findViewById(R.id.iv_item_main_title_image) as ImageView
        var titleName = itemView.findViewById(R.id.tv_item_main_title) as TextView
        var container = itemView.findViewById(R.id.rv_item_main_title_container) as RelativeLayout

    }

}