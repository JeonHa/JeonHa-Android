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

        iv_main_mypage.setOnClickListener {
            startActivity<MypageActivity>()
        }

        iv_main_QRtest.setOnClickListener {
            onQrcodeScanner()
        }

        iv_main_act_more_btn.setOnClickListener {
            startActivity<ClassListActivity>()
        }

        setTitleRecyclerView()
        stayRequest()

        switch_main_main_act_selector.setOnCheckedChangeListener { button, checked ->
            if(!checked){
                //한옥통신
                Log.e("한옥통신", "In   "+switch_main_main_act_selector.isChecked)
                //switch_main_main_act_selector.toggle()

                stayRequest()

                iv_main_act_more_btn.setOnClickListener {
                    //startActivity<ClassListActivity>()
                }

            }else{
                //클래스통신
                Log.e("클래스통신", "In   "+switch_main_main_act_selector.isSelected())
                //switch_main_main_act_selector.toggle()

                classRequest()

                iv_main_act_more_btn.setOnClickListener {
                    startActivity<ClassListActivity>()
                }
            }
        }


    }

    fun stayRequest(){
        Log.e("한옥통신", "fun")

        arrayListData.clear()
        arrayListData.add(ProgramData(0,"https://res.klook.com/images/fl_lossy.progressive,q_65/c_fill,w_1295,h_720,f_auto/w_80,x_15,y_15,g_south_west,l_klook_water/activities/cpdq3jxrnhdmjvow79qs/.jpg","한옥스테이"))
        arrayListData.add(ProgramData(1,"https://res.klook.com/images/fl_lossy.progressive,q_65/c_fill,w_1295,h_720,f_auto/w_80,x_15,y_15,g_south_west,l_klook_water/activities/cpdq3jxrnhdmjvow79qs/.jpg","스떼이~~~!"))
        arrayListData.add(ProgramData(2,"https://file2.nocutnews.co.kr/newsroom/image/2011/09/07170436417004_61000040.jpg","한옥한옥"))
        arrayListData.add(ProgramData(3,"https://post-phinf.pstatic.net/MjAxNzA5MjBfMTAx/MDAxNTA1ODc3OTc0NDEz.Kvi6RAECepI8fweR4ddrgFEdRJzU2KC-WLmFRTmuSEEg.BkaL2u6ZTT-wn7agPveSnOYSwxodVIeKzUc_pL5PRrgg.JPEG/trd032tg13012.jpg?type=w800_q75","하녹하녹"))
        arrayListData.add(ProgramData(4,"https://res.klook.com/images/fl_lossy.progressive,q_65/c_fill,w_1295,h_720,f_auto/w_80,x_15,y_15,g_south_west,l_klook_water/activities/cpdq3jxrnhdmjvow79qs/.jpg","한옥옥옥ㅇ"))
        arrayListData.add(ProgramData(5,"https://post-phinf.pstatic.net/MjAxNzA5MjBfMTAx/MDAxNTA1ODc3OTc0NDEz.Kvi6RAECepI8fweR4ddrgFEdRJzU2KC-WLmFRTmuSEEg.BkaL2u6ZTT-wn7agPveSnOYSwxodVIeKzUc_pL5PRrgg.JPEG/trd032tg13012.jpg?type=w800_q75","ㅎ_ㅎ"))
        arrayListData.add(ProgramData(6,"https://res.klook.com/images/fl_lossy.progressive,q_65/c_fill,w_1295,h_720,f_auto/w_80,x_15,y_15,g_south_west,l_klook_water/activities/cpdq3jxrnhdmjvow79qs/.jpg","한옥"))

        setClassProgramRecyclerView()
    }

    fun classRequest(){
        Log.e("클래스통신", "fun")

        arrayListData.clear()
        arrayListData.add(ProgramData(0,"https://post-phinf.pstatic.net/MjAxNzA5MjBfMTAx/MDAxNTA1ODc3OTc0NDEz.Kvi6RAECepI8fweR4ddrgFEdRJzU2KC-WLmFRTmuSEEg.BkaL2u6ZTT-wn7agPveSnOYSwxodVIeKzUc_pL5PRrgg.JPEG/trd032tg13012.jpg?type=w800_q75","전통 차 만들기"))
        arrayListData.add(ProgramData(1,"https://res.klook.com/images/fl_lossy.progressive,q_65/c_fill,w_1295,h_720,f_auto/w_80,x_15,y_15,g_south_west,l_klook_water/activities/cpdq3jxrnhdmjvow79qs/.jpg","한복 체험"))
        arrayListData.add(ProgramData(2,"https://file2.nocutnews.co.kr/newsroom/image/2011/09/07170436417004_61000040.jpg","떡메치기"))
        arrayListData.add(ProgramData(3,"https://post-phinf.pstatic.net/MjAxNzA5MjBfMTAx/MDAxNTA1ODc3OTc0NDEz.Kvi6RAECepI8fweR4ddrgFEdRJzU2KC-WLmFRTmuSEEg.BkaL2u6ZTT-wn7agPveSnOYSwxodVIeKzUc_pL5PRrgg.JPEG/trd032tg13012.jpg?type=w800_q75","전통 차 만들기"))
        arrayListData.add(ProgramData(4,"https://res.klook.com/images/fl_lossy.progressive,q_65/c_fill,w_1295,h_720,f_auto/w_80,x_15,y_15,g_south_west,l_klook_water/activities/cpdq3jxrnhdmjvow79qs/.jpg","한복 체험"))
        arrayListData.add(ProgramData(5,"https://post-phinf.pstatic.net/MjAxNzA5MjBfMTAx/MDAxNTA1ODc3OTc0NDEz.Kvi6RAECepI8fweR4ddrgFEdRJzU2KC-WLmFRTmuSEEg.BkaL2u6ZTT-wn7agPveSnOYSwxodVIeKzUc_pL5PRrgg.JPEG/trd032tg13012.jpg?type=w800_q75","전통 차 만들기"))
        arrayListData.add(ProgramData(6,"https://res.klook.com/images/fl_lossy.progressive,q_65/c_fill,w_1295,h_720,f_auto/w_80,x_15,y_15,g_south_west,l_klook_water/activities/cpdq3jxrnhdmjvow79qs/.jpg","한복 체험"))

        setClassProgramRecyclerView()
    }


    fun setClassProgramRecyclerView(){

        programListRecyclerViewAdapter = ProgramListRecyclerViewAdapter(this, arrayListData)
        programListRecyclerViewAdapter.notifyDataSetChanged()
        rv_main_act_class_list.adapter = programListRecyclerViewAdapter
        rv_main_act_class_list.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false)

    }

    fun setTitleRecyclerView(){
        titleListData.add(TitleData(1,"https://file2.nocutnews.co.kr/newsroom/image/2011/09/07170436417004_61000040.jpg","떡메치기"))
        titleListData.add(TitleData(1,"https://post-phinf.pstatic.net/MjAxNzA5MjBfMTAx/MDAxNTA1ODc3OTc0NDEz.Kvi6RAECepI8fweR4ddrgFEdRJzU2KC-WLmFRTmuSEEg.BkaL2u6ZTT-wn7agPveSnOYSwxodVIeKzUc_pL5PRrgg.JPEG/trd032tg13012.jpg?type=w800_q75","전통 차 다리"))
        titleListData.add(TitleData(1,"https://res.klook.com/images/fl_lossy.progressive,q_65/c_fill,w_1295,h_720,f_auto/w_80,x_15,y_15,g_south_west,l_klook_water/activities/cpdq3jxrnhdmjvow79qs/.jpg","전통한복 체험"))
        titleListData.add(TitleData(1,"https://post-phinf.pstatic.net/MjAxNzA5MjBfMTAx/MDAxNTA1ODc3OTc0NDEz.Kvi6RAECepI8fweR4ddrgFEdRJzU2KC-WLmFRTmuSEEg.BkaL2u6ZTT-wn7agPveSnOYSwxodVIeKzUc_pL5PRrgg.JPEG/trd032tg13012.jpg?type=w800_q75","부채춤"))
        titleListData.add(TitleData(1,"https://post-phinf.pstatic.net/MjAxNzA5MjBfMTAx/MDAxNTA1ODc3OTc0NDEz.Kvi6RAECepI8fweR4ddrgFEdRJzU2KC-WLmFRTmuSEEg.BkaL2u6ZTT-wn7agPveSnOYSwxodVIeKzUc_pL5PRrgg.JPEG/trd032tg13012.jpg?type=w800_q75","부채춤"))

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

}
