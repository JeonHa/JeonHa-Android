package com.song2.jeonha.UI.Hanok

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.song2.jeonha.UI.Hanok.data.HanokDetailItem
import com.song2.jeonha.UI.Hanok.data.Rooms
import com.song2.jeonha.Network.ApplicationController
import com.song2.jeonha.Network.Get.GetHanokDetailResponse
import com.song2.jeonha.Network.NetworkService
import com.song2.jeonha.NetworkDataClass.BookingData
import com.song2.jeonha.R
import com.song2.jeonha.UI.Hanok.adapter.SliderMainPagerAdapter
import com.song2.jeonha.UI.Hanok.data.PhotoItem
import kotlinx.android.synthetic.main.activity_hanok_detail.*
import kotlinx.android.synthetic.main.dialog_hanok.*
import kotlinx.android.synthetic.main.dialog_orderok.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HanokDetailActivity : AppCompatActivity(), OnMapReadyCallback {

    val TAG = "HanokDetailActivity TAG"

    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }
    var PhotoItemLIst = ArrayList<String>()
    var hanokIdx: Int? = null

    var mMap: GoogleMap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hanok_detail)
        _ac_hanok_detail_order.setOnClickListener {
          //  ordercancelBtn(this)
            postBookingResponse(intent.getIntExtra("idx", -1))

        }
        iv_back.setOnClickListener {
            finish()
        }
        hanokIdx = intent.getIntExtra("idx", -1)
        getHanokDetailResponse(intent.getIntExtra("idx", -1))
        getMap()
    }


    private fun getHanokDetailResponse(idx: Int) {
        val getHanokDetailResponse = networkService.getHanokDetailResponse(idx)
        getHanokDetailResponse.enqueue(object : Callback<GetHanokDetailResponse> {
            override fun onFailure(call: Call<GetHanokDetailResponse>, t: Throwable) {
                Log.e("HanokDetail Fail", t.toString())
            }

            override fun onResponse(
                call: Call<GetHanokDetailResponse>,
                response: Response<GetHanokDetailResponse>
            ) {
                if (response.isSuccessful) {
                    if (response.body()!!.status == 200) {
                        val items: HanokDetailItem = response.body()!!.data

                        for (i in 0..response.body()!!.data.img.size - 1) {
                            PhotoItemLIst.add(response.body()!!.data.img[i].img)
                        }
                        Log.d(TAG, items.toString())
                        vp_main_slider.adapter = SliderMainPagerAdapter(
                            supportFragmentManager,
                            response.body()!!.data.img.size,
                            PhotoItemLIst
                        )
                        vp_main_slider.offscreenPageLimit = 2
                        tl_main_indicator.setupWithViewPager(vp_main_slider)
                        tv_ac_hanok_detail_.text = response.body()!!.data.type
                        tv_detail_addres1.text = response.body()!!.data.place
                        tv_ac_hanok_detail_name.text = response.body()!!.data.name
                        tv_ac_hanok_detail_address.text = response.body()!!.data.address
                        tv_ac_.text = response.body()!!.data.detail
                        tv_hanok_ad.text = response.body()!!.data.address
                        tv_ha_tran.text = response.body()!!.data.transport
                        var roomsList: ArrayList<Rooms> = ArrayList()
                        roomsList = response.body()!!.data.rooms
                        var hanOkRecyclcerViewAdapter =
                            HanOkRecyclcerViewAdapter(this@HanokDetailActivity, roomsList)
                        rv_ac_hanok_detail_room.adapter = hanOkRecyclcerViewAdapter
                        rv_ac_hanok_detail_room.layoutManager = LinearLayoutManager(
                            this@HanokDetailActivity,
                            LinearLayoutManager.HORIZONTAL,
                            false
                        )
                        //mMap = googleMap
                        val location = LatLng(
                            response.body()!!.data.latitude,
                            response.body()!!.data.longitude
                        )
                        if (mMap != null) addMarker(mMap, location)

                        // setContent(items)
                    }
                }
            }
        })
    }

    /*  private fun setContent(items: HanokDetailItem) {
        val location = LatLng(items.latitude, items.longitude)
        if (mMap != null) addMarker(mMap, location)

        tv_ac_hanok_detail_name.text = items.name
        tv_ac_hanok_detail_.text = items.type
        tv_ac_hanok_detail_address.text = items.address
        tv_ac_hanok_detail_address_map.text = items.address
        tv_ac_hanok_detail_place.text = items.place
        tv_ac_hanok_detail_detail.text = items.detail
        tv_ac_hanok_detail_address_trans.text = items.transport

        if (items.option != null && items.option != "") {
            view_option.visibility = View.VISIBLE
            ll_option.visibility = View.VISIBLE
            tv_ac_hanok_detail_option.text = items.option
        } else {
            view_option.visibility = View.GONE
            ll_option.visibility = View.GONE
        }
    }
*/
    private fun getMap() {
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.frag_detail_map) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        Log.d(TAG, "onMapReady()")
        mMap = googleMap

        if (hanokIdx != -1 && hanokIdx != null) getHanokDetailResponse(hanokIdx!!)
    }

    private fun addMarker(mMap: GoogleMap?, location: LatLng) {

        val markerOptions = MarkerOptions().let {
            it.position(location)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.location_icon_big))
        }

        mMap?.let {
            it.addMarker(markerOptions)
            it.moveCamera(CameraUpdateFactory.newLatLng(location))
            it.animateCamera(CameraUpdateFactory.zoomTo(10f))
        }
    }


    private fun postBookingResponse(idx: Int) {
        val postBookingResponse = networkService.getBookingResponse(
            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZHgiOjEsImlhdCI6MTU2OTIwODA0NSwiZXhwIjoxNTY5ODEyODQ1LCJpc3MiOiJqZW9uaGEyMDE5In0.D9Ao9zBftj5qdd1NL8lSk_--0hPir8Du3tTZs834Afw",
            idx
        )
        postBookingResponse.enqueue(object : Callback<BookingData> {
            override fun onFailure(call: Call<BookingData>, t: Throwable) {
                Log.e("booking Fail", "예약 시스템 시류ㅐ")
            }

            override fun onResponse(call: Call<BookingData>, response: Response<BookingData>) {
                if (response.isSuccessful) {
                    Log.e("booking Fail", "예약 시스템 통신 성공")
                  //  Toast.makeText(this@HanokDetailActivity,response.body()!!.resMessage,Toast.LENGTH_SHORT).show()

                    if (response.body()!!.resMessage=="한옥 예약 성공") {
                        Log.e("booking Fail", "예약 시스템 다이얼로그")
                        orderokBtn(this@HanokDetailActivity)
                    }
                    else if (response.body()!!.resMessage=="이미 신청한 예약입니다" ){
                        Log.e("booking Fail", "예약 시스템 204")
                        ordercancelBtn(this@HanokDetailActivity)
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
