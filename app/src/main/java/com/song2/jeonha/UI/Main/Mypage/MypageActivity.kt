package com.song2.jeonha.UI.Main.Mypage

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.util.Log
import com.song2.jeonha.Network.ApplicationController
import com.song2.jeonha.Network.Get.GetStampResponse
import com.song2.jeonha.Network.NetworkService
import kotlinx.android.synthetic.main.activity_mypage.*
import org.jetbrains.anko.startActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.song2.jeonha.DB.SharedPreferenceController
import com.song2.jeonha.R
import com.song2.jeonha.UI.Login.LoginActivity


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
        tv_mypage_logout.setOnClickListener {
            SharedPreferenceController.clearAccessToken(this)
            ActivityCompat.finishAffinity(this)
            Toast.makeText(this,"로그아웃 되었습니다.",Toast.LENGTH_SHORT).show()
            finish()
        }

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
        val getStampListResponse = networkService.getStampResponse(SharedPreferenceController.getAccessToken(this))
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
                        tv_mypage_name.text=response.body()!!.data.userName+"님\n오늘도\n강녕하시옵소서"
                        for (i in 0..response.body()!!.data.stampList.size-1) {

                           val INDEX: Int
                            INDEX = i
                            ImageView[i] = findViewById(Rid_ImageView[i]) as ImageView
                           Glide.with(this@MypageActivity).load(response.body()!!.data.stampList[i]!!.img).into(
                               ImageView[INDEX]!!
                           )
                        }

                    }
                }
            }
        })
    }
}
