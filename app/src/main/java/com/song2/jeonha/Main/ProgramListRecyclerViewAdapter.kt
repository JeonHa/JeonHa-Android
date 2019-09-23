package com.song2.jeonha.Main

import android.content.Context
import org.jetbrains.anko.startActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.song2.unifarm.Data.CollectPopularProgramData
import com.song2.unifarm.DetailedActivity
import com.song2.unifarm.R
import java.util.regex.Pattern

class ProgramListRecyclerViewAdapter(var ctx: Context, var dataList: ArrayList<CollectPopularProgramData>) :
    RecyclerView.Adapter<ProgramListRecyclerViewAdapter.Holder>() {
    override fun onCreateViewHolder(viewgroup: ViewGroup, position: Int): Holder {
        val view: View = LayoutInflater.from(ctx).inflate(R.layout.rv_collect_popular_program, viewgroup, false)

        return Holder(view)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {

        holder.rv_tv_collect_popular_program.text=dataList[position].title.toString()
      holder.rv_collect_allview.setOnClickListener {
          //전체 아이템 클릭 (프로그램으로 이동하는거 만들기!)
          //idx 틀림!!!
          //ctx.startActivity<DetailedActivity>("idxxx" to position)

      }
        Glide.with(ctx)
            .load(dataList[position].thumbnail)
            .into(holder.cv_collect_popular)
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var rv_collect_allview = itemView.findViewById(R.id.rv_collect_allview) as RelativeLayout
        var rv_tv_collect_popular_program=itemView.findViewById(R.id.rv_tv_collect_popular_program) as TextView
        var cv_collect_popular=itemView.findViewById(R.id.cv_collect_popular) as ImageView
    }

}