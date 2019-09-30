package com.song2.jeonha.UI.Class

import android.app.Dialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Window
import android.widget.TextView
import com.song2.jeonha.Network.ApplicationController
import com.song2.jeonha.Network.Get.GetClassListResponse
import com.song2.jeonha.Network.NetworkService
import com.song2.jeonha.R
import com.song2.jeonha.UI.Class.adapter.ClassListRecyclerViewAdapter
import com.song2.jeonha.UI.Main.Mypage.MyPageFragment.GetMyBookingList.ClassListItemData
import kotlinx.android.synthetic.main.activity_class_list.*
import retrofit2.Call
import retrofit2.Response

class ClassListActivity : AppCompatActivity() {

    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }

    lateinit var classListRecyclerViewAdapter: ClassListRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_class_list)

        getClassListResponse(0)

        iv_class_list_act_filter_btn.setOnClickListener {
            clickFilterBtn()
        }

        iv_class_list_act_back_btn.setOnClickListener {
            finish()
        }
    }

    fun clickFilterBtn(){

        val dlg = Dialog(this)

        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dlg.setContentView(R.layout.dialog_select_class)
        dlg.show()

        val dialogQuit = dlg.findViewById<TextView>(R.id.tv_select_class_dialog_quit_btn)
        val dialogSubmit = dlg.findViewById<TextView>(R.id.tv_select_class_dialog_submit_btn)


        val one = dlg.findViewById<TextView>(R.id.rb_select_class_dialog_monday)
        val two = dlg.findViewById<TextView>(R.id.rb_select_class_dialog_tuesday)
        val three = dlg.findViewById<TextView>(R.id.rb_select_class_dialog_wednesday)
        val four = dlg.findViewById<TextView>(R.id.rb_select_class_dialog_thursday)
        val five = dlg.findViewById<TextView>(R.id.rb_select_class_dialog_friday)
        val six = dlg.findViewById<TextView>(R.id.rb_select_class_dialog_saturday)
        val seven = dlg.findViewById<TextView>(R.id.rb_select_class_dialog_sunday)

        one.setOnClickListener {
            getClassListResponse(1)
        }

        two.setOnClickListener {
            getClassListResponse(2)
        }

        three.setOnClickListener {
            getClassListResponse(3)

        }

        four.setOnClickListener {
            getClassListResponse(4)
        }

        five.setOnClickListener {
            getClassListResponse(5)
        }


        six.setOnClickListener {
            getClassListResponse(6)
        }


        seven.setOnClickListener {
            getClassListResponse(7)
        }

        dialogQuit.setOnClickListener {
            dlg.dismiss()
        }
        dialogSubmit.setOnClickListener {
            dlg.dismiss()
        }
    }


    fun setClassRecyclerView(data: ArrayList<ClassListItemData>){

        Log.e("getProgramsKeywordResponse", data.size.toString())

        classListRecyclerViewAdapter = ClassListRecyclerViewAdapter(this, data)
        rv_class_list_act_list.adapter = classListRecyclerViewAdapter
        classListRecyclerViewAdapter.notifyDataSetChanged()
        rv_class_list_act_list.layoutManager = LinearLayoutManager(this)

    }

    fun getClassListResponse(day : Int) {

        val getClassListResponse = networkService.getClassListResponse(day)

        getClassListResponse.enqueue(object : retrofit2.Callback<GetClassListResponse> {
            override fun onFailure(call: Call<GetClassListResponse>, t: Throwable) {
                Log.e("getProgramsKeywordResponse fail", t.toString())
            }

            override fun onResponse(call: Call<GetClassListResponse>, response: Response<GetClassListResponse>) {
                if (response.isSuccessful) {
                        val responseData: GetClassListResponse = response.body()!!

                        if (responseData.data.size > 0) {
                            for (i in responseData.data.indices)
                                Log.e("classList success", "test"+responseData.data[i].weekday)

                            setClassRecyclerView(responseData.data)

                        } else {
                            Log.e("classList success", ":::test")
                        }

                }
            }
        })
    }
}
