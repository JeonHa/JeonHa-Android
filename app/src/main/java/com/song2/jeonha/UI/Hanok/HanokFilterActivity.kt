package com.song2.jeonha.UI.Hanok

import android.app.Dialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.LinearLayout
import android.widget.RadioButton
import com.song2.jeonha.UI.Hanok.adapter.HanokListAdapter
import com.song2.jeonha.UI.Hanok.data.HanokItem
import com.song2.jeonha.Network.ApplicationController
import com.song2.jeonha.Network.Get.GetHanokListResponse
import com.song2.jeonha.Network.NetworkService
import com.song2.jeonha.R
import kotlinx.android.synthetic.main.activity_hanok_filter.*
import kotlinx.android.synthetic.main.dialog_hanok.*
import kotlinx.android.synthetic.main.toolbar_hanok_filter.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HanokFilterActivity : AppCompatActivity() {

    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }

    var sort: Int? = null

    val TAG = "HanokFIlterActivity TAG"

    var hanokListAdapter: HanokListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hanok_filter)

        getHanokListResponse(0)
        setOnBtnClickListener()
    }


    private fun setCheckedListenerOnRadioButton(radioButton: RadioButton?, i : Int,dialog: Dialog) {
        if(radioButton!!.id == i){
            setSort(radioButton.id,dialog)
        }
    }

    private fun setOnBtnClickListener() {
        img_hanok_filter_act_filter.setOnClickListener {
            showDialog()
        }

        btn_tb_class_list_back.setOnClickListener {
            finish()
        }
    }

    private fun showDialog() {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_hanok)
        dialog.show()

        setDialogClickListener(dialog)
    }

    private fun setDialogClickListener(dialog: Dialog) {

        dialog.rg_dialog_hanok.setOnCheckedChangeListener { radioGroup, i ->
            setCheckedListenerOnRadioButton(dialog.dialog_hanok_maphogu,i,dialog)
            setCheckedListenerOnRadioButton(dialog.dialog_hanok_cityhall,i,dialog)
            setCheckedListenerOnRadioButton(dialog.dialog_hanok_dongdaemoon,i,dialog)
            setCheckedListenerOnRadioButton(dialog.dialog_hanok_gangnam,i,dialog)
            setCheckedListenerOnRadioButton(dialog.dialog_hanok_hongdae,i,dialog)
            setCheckedListenerOnRadioButton(dialog.dialog_hanok_gogung,i,dialog)
            setCheckedListenerOnRadioButton(dialog.dialog_hanok_yongsan,i,dialog)
            setCheckedListenerOnRadioButton(dialog.dialog_hanok_jamsil,i,dialog)
            setCheckedListenerOnRadioButton(dialog.dialog_hanok_etc,i,dialog)
        }

        dialog.btn_dialog_hanok_cancel.setOnClickListener {
            dialog.dismiss()
        }

        dialog.btn_dialog_hanok_ok.setOnClickListener {
            dialog.dismiss()

            if (sort != null) getHanokListResponse(sort!!)
        }
    }

    private fun setSort(id:Int,dialog: Dialog) {
        when (id) {
            dialog.dialog_hanok_maphogu.id -> sort = 1
            dialog.dialog_hanok_cityhall.id -> sort = 2
            dialog.dialog_hanok_dongdaemoon.id -> sort = 3
            dialog.dialog_hanok_gangnam.id -> sort = 4
            dialog.dialog_hanok_hongdae.id -> sort = 5
            dialog.dialog_hanok_gogung.id -> sort = 6
            dialog.dialog_hanok_yongsan.id -> sort = 7
            dialog.dialog_hanok_jamsil.id -> sort = 8
            dialog.dialog_hanok_etc.id -> sort = 9
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
