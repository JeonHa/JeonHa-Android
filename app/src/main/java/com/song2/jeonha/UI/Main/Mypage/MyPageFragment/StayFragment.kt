package com.song2.jeonha.UI.Main.Mypage.MyPageFragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.song2.jeonha.DB.SharedPreferenceController
import com.song2.jeonha.UI.Main.Mypage.MyPageFragment.GetMyBookingList.GetBookingHanokListResponse
import com.song2.jeonha.UI.Main.Mypage.MyPageFragment.GetMyBookingList.HanokData
import com.song2.jeonha.Network.ApplicationController
import com.song2.jeonha.Network.NetworkService

import com.song2.jeonha.R
import kotlinx.android.synthetic.main.fragment_stay.*
import org.jetbrains.anko.support.v4.ctx
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * A simple [Fragment] subclass.
 */
class StayFragment : Fragment() {
    val networkService : NetworkService by lazy {
        ApplicationController.instance.networkService
    }

    lateinit var hanokStayBookingRecyclerViewAdapter: HanokStayBookingRecyclerViewAdapter
    var arrayListData : ArrayList<HanokData?> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_stay, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        getHanokBookingListResponse()
    }

    private fun getHanokBookingListResponse() {
        val getHanokBookingListResponse = networkService.getHanokBookingListResponse(
            SharedPreferenceController.getAccessToken(ctx))
        getHanokBookingListResponse.enqueue(object : Callback<GetBookingHanokListResponse> {
            override fun onFailure(call: Call<GetBookingHanokListResponse>, t: Throwable) {
                Log.e("GET Hanok Booking List Fail",t.toString())
            }

            override fun onResponse(
                call: Call<GetBookingHanokListResponse>,
                response: Response<GetBookingHanokListResponse>
            ) {
                if(response.isSuccessful){
                    if(response.body()!!.status == 200){
                        tv_stay_number.text= response.body()!!.data.totalCnt.toString()+"개 신청하였소"
                        arrayListData=response.body()!!.data.list
                        hanokStayBookingRecyclerViewAdapter = HanokStayBookingRecyclerViewAdapter(ctx, arrayListData)
                        hanokStayBookingRecyclerViewAdapter.notifyDataSetChanged()
                        rv_stay_contents.adapter = hanokStayBookingRecyclerViewAdapter
                        rv_stay_contents.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                    }
                }
            }
        })
    }
}
