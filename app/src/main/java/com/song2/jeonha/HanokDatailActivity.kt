package com.song2.jeonha

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_hanok_datail.*

class HanokDatailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hanok_datail)



        //추천 리사이클러뷰
        var roomsList : ArrayList<Rooms> = ArrayList()
        roomsList.add(Rooms("싱클룸", 1, 2))
        roomsList.add(Rooms("트리플", 1, 2))
        roomsList.add(Rooms("기타", 1, 2))


        var hanOkRecyclcerViewAdapter = HanOkRecyclcerViewAdapter(this,roomsList)
        rv_ac_hanok_detail_room.adapter = hanOkRecyclcerViewAdapter
        rv_ac_hanok_detail_room.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)



    }







}
