package com.song2.jeonha.Hanok

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.song2.jeonha.Hanok.data.HanokDetailItem
import com.song2.jeonha.Hanok.data.Rooms
import com.song2.jeonha.Network.ApplicationController
import com.song2.jeonha.Network.Get.GetHanokDetailResponse
import com.song2.jeonha.Network.NetworkService
import com.song2.jeonha.R
import kotlinx.android.synthetic.main.activity_hanok_detail.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HanokDetailActivity : AppCompatActivity(), OnMapReadyCallback {

    val TAG = "HanokDetailActivity TAG"

    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }

    var hanokIdx: Int? = null

    var mMap: GoogleMap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hanok_detail)

        hanokIdx = intent.getIntExtra("idx", -1)

        getMap()
        setRecyclerView()
    }

    private fun setRecyclerView() {
        //추천 리싸이클러뷰
        var roomsList: ArrayList<Rooms> = ArrayList()
        roomsList.add(Rooms("싱클룸", 1, 2))
        roomsList.add(Rooms("트리플", 1, 2))
        roomsList.add(Rooms("기타", 1, 2))


        var hanOkRecyclcerViewAdapter =
            HanOkRecyclcerViewAdapter(this, roomsList)
        rv_ac_hanok_detail_room.adapter = hanOkRecyclcerViewAdapter
        rv_ac_hanok_detail_room.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    }

    private fun getHanokDetailResponse(idx: Int) {
        val getHanokDetailResponse = networkService.getHanokDetailResponse("application/json", idx)
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
        val location = LatLng(items.latitude, items.longitude)
        if (mMap != null) addMarker(mMap, location)
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
