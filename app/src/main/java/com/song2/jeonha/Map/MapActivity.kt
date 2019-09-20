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
import android.widget.Toast
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.song2.jeonha.R
import org.jetbrains.anko.toast

class MapActivity : AppCompatActivity(), OnMapReadyCallback, ActivityCompat.OnRequestPermissionsResultCallback {

    val TAG: String = "MapActivity TAG"

    private val PERMISSIONS_REQUEST_CODE = 100
    private val PERMISSIONS = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )
/*

    private val hasFineLocationPermission =
        ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
    private val hasCoarseLocationPermission =
        ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)

*/

    private var fusedLocationClient: FusedLocationProviderApi? = null
    private var location: Location? = null
    private var locationRequest : LocationRequest? = null

    private var currentLatLng: LatLng? = null

    var mMap: GoogleMap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

/*
        getPermission()
        createLocationRequest()*/
        addMap()
    }
/*
    internal var locationCallback: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult?) {
            super.onLocationResult(locationResult)

            val locationList = locationResult!!.locations

            if (locationList.size > 0) {
                location = locationList[locationList.size - 1]

                currentLatLng = LatLng(location!!.latitude, location!!.longitude)
            }
        }
    }*/

   /* private fun getPermission() {

        if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED && hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED) {
            *//**
             * 권한을 허용했다면 위치를 요청합니다.
             *//*
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
    }*/
    private fun createLocationRequest() {
        locationRequest = LocationRequest()
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY) //create a location request
            .setInterval(60 * 1000) //이 메소드는 앱에서 위치 업데이트 수신간격을 밀리초단위로 설정한다.
    }
//
//    private fun requestLocation() {
//        if (hasFineLocationPermission != PackageManager.PERMISSION_GRANTED && hasCoarseLocationPermission != PackageManager.PERMISSION_GRANTED) {
//            /**
//             * 권한 요청이 허가되어 있지 않은 경우
//             * 권한 요청을 합니다.
//             */
//            getPermission()
//        }
//
//        fusedLocationClient = GoogleApiClient
//        fusedLocationClient.requestLocationUpdates(locationCallback,locationRequest,Looper.myLooper())
//    }

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
}
