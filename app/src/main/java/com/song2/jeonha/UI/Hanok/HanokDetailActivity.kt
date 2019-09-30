package com.song2.jeonha.UI.Hanok

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
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
import com.song2.jeonha.R
import com.song2.jeonha.UI.Hanok.adapter.SliderMainPagerAdapter
import com.song2.jeonha.UI.Hanok.data.PhotoItem
import kotlinx.android.synthetic.main.activity_hanok_detail.*
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
                        Log.d(TAG, items.toString())

                        setContent(items)
                    }
                }
            }
        })
    }

   private fun setContent(items: HanokDetailItem) {
       tv_ac_hanok_detail_.text=items.type
       tv_detail_addres1.text=items.place
       tv_ac_hanok_detail_name.text=items.name
       tv_ac_hanok_detail_address.text=items.address
       tv_ac_hanok_detail_detail.text=items.detail
       tv_hanok_ad.text=items.address
       tv_ha_tran.text=items.transport

       if (items.option != null && items.option != "") {
           view_option.visibility = View.VISIBLE
           ll_option.visibility = View.VISIBLE
           tv_ac_hanok_detail_option.text = items.option
       } else {
           view_option.visibility = View.GONE
           ll_option.visibility = View.GONE
       }

       setAdapter(items)

       //mMap = googleMap
       val location = LatLng(items.latitude, items.longitude)
       if (mMap != null) addMarker(mMap, location)

    }

    private fun setAdapter(items: HanokDetailItem) {
        for (i in 0 until items.img.size-1){
            PhotoItemLIst.add(items.img[i].img)
        }
        vp_main_slider.adapter= SliderMainPagerAdapter(supportFragmentManager,items.img.size, PhotoItemLIst)
        vp_main_slider.offscreenPageLimit=2
        tl_main_indicator.setupWithViewPager(vp_main_slider)


        var roomsList: ArrayList<Rooms> = ArrayList()
        roomsList=items.rooms
        var hanOkRecyclcerViewAdapter = HanOkRecyclcerViewAdapter(this@HanokDetailActivity, roomsList)
        rv_ac_hanok_detail_room.adapter = hanOkRecyclcerViewAdapter
        rv_ac_hanok_detail_room.layoutManager = LinearLayoutManager(this@HanokDetailActivity, LinearLayoutManager.HORIZONTAL, false)
    }

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


}
