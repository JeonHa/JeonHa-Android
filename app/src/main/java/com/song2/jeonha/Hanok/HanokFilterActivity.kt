package com.song2.jeonha.Hanok

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.LinearLayout
import com.song2.jeonha.Hanok.adapter.HanokListAdapter
import com.song2.jeonha.Hanok.data.HanokItem
import com.song2.jeonha.Network.ApplicationController
import com.song2.jeonha.Network.Get.GetHanokListResponse
import com.song2.jeonha.Network.NetworkService
import com.song2.jeonha.R
import kotlinx.android.synthetic.main.activity_hanok_filter.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HanokFilterActivity : AppCompatActivity() {

    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }

    var sort = 1


    var hanokListAdapter: HanokListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hanok_filter)


        getHanokListResponse(0)
    }

    private fun setRecyclerView(item: ArrayList<HanokItem>) {
        hanokListAdapter = HanokListAdapter(this, item)
        rv_hanok_filter_act.let {
            it.adapter = hanokListAdapter
            it.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        }
    }



private fun getHanokListResponse(sort: Int) {
    val getHanokListResponse = networkService.getHanokListResponse("application/json", sort)
    getHanokListResponse.enqueue(object : Callback<GetHanokListResponse> {
        override fun onFailure(call: Call<GetHanokListResponse>, t: Throwable) {
            Log.e("HanokList Fail", t.toString())
        }

        override fun onResponse(
            call: Call<GetHanokListResponse>,
            response: Response<GetHanokListResponse>
        ) {
            if (response.isSuccessful) {
                if (response.body()!!.status == 200) {
                    val tmp: ArrayList<HanokItem> = response.body()!!.data!!

                    setRecyclerView(tmp)
                }
            }
        }
    })
}


}
