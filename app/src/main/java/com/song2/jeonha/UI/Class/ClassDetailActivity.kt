package com.song2.jeonha.UI.Class

import android.app.Dialog
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Window
import android.widget.TextView
import com.song2.jeonha.DB.SharedPreferenceController
import com.song2.jeonha.UI.Class.adapter.ClassBookDateRecyclerViewAdapter
import com.song2.jeonha.UI.Class.data.ClassDetailedData
import com.song2.jeonha.Network.ApplicationController
import com.song2.jeonha.Network.Get.GetClassDetailResponse
import com.song2.jeonha.Network.NetworkService
import com.song2.jeonha.NetworkDataClass.BookingData
import com.song2.jeonha.R
import com.song2.jeonha.UI.Hanok.adapter.SliderMainPagerAdapter
import kotlinx.android.synthetic.main.activity_class_detail.*
import kotlinx.android.synthetic.main.activity_class_detail.tl_main_indicator
import kotlinx.android.synthetic.main.activity_class_detail.vp_main_slider
import kotlinx.android.synthetic.main.activity_hanok_detail.*
import org.jetbrains.anko.toast
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
        iv_class_detail_back_btn.setOnClickListener {
            finish()
        }
        classIdx = intent.getIntExtra("idx", -1)

        iv_ac_hanok_detail_order.setOnClickListener {
            postClassBookingResponse(intent.getIntExtra("weekIdx", -1))
        }
        //toast(classIdx.toString())
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


    private fun postClassBookingResponse(idx: Int) {
        val getClassBookingResponse = networkService.getClassBookingResponse(
            SharedPreferenceController.getAccessToken(this),
            idx
        )
        getClassBookingResponse.enqueue(object : Callback<BookingData> {
            override fun onFailure(call: Call<BookingData>, t: Throwable) {
                Log.e("booking Fail", "예약 시스템 시류ㅐ")
            }

            override fun onResponse(call: Call<BookingData>, response: Response<BookingData>) {
                if (response.isSuccessful) {
                    Log.e("booking Fail", response.body()!!.status.toString())
                    //  Toast.makeText(this@HanokDetailActivity,response.body()!!.resMessage,Toast.LENGTH_SHORT).show()
                    toast(response.body()!!.status.toString())
                    if (response.body()!!.status==201) {
                        Log.e("booking Fail", "이미 신청한 예약입니다")
                        orderokBtn(this@ClassDetailActivity)
                    }
                    else if (response.body()!!.status==204 ){
                        Log.e("booking Fail", "예약 시스템 204")
                        ordercancelBtn(this@ClassDetailActivity)
                    }else{
                        Log.e(TAG,"알 수 없는 오류 입니다.")
                    }
                }
            }
        })

    }

    public fun orderokBtn(context: Context) {

        val dlg = Dialog(context)

        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dlg.setContentView(R.layout.dialog_orderok)
        dlg.show()

        val dialogQuit = dlg.findViewById<TextView>(R.id.btn_dialog_hanok_ok1)

        dialogQuit.setOnClickListener {
            dlg.dismiss()
        }
    }
    public  fun ordercancelBtn(context: Context) {

        val dlg = Dialog(context)

        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dlg.setContentView(R.layout.dialog_ordercancel)
        dlg.show()

        val dialogQuit = dlg.findViewById<TextView>(R.id.btn_dialog_hanok_ok2)

        dialogQuit.setOnClickListener {
            dlg.dismiss()
        }
    }

}
