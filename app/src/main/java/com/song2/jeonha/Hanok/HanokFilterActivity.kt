package com.song2.jeonha.Hanok

import android.app.Dialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.RadioGroup
import com.song2.jeonha.Hanok.adapter.HanokListAdapter
import com.song2.jeonha.Hanok.data.HanokItem
import com.song2.jeonha.Network.ApplicationController
import com.song2.jeonha.Network.Get.GetHanokListResponse
import com.song2.jeonha.Network.NetworkService
import com.song2.jeonha.R
import kotlinx.android.synthetic.main.activity_hanok_filter.*
import kotlinx.android.synthetic.main.dialog_hanok.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class HanokFilterActivity : AppCompatActivity() {

    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }

    var sort = 1

    val TAG = "HanokFIlterActivity TAG"

    var hanokListAdapter: HanokListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hanok_filter)


        getHanokListResponse(0)
        setOnBtnClickListener()
    }

    private fun setOnBtnClickListener() {
        img_hanok_filter_act_filter.setOnClickListener {
            showDialog()
        }
    }

    private fun showDialog() {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_hanok)
        dialog.show()

        setDialogClickListener(dialog)
    }

    private fun setDialogClickListener(dialog: Dialog) {

        dialog.btn_dialog_hanok_cancel.setOnClickListener {
            dialog.dismiss()
        }

        dialog.btn_dialog_hanok_ok.setOnClickListener {
            dialog.dismiss()
            var sort = dialog.rg_dialog_hanok.checkedRadioButtonId
            Log.d(TAG,sort.toString()+"클릭 됨,")
            getHanokListResponse(1)
        }
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
                        val tmp: ArrayList<HanokItem> = response.body()!!.data
                        Log.d(TAG, tmp.toString())

                        setRecyclerView(tmp)
                    }
                }
            }
        })
    }


}
