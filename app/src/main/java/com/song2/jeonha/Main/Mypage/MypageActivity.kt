package com.song2.jeonha.Main.Mypage

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.song2.jeonha.Hanok.data.HanokItem
import com.song2.jeonha.Network.ApplicationController
import com.song2.jeonha.Network.Get.GetHanokListResponse
import com.song2.jeonha.Network.Get.GetStampResponse
import com.song2.jeonha.Network.NetworkService
import kotlinx.android.synthetic.main.activity_mypage.*
import org.jetbrains.anko.startActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.graphics.BitmapFactory
import android.graphics.Bitmap
import android.support.v4.app.SupportActivity
import android.support.v4.app.SupportActivity.ExtraData
import android.support.v4.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.support.v4.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.widget.Button
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.song2.jeonha.R
import org.jetbrains.anko.toast


class MypageActivity : AppCompatActivity() {
    val networkService : NetworkService by lazy {
        ApplicationController.instance.networkService
    }
    internal var ImageView = arrayOfNulls<ImageView>(9)
    internal var Rid_ImageView = arrayOf<Int>(
        R.id.iv_mypage_stamp0,
        R.id.iv_mypage_stamp1,
        R.id.iv_mypage_stamp2,
        R.id.iv_mypage_stamp3,
        R.id.iv_mypage_stamp4,
        R.id.iv_mypage_stamp5,
        R.id.iv_mypage_stamp6,
        R.id.iv_mypage_stamp7,
        R.id.iv_mypage_stamp8
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.song2.jeonha.R.layout.activity_mypage)


        getStampHResponse()
        // back button
        iv_mypage_back_btn.setOnClickListener {
            finish()
        }

        tv_mypage_myList.setOnClickListener {
            startActivity<ApplyHistoryActivity>()
        }
    }
    private fun getStampHResponse() {
        val getStampListResponse = networkService.getStampResponse("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZHgiOjMsImlhdCI6MTU2OTUwMDMxMCwiZXhwIjoxNTcwMTA1MTEwLCJpc3MiOiJqZW9uaGEyMDE5In0.BEilMzrtMn4JPvotwmFqEmMEM5GNzqAPqk5I_Sqise4")
        getStampListResponse.enqueue(object : Callback<GetStampResponse> {
            override fun onFailure(call: Call<GetStampResponse>, t: Throwable) {
                Log.e("StampList Fail",t.toString())
            }

            override fun onResponse(
                call: Call<GetStampResponse>,
                response: Response<GetStampResponse>
            ) {
                if(response.isSuccessful){
                    if(response.body()!!.status == 200){
                        for (i in 0..response.body()!!.data.size-1) {

                           val INDEX: Int
                            INDEX = i
                            ImageView[i] = findViewById(Rid_ImageView[i]) as ImageView
                           Glide.with(this@MypageActivity).load(response.body()!!.data[i]!!.img).into(
                               ImageView[INDEX]!!
                           )
                        }

                    }
                }
            }
        })
    }
}
