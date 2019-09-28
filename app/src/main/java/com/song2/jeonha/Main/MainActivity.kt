package com.song2.jeonha.Main

import android.app.Activity
import android.content.AbstractThreadedSyncAdapter
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.Toast
import com.google.zxing.integration.android.IntentIntegrator
import com.song2.jeonha.Class.ClassListActivity
import com.google.zxing.integration.android.IntentResult
import com.song2.jeonha.Main.Mypage.MypageActivity
import com.song2.jeonha.Main.QRcode.QRcodeActivity
import com.song2.jeonha.Network.ApplicationController
import com.song2.jeonha.Network.Get.ClassPrograms
import com.song2.jeonha.Network.Get.GetMainResponse
import com.song2.jeonha.Network.Get.HanokPrograms
import com.song2.jeonha.Network.Get.MainPrograms
import com.song2.jeonha.Network.NetworkService
import com.song2.jeonha.R
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.ctx

import org.jetbrains.anko.startActivity
import retrofit2.Call
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }

    lateinit var programListRecyclerViewAdapter: ProgramListRecyclerViewAdapter
    var arrayListData: ArrayList<ProgramData> = ArrayList()
    var arrayListClassData: ArrayList<ProgramData> = ArrayList()

    lateinit var titleListRecyclerViewAdapter: TitleListRecyclerViewAdapter
    var titleListData: ArrayList<TitleData> = ArrayList()

    //lateinit var mainProgram: MainPrograms ;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        iv_main_mypage.setOnClickListener {
            startActivity<MypageActivity>()
        }

        iv_main_QRtest.setOnClickListener {
            onQrcodeScanner()
        }

        iv_main_act_more_btn.setOnClickListener {
            startActivity<ClassListActivity>()
        }

        getMainProgramsResponse()
        setClassProgramRecyclerView(arrayListData)
        setTitleRecyclerView()

        switch_main_main_act_selector.setOnCheckedChangeListener { button, checked ->
            if (!checked) {
                //한옥통신
                Log.e("한옥통신", "In   " + switch_main_main_act_selector.isChecked)
                setClassProgramRecyclerView(arrayListData)
                iv_main_act_more_btn.setOnClickListener {
                    startActivity<ClassListActivity>()
                }

            } else {
                //클래스통신
                Log.e("클래스통신", "In   " + switch_main_main_act_selector.isSelected())

                setClassProgramRecyclerView(arrayListClassData)

                iv_main_act_more_btn.setOnClickListener {
                    startActivity<ClassListActivity>()
                }
            }
        }
    }

    fun setClassProgramRecyclerView(arrayListData: ArrayList<ProgramData>) {

        programListRecyclerViewAdapter = ProgramListRecyclerViewAdapter(this, arrayListData)
        programListRecyclerViewAdapter.notifyDataSetChanged()
        rv_main_act_class_list.adapter = programListRecyclerViewAdapter
        rv_main_act_class_list.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

    }

    fun setTitleRecyclerView() {
        titleListData.add(TitleData(1, "https://file2.nocutnews.co.kr/newsroom/image/2011/09/07170436417004_61000040.jpg", "떡메치기"))
        titleListData.add(TitleData(1, "https://post-phinf.pstatic.net/MjAxNzA5MjBfMTAx/MDAxNTA1ODc3OTc0NDEz.Kvi6RAECepI8fweR4ddrgFEdRJzU2KC-WLmFRTmuSEEg.BkaL2u6ZTT-wn7agPveSnOYSwxodVIeKzUc_pL5PRrgg.JPEG/trd032tg13012.jpg?type=w800_q75", "전통 차 다리"))
        titleListData.add(TitleData(1, "https://res.klook.com/images/fl_lossy.progressive,q_65/c_fill,w_1295,h_720,f_auto/w_80,x_15,y_15,g_south_west,l_klook_water/activities/cpdq3jxrnhdmjvow79qs/.jpg", "전통한복 체험"))
        titleListData.add(TitleData(1, "https://post-phinf.pstatic.net/MjAxNzA5MjBfMTAx/MDAxNTA1ODc3OTc0NDEz.Kvi6RAECepI8fweR4ddrgFEdRJzU2KC-WLmFRTmuSEEg.BkaL2u6ZTT-wn7agPveSnOYSwxodVIeKzUc_pL5PRrgg.JPEG/trd032tg13012.jpg?type=w800_q75", "부채춤"))
        titleListData.add(TitleData(1, "https://post-phinf.pstatic.net/MjAxNzA5MjBfMTAx/MDAxNTA1ODc3OTc0NDEz.Kvi6RAECepI8fweR4ddrgFEdRJzU2KC-WLmFRTmuSEEg.BkaL2u6ZTT-wn7agPveSnOYSwxodVIeKzUc_pL5PRrgg.JPEG/trd032tg13012.jpg?type=w800_q75", "부채춤"))

        arrayListData.clear()
        titleListRecyclerViewAdapter = TitleListRecyclerViewAdapter(this, titleListData)
        rv_main_act_title_list.adapter = titleListRecyclerViewAdapter
        rv_main_act_title_list.layoutManager = LinearLayoutManager(this)
    }

    private fun onQrcodeScanner() {


        val integrator = IntentIntegrator(this)
        integrator.setBeepEnabled(false)
        integrator.captureActivity = QRcodeActivity::class.java
        integrator.initiateScan()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            val scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
            val re = scanResult.contents
            Toast.makeText(this, "$re", Toast.LENGTH_LONG).show()
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    fun getMainProgramsResponse() {

        val getProgramsKeywordResponse = networkService.getMainResponse(
            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZHgiOjEsImlhdCI6MTU2OTIwODA0NSwiZXhwIjoxNTY5ODEyODQ1LCJpc3MiOiJqZW9uaGEyMDE5In0.D9Ao9zBftj5qdd1NL8lSk_--0hPir8Du3tTZs834Afw"
        )

        getProgramsKeywordResponse.enqueue(object : retrofit2.Callback<GetMainResponse> {
            override fun onFailure(call: Call<GetMainResponse>, t: Throwable) {
                Log.e("getProgramsKeywordResponse fail", t.toString())
            }

            override fun onResponse(call: Call<GetMainResponse>, response: Response<GetMainResponse>) {
                if (response.isSuccessful) {
                    if(response.body()!!.status == 200){
                        val mainPrograms: MainPrograms = response.body()!!.data

                        if (mainPrograms != null) {
                            settingListData(mainPrograms)
                            Log.e("mainPrograms success", "test")
                        } else {
                            Log.e("mainPrograms success", ":::test")
                        }
                    }
                }
            }
        })
    }

    fun settingListData(mainPrograms: MainPrograms) {

        for (i in mainPrograms.hanokList.indices) {
            val programData = ProgramData(
                mainPrograms.hanokList.get(i).hanokIdx, mainPrograms.hanokList.get(i).thumnail, mainPrograms.hanokList.get(i).name
            );
            arrayListData.add(programData)
        }

        for (i in mainPrograms.classList.indices) {
            val programData = ProgramData(
                mainPrograms.classList.get(i).classIdx, mainPrograms.classList.get(i).thumnail, mainPrograms.classList.get(i).name
            );
            arrayListClassData.add(programData)
        }
    }

}
