package com.song2.jeonha.UI.Class

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.song2.jeonha.UI.Class.adapter.ClassBookDateRecyclerViewAdapter
import com.song2.jeonha.UI.Class.data.ClassDetailedData
import com.song2.jeonha.Network.ApplicationController
import com.song2.jeonha.Network.Get.GetClassDetailResponse
import com.song2.jeonha.Network.NetworkService
import com.song2.jeonha.R
import com.song2.jeonha.UI.Hanok.adapter.SliderMainPagerAdapter
import kotlinx.android.synthetic.main.activity_class_detail.*
import kotlinx.android.synthetic.main.activity_class_detail.tl_main_indicator
import kotlinx.android.synthetic.main.activity_class_detail.vp_main_slider
import kotlinx.android.synthetic.main.activity_hanok_detail.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ClassDetailActivity : AppCompatActivity() {

    val TAG = "ClassDetailActivity TAG"

    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }

    lateinit var classBookDateRecyclerViewAdapter: ClassBookDateRecyclerViewAdapter

    var classIdx: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_class_detail)

        classIdx = intent.getIntExtra("idx", -1)
        getClassDetailResponse(classIdx!!)

        //getClassDetailResponse(classIdx!!)
    }

    private fun getClassDetailResponse(idx: Int) {
        val getClassDetailResponse = networkService.getClassDetailResponse(idx)
        getClassDetailResponse.enqueue(object : Callback<GetClassDetailResponse> {
            override fun onFailure(call: Call<GetClassDetailResponse>, t: Throwable) {
                Log.e("ClassDetail Fail", t.toString())
            }

            override fun onResponse(call: Call<GetClassDetailResponse>, response: Response<GetClassDetailResponse>) {
                if (response.isSuccessful) {
                    if (response.body()!!.status == 200) {
                        val items: ClassDetailedData = response.body()!!.data
                        Log.d(TAG, items.toString())
                        setContent(items)
                    }
                }
            }

        })
    }

    private fun setContent(items: ClassDetailedData) {

        var str = ""
        for(i in items.schedule.indices){
            if(i !=0)
                str+="·"
            str += items.schedule.get(i).weekday
        }

        Log.e(TAG, items.toString())

        var imgArrayList = ArrayList<String>()

        Log.e(TAG+":", items.toString()+"..." +items.img.indices)
        for(i in items.img.indices){
            imgArrayList.add(items.img.get(i).img)
        }

        vp_main_slider.adapter= SliderMainPagerAdapter(supportFragmentManager,items.img.size, imgArrayList)
        vp_main_slider.offscreenPageLimit=2
        tl_main_indicator.setupWithViewPager(vp_main_slider)


        tv_class_detail_act_day.setText(str)
        tv_class_detail_act_name.setText(items.name)
        tv_class_detail_act_info.setText(items.detail)
        tv_class_detail_act_address.setText(items.address)

        classBookDateRecyclerViewAdapter = ClassBookDateRecyclerViewAdapter(this, items.schedule)
        rv_class_detail_act_time.adapter = classBookDateRecyclerViewAdapter
        rv_class_detail_act_time.layoutManager = LinearLayoutManager(this)
    }



}
