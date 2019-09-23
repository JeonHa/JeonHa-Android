package com.song2.jeonha.Main

import android.app.Activity
import android.content.AbstractThreadedSyncAdapter
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.google.zxing.integration.android.IntentIntegrator
import com.song2.jeonha.Main.QRcode.QRcodeActivity
import com.song2.jeonha.R
import kotlinx.android.synthetic.main.activity_main.*

import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity() {

    lateinit var programListRecyclerViewAdapter: ProgramListRecyclerViewAdapter
    var arrayListData : ArrayList<ProgramData> = ArrayList()

    lateinit var titleListRecyclerViewAdapter: TitleListRecyclerViewAdapter
    var titleListData : ArrayList<TitleData> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        iv_main_QRtest.setOnClickListener {
            onQrcodeScanner()
            startActivity<QRcodeActivity>()
        }

        setClassProgramRecyclerView()
        setTitleRecyclerView()

    }

    fun setClassProgramRecyclerView(){

        arrayListData.add(ProgramData(0,"https://post-phinf.pstatic.net/MjAxNzA5MjBfMTAx/MDAxNTA1ODc3OTc0NDEz.Kvi6RAECepI8fweR4ddrgFEdRJzU2KC-WLmFRTmuSEEg.BkaL2u6ZTT-wn7agPveSnOYSwxodVIeKzUc_pL5PRrgg.JPEG/trd032tg13012.jpg?type=w800_q75","전통 차 만들기"))
        arrayListData.add(ProgramData(1,"https://res.klook.com/images/fl_lossy.progressive,q_65/c_fill,w_1295,h_720,f_auto/w_80,x_15,y_15,g_south_west,l_klook_water/activities/cpdq3jxrnhdmjvow79qs/.jpg","한복 체험"))
        arrayListData.add(ProgramData(2,"https://file2.nocutnews.co.kr/newsroom/image/2011/09/07170436417004_61000040.jpg","떡메치기"))
        arrayListData.add(ProgramData(3,"https://post-phinf.pstatic.net/MjAxNzA5MjBfMTAx/MDAxNTA1ODc3OTc0NDEz.Kvi6RAECepI8fweR4ddrgFEdRJzU2KC-WLmFRTmuSEEg.BkaL2u6ZTT-wn7agPveSnOYSwxodVIeKzUc_pL5PRrgg.JPEG/trd032tg13012.jpg?type=w800_q75","전통 차 만들기"))
        arrayListData.add(ProgramData(4,"https://res.klook.com/images/fl_lossy.progressive,q_65/c_fill,w_1295,h_720,f_auto/w_80,x_15,y_15,g_south_west,l_klook_water/activities/cpdq3jxrnhdmjvow79qs/.jpg","한복 체험"))
        arrayListData.add(ProgramData(5,"https://post-phinf.pstatic.net/MjAxNzA5MjBfMTAx/MDAxNTA1ODc3OTc0NDEz.Kvi6RAECepI8fweR4ddrgFEdRJzU2KC-WLmFRTmuSEEg.BkaL2u6ZTT-wn7agPveSnOYSwxodVIeKzUc_pL5PRrgg.JPEG/trd032tg13012.jpg?type=w800_q75","전통 차 만들기"))
        arrayListData.add(ProgramData(6,"https://res.klook.com/images/fl_lossy.progressive,q_65/c_fill,w_1295,h_720,f_auto/w_80,x_15,y_15,g_south_west,l_klook_water/activities/cpdq3jxrnhdmjvow79qs/.jpg","한복 체험"))
/*
        arrayListData.add(ProgramData(2,"http://mblogthumb3.phinf.naver.net/20130325_206/tramedi_expo_1364189236351TsOnD_JPEG/%C7%D1%BE%E0_%B4%D9%B8%AE%B1%E2_3.jpg?type=w420","한약 다리기"))
        arrayListData.add(ProgramData(3,"https://res.klook.com/images/fl_lossy.progressive,q_65/c_fill,w_1295,h_720,f_auto/w_80,x_15,y_15,g_south_west,l_klook_water/activities/cpdq3jxrnhdmjvow79qs/.jpg","한복 체"))
        arrayListData.add(ProgramData(4,"http://mblogthumb3.phinf.naver.net/20130325_206/tramedi_expo_1364189236351TsOnD_JPEG/%C7%D1%BE%E0_%B4%D9%B8%AE%B1%E2_3.jpg?type=w420","한약 다리기"))
*/

        programListRecyclerViewAdapter = ProgramListRecyclerViewAdapter(this, arrayListData)
        rv_main_act_class_list.adapter = programListRecyclerViewAdapter
        rv_main_act_class_list.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false)

    }

    fun setTitleRecyclerView(){
        titleListData.add(TitleData(1,"https://file2.nocutnews.co.kr/newsroom/image/2011/09/07170436417004_61000040.jpg","떡메치기"))
        titleListData.add(TitleData(1,"https://post-phinf.pstatic.net/MjAxNzA5MjBfMTAx/MDAxNTA1ODc3OTc0NDEz.Kvi6RAECepI8fweR4ddrgFEdRJzU2KC-WLmFRTmuSEEg.BkaL2u6ZTT-wn7agPveSnOYSwxodVIeKzUc_pL5PRrgg.JPEG/trd032tg13012.jpg?type=w800_q75","전통 차 다리"))
        titleListData.add(TitleData(1,"https://res.klook.com/images/fl_lossy.progressive,q_65/c_fill,w_1295,h_720,f_auto/w_80,x_15,y_15,g_south_west,l_klook_water/activities/cpdq3jxrnhdmjvow79qs/.jpg","전통한복 체험"))
        titleListData.add(TitleData(1,"https://post-phinf.pstatic.net/MjAxNzA5MjBfMTAx/MDAxNTA1ODc3OTc0NDEz.Kvi6RAECepI8fweR4ddrgFEdRJzU2KC-WLmFRTmuSEEg.BkaL2u6ZTT-wn7agPveSnOYSwxodVIeKzUc_pL5PRrgg.JPEG/trd032tg13012.jpg?type=w800_q75","부채춤"))
        titleListData.add(TitleData(1,"https://post-phinf.pstatic.net/MjAxNzA5MjBfMTAx/MDAxNTA1ODc3OTc0NDEz.Kvi6RAECepI8fweR4ddrgFEdRJzU2KC-WLmFRTmuSEEg.BkaL2u6ZTT-wn7agPveSnOYSwxodVIeKzUc_pL5PRrgg.JPEG/trd032tg13012.jpg?type=w800_q75","부채춤"))

        titleListRecyclerViewAdapter = TitleListRecyclerViewAdapter(this, titleListData)
        rv_main_act_title_list.adapter = titleListRecyclerViewAdapter
        rv_main_act_title_list.layoutManager = LinearLayoutManager(this)
    }

    private fun onQrcodeScanner() {

        Toast.makeText(this, "Camera", Toast.LENGTH_SHORT).show()

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

}
