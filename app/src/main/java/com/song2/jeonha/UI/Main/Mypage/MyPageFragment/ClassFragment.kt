package com.song2.jeonha.UI.Main.Mypage.MyPageFragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.song2.jeonha.DB.SharedPreferenceController
import com.song2.jeonha.UI.Main.Mypage.MyPageFragment.GetMyBookingList.ClassData
import com.song2.jeonha.UI.Main.Mypage.MyPageFragment.GetMyBookingList.GetBookingClassListResponse
import com.song2.jeonha.Network.ApplicationController
import com.song2.jeonha.Network.NetworkService

import com.song2.jeonha.R
import kotlinx.android.synthetic.main.fragment_class.*
import org.jetbrains.anko.support.v4.ctx
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * A simple [Fragment] subclass.
 */
class ClassFragment : Fragment() {
    val networkService : NetworkService by lazy {
        ApplicationController.instance.networkService
    }
    lateinit var classBookingRecyclerViewAdapter: ClassBookingRecyclerViewAdapter
    var arrayListData : ArrayList<ClassData?> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_class, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        getClassBookingListResponse()
    }

    private fun getClassBookingListResponse() {
        val getClassBookingListResponse = networkService.getClassBookingListResponse(
            SharedPreferenceController.getAccessToken(ctx))
        getClassBookingListResponse.enqueue(object : Callback<GetBookingClassListResponse> {
            override fun onFailure(call: Call<GetBookingClassListResponse>, t: Throwable) {
                Log.e("GET Class Booking List Fail",t.toString())
            }

            override fun onResponse(
                call: Call<GetBookingClassListResponse>,
                response: Response<GetBookingClassListResponse>
            ) {
                if(response.isSuccessful){
                    if(response.body()!!.status == 200){
                        tv_class_number.text= response.body()!!.data.totalCnt.toString()+"개 신청하였소"
                        arrayListData=response.body()!!.data.list
                        classBookingRecyclerViewAdapter = ClassBookingRecyclerViewAdapter(ctx, arrayListData)
                        classBookingRecyclerViewAdapter.notifyDataSetChanged()
                        rv_class_contents.adapter = classBookingRecyclerViewAdapter
                        rv_class_contents.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                    }
                }
            }
        })
    }
}
