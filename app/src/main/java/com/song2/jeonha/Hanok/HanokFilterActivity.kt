package com.song2.jeonha.Hanok

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.song2.jeonha.Hanok.data.HanokItem
import com.song2.jeonha.Network.ApplicationController
import com.song2.jeonha.Network.Get.GetHanokListResponse
import com.song2.jeonha.Network.NetworkService
import com.song2.jeonha.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HanokFilterActivity : AppCompatActivity() {

    val networkService : NetworkService by lazy {
        ApplicationController.instance.networkService
    }
    var sort = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hanok_filter)

        setRecyclerView()
    }

    private fun setRecyclerView() {

        getHanokListResponse(0)
    }

    private fun getHanokListResponse(sort: Int) {
        val getHanokListResponse = networkService.getHanokListResponse("application/json",sort)
        getHanokListResponse.enqueue(object : Callback<GetHanokListResponse> {
            override fun onFailure(call: Call<GetHanokListResponse>, t: Throwable) {
                Log.e("HanokList Fail",t.toString())
            }

            override fun onResponse(
                call: Call<GetHanokListResponse>,
                response: Response<GetHanokListResponse>
            ) {
                if(response.isSuccessful){
                    if(response.body()!!.status == 200){
                        val tmp: ArrayList<HanokItem> =  response.body()!!.data!!
//                        productOverviewRecyclerViewAdapter.dataList = tmp
//                        productOverviewRecyclerViewAdapter.notifyDataSetChanged()
                    }
                }
            }
        })
    }


}
