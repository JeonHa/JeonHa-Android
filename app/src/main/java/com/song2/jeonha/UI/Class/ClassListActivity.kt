package com.song2.jeonha.UI.Class

import android.app.Dialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.Window
import android.widget.RadioButton
import android.widget.RelativeLayout
import android.widget.TextView
import com.song2.jeonha.UI.Class.adapter.ClassListRecyclerViewAdapter
import com.song2.jeonha.UI.Class.data.ClassData
import com.song2.jeonha.R
import kotlinx.android.synthetic.main.activity_class_list.*

class ClassListActivity : AppCompatActivity() {


    lateinit var classListRecyclerViewAdapter: ClassListRecyclerViewAdapter
    var arrayListData : ArrayList<ClassData> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_class_list)

        setClassRecyclerView()

        iv_class_list_act_filter_btn.setOnClickListener {
            clickFilterBtn()
        }
    }

    fun clickFilterBtn(){

        val dlg = Dialog(this)

        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dlg.setContentView(R.layout.dialog_select_class)
        dlg.show()

        val dialogQuit = dlg.findViewById<TextView>(R.id.tv_select_class_dialog_quit_btn)
        val dialogSubmit = dlg.findViewById<TextView>(R.id.tv_select_class_dialog_submit_btn)

        val monContainer = dlg.findViewById<RelativeLayout>(R.id.rl_select_class_dialog_monday)
        val monRadio = dlg.findViewById<RadioButton>(R.id.rb_select_class_dialog_monday)

        monContainer.setOnClickListener {
            if (monRadio.isChecked){
                
            }
        }


        dialogQuit.setOnClickListener {
            dlg.dismiss()
        }
        dialogSubmit.setOnClickListener {
            dlg.dismiss()
        }
    }

    fun setClassRecyclerView(){

        var day : ArrayList<String> = ArrayList()

        day.add("월요일")
        day.add("화요일")
        day.add("수요일")

        arrayListData.add(
            ClassData(
                0,
                "https://post-phinf.pstatic.net/MjAxNzA5MjBfMTAx/MDAxNTA1ODc3OTc0NDEz.Kvi6RAECepI8fweR4ddrgFEdRJzU2KC-WLmFRTmuSEEg.BkaL2u6ZTT-wn7agPveSnOYSwxodVIeKzUc_pL5PRrgg.JPEG/trd032tg13012.jpg?type=w800_q75",
                "전통 차 만들기",
                day
            )
        )
        arrayListData.add(
            ClassData(
                1,
                "https://res.klook.com/images/fl_lossy.progressive,q_65/c_fill,w_1295,h_720,f_auto/w_80,x_15,y_15,g_south_west,l_klook_water/activities/cpdq3jxrnhdmjvow79qs/.jpg",
                "한복 체험",
                day
            )
        )
        arrayListData.add(
            ClassData(
                2,
                "https://file2.nocutnews.co.kr/newsroom/image/2011/09/07170436417004_61000040.jpg",
                "떡메치기",
                day
            )
        )
        arrayListData.add(
            ClassData(
                3,
                "https://post-phinf.pstatic.net/MjAxNzA5MjBfMTAx/MDAxNTA1ODc3OTc0NDEz.Kvi6RAECepI8fweR4ddrgFEdRJzU2KC-WLmFRTmuSEEg.BkaL2u6ZTT-wn7agPveSnOYSwxodVIeKzUc_pL5PRrgg.JPEG/trd032tg13012.jpg?type=w800_q75",
                "전통 차 만들기",
                day
            )
        )
        arrayListData.add(
            ClassData(
                4,
                "https://res.klook.com/images/fl_lossy.progressive,q_65/c_fill,w_1295,h_720,f_auto/w_80,x_15,y_15,g_south_west,l_klook_water/activities/cpdq3jxrnhdmjvow79qs/.jpg",
                "한복 체험",
                day
            )
        )
        arrayListData.add(
            ClassData(
                5,
                "https://post-phinf.pstatic.net/MjAxNzA5MjBfMTAx/MDAxNTA1ODc3OTc0NDEz.Kvi6RAECepI8fweR4ddrgFEdRJzU2KC-WLmFRTmuSEEg.BkaL2u6ZTT-wn7agPveSnOYSwxodVIeKzUc_pL5PRrgg.JPEG/trd032tg13012.jpg?type=w800_q75",
                "전통 차 만들기",
                day
            )
        )
        arrayListData.add(
            ClassData(
                6,
                "https://res.klook.com/images/fl_lossy.progressive,q_65/c_fill,w_1295,h_720,f_auto/w_80,x_15,y_15,g_south_west,l_klook_water/activities/cpdq3jxrnhdmjvow79qs/.jpg",
                "한복 체험",
                day
            )
        )

        classListRecyclerViewAdapter = ClassListRecyclerViewAdapter(this, arrayListData)
        rv_class_list_act_list.adapter = classListRecyclerViewAdapter
        rv_class_list_act_list.layoutManager = LinearLayoutManager(this)

    }
}
