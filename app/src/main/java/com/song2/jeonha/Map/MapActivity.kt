package com.song2.jeonha.Map

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.View
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.song2.jeonha.Map.data.MapData
import com.song2.jeonha.Network.ApplicationController
import com.song2.jeonha.Network.Get.GetHanokMapResponse
import com.song2.jeonha.Network.NetworkService
import com.song2.jeonha.R
import kotlinx.android.synthetic.main.activity_map.*
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MapActivity : AppCompatActivity(), OnMapReadyCallback,
    ActivityCompat.OnRequestPermissionsResultCallback {

    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }

    val TAG: String = "MapActivity TAG"

    private val PERMISSIONS_REQUEST_CODE = 100
    private val PERMISSIONS = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )


    private var fusedLocationClient: FusedLocationProviderClient? = null
    private var location: Location? = null
    private var locationRequest: LocationRequest? = null

    private var currentLatLng: LatLng? = null

    private var markerList: ArrayList<Marker>? = ArrayList()

    var mMap: GoogleMap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)


        getPermission()
        createLocationRequest()
        addMap()
        getHanokMapResponse()
    }

    internal var locationCallback: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult?) {
            super.onLocationResult(locationResult)

            val locationList = locationResult!!.locations

            if (locationList.size > 0) {
                mMap!!.clear()

                location = locationList[locationList.size - 1]

                currentLatLng = LatLng(location!!.latitude, location!!.longitude)

                focusToPosition(currentLatLng!!)
            }
        }
    }

    private fun focusToPosition(currentLatLng: LatLng) {
        val markerOptions = MarkerOptions()
        markerOptions.position(currentLatLng)
            .title("현재 위치")
            .icon(BitmapDescriptorFactory.fromResource(R.drawable.mylocation_icon))

        mMap!!.addMarker(markerOptions)
        mMap!!.moveCamera(CameraUpdateFactory.newLatLng(currentLatLng))
        mMap!!.animateCamera(CameraUpdateFactory.zoomTo(5f))
    }

    private fun getPermission() {

        val hasFineLocationPermission =
            ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
        val hasCoarseLocationPermission =
            ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)

        if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED && hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED) {
            /**
             * 권한을 허용했다면 위치를 요청합니다.
             */
            requestLocation()

        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    PERMISSIONS[0]
                )
            ) {

                toast("이 앱을 실행하려면 위치 접근 권한이 필요합니다.")
                ActivityCompat.requestPermissions(
                    this, PERMISSIONS,
                    PERMISSIONS_REQUEST_CODE
                )

            } else {

                ActivityCompat.requestPermissions(
                    this, PERMISSIONS,
                    PERMISSIONS_REQUEST_CODE
                )

            }
        }
    }

    private fun createLocationRequest() {
        locationRequest = LocationRequest()
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
            .setInterval(60 * 1000) //위치 업데이트 수신간격(밀리초 단위)
    }

    private fun requestLocation() {

        val hasFineLocationPermission =
            ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
        val hasCoarseLocationPermission =
            ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
        if (hasFineLocationPermission != PackageManager.PERMISSION_GRANTED && hasCoarseLocationPermission != PackageManager.PERMISSION_GRANTED) {
            /**
             * 권한 요청이 허가되어 있지 않은 경우
             * 권한 요청을 합니다.
             */
            getPermission()
        }

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        locationRequest = LocationRequest.create().let {
            it.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
            it.setInterval(60 * 1000)
        }

        fusedLocationClient!!.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.myLooper()
        )
    }

    private fun addMap() {
        Log.d(TAG, "addMap()")

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.frag_map) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        Log.d(TAG, "onMapReady()")

        mMap = googleMap

        initLocation(mMap)
    }

    private fun initLocation(mMap: GoogleMap?) {
        val defaultLocation = LatLng(37.56, 126.97) //서울

        val markerOptions = MarkerOptions().let {
            it.position(defaultLocation)
                .title("위치 정보가 수집되지 않았습니다.")
        }

        mMap?.let {
            it.addMarker(markerOptions)
            it.moveCamera(CameraUpdateFactory.newLatLng(defaultLocation))
            it.animateCamera(CameraUpdateFactory.zoomTo(15f))
        }
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop()")

        if (fusedLocationClient != null)
            fusedLocationClient!!.removeLocationUpdates(locationCallback)
    }

    fun getHanokMapResponse() {
        val getHanokMapResponse = networkService.getHanokMapResponse("application/json")
        getHanokMapResponse.enqueue(object : Callback<GetHanokMapResponse> {
            override fun onFailure(call: Call<GetHanokMapResponse>, t: Throwable) {
                Log.e("MapResponse fail", t.toString())
            }

            override fun onResponse(
                call: Call<GetHanokMapResponse>,
                response: Response<GetHanokMapResponse>
            ) {
                if (response.isSuccessful) {
                    if (response.body()!!.status == 200) {
                        val mapDataList: ArrayList<MapData> = response.body()!!.data!!
                        floatMarker(mapDataList)
                        Log.d(TAG, mapDataList.toString())

                    }
                }
            }
        })
    }

    private fun floatMarker(items: ArrayList<MapData>) {
        markerList!!.clear()
        for (i in items.indices) {
            val markerLatLng = LatLng(items[i].latitude, items[i].longitude)
            val markerOptions = MarkerOptions()
            markerOptions.position(markerLatLng)
//                    .title(items[i].name)
//                    .snippet(items[i].place())
            val marker: Marker = mMap!!.addMarker(markerOptions)

            markerList!!.add(i, marker)
        }
/*
        val markerOptions = MarkerOptions().position(LatLng(37.56, 126.97))
        val marker: Marker = mMap!!.addMarker(markerOptions)
        markerList!!.add(0,marker)
        setMarkerClickListener(mMap,items)*/
    }

    private fun setMarkerClickListener(
        mMap: GoogleMap?,
        mapDataList: ArrayList<MapData>
    ) {
        mMap!!.setOnMarkerClickListener {
            var idx: Int = -1

            for (i in 0 until markerList!!.size) {
                markerList!![i].tag = markerList!![i] == it

                if (markerList!![i].tag == true) idx = i
                Log.v("마커 클릭 ", i.toString() + " 는 " + markerList!![i].tag)
            }

            setHanokDetailView(idx,mapDataList)
            return@setOnMarkerClickListener false
        }
    }

    private fun setHanokDetailView(
        idx: Int,
        mapDataList: ArrayList<MapData>
    ) {
        ll_map_act_info.visibility = View.VISIBLE
        txt_map_act_stay_region.text = mapDataList[idx].place
        txt_map_act_stay_hanok_name.text = mapDataList[idx].name
    }

}
