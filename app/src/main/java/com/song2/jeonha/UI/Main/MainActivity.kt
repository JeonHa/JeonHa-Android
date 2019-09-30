package com.song2.jeonha.UI.Main

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.Toast
import com.google.zxing.integration.android.IntentIntegrator
import com.song2.jeonha.UI.Main.Mypage.MypageActivity
import com.song2.jeonha.UI.Main.QRcode.QRcodeActivity
import com.song2.jeonha.UI.Main.adapter.ProgramListRecyclerViewAdapter
import com.song2.jeonha.UI.Main.adapter.TitleListRecyclerViewAdapter
import com.song2.jeonha.UI.Main.data.ProgramData
import com.song2.jeonha.UI.Main.data.TitleData
import com.song2.jeonha.Network.ApplicationController
import com.song2.jeonha.Network.Get.GetMainResponse
import com.song2.jeonha.UI.Main.data.MainPrograms
import com.song2.jeonha.Network.NetworkService
import com.song2.jeonha.R
import com.song2.jeonha.UI.Class.ClassListActivity
import com.song2.jeonha.UI.Hanok.HanokFilterActivity
import kotlinx.android.synthetic.main.activity_main.*

import org.jetbrains.anko.startActivity
import retrofit2.Call
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    var flag_first=0

    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }

    var isVisited = false;
    var b : Boolean = false
    lateinit var programListRecyclerViewAdapter: ProgramListRecyclerViewAdapter
    var arrayListData: ArrayList<ProgramData> = ArrayList()
    var arrayListClassData: ArrayList<ProgramData> = ArrayList()

    lateinit var titleListRecyclerViewAdapter: TitleListRecyclerViewAdapter
    var titleListData: ArrayList<TitleData> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        iv_main_mypage.setOnClickListener {
            startActivity<MypageActivity>()
        }

        iv_main_QRtest.setOnClickListener {
            onQrcodeScanner()
        }

        getMainProgramsResponse()

        //setClassProgramRecyclerView(arrayListData)
        setTitleRecyclerView()

        setOnBtnClickListener()
        switch_main_main_act_selector.setOnCheckedChangeListener { button, checked ->
            if (!checked) {
                //한옥통신
                Log.e("한옥통신", "In   " + switch_main_main_act_selector.isSelected())
                b = false
                setClassProgramRecyclerView(arrayListData)

                iv_main_act_more_btn.setOnClickListener {
                    startActivity<HanokFilterActivity>()
                }

            } else {
                //클래스통신
                Log.e("클래스통신", "In   " + switch_main_main_act_selector.isSelected())
                b = true
                setClassProgramRecyclerView(arrayListClassData)
                iv_main_act_more_btn.setOnClickListener {
                    startActivity<ClassListActivity>()
                }
            }
        }
    }

    private fun setOnBtnClickListener() {
        if(flag_first ==0){

            iv_main_act_more_btn.setOnClickListener {
                startActivity<HanokFilterActivity>()
            }

            flag_first =1
        }
    }

    fun setClassProgramRecyclerView(arrayListData: ArrayList<ProgramData>) {

        Log.e("리사이클러뷰 Data", ":::confirm"+arrayListData.size)


        programListRecyclerViewAdapter = ProgramListRecyclerViewAdapter(this, arrayListData,b)
        programListRecyclerViewAdapter.notifyDataSetChanged()
        rv_main_act_class_list.adapter = programListRecyclerViewAdapter
        rv_main_act_class_list.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

    }

    fun setTitleRecyclerView() {

        titleListData.add(
            TitleData(
                "https://res.klook.com/images/fl_lossy.progressive,q_65/c_fill,w_1295,h_720,f_auto/w_80,x_15,y_15,g_south_west,l_klook_water/activities/cpdq3jxrnhdmjvow79qs/.jpg",
                "사랑하는 사람과 함께하는 삼청동 데이트 코스",
                "북촌 8경, 감성 가득한 포토 스팟에서\n" +
                        "한옥의 빛깔로 그려진 추억 사진 남기기",
                "http://naver.me/xJvi2C9H"


            )
        )
        titleListData.add(
            TitleData(
                "https://post-phinf.pstatic.net/MjAxODA4MTNfMTU3/MDAxNTM0MTUyMDY3NzA1.NTuGE6MSj6GfMI5SmGlqH0n9OQWE_RXP_v3TZi93ovIg.aV01K90j0xnor7bnrDBJQwt0Ij_Q3eIDZNWHPs21Dd0g.JPEG/%ED%83%88%EB%B0%A9.JPG?type=w1200",
                "오래된 한국의 공방, 오래가게 8선",
                "익숙해서 눈치채지 못했던,\n" +
                        "한국의 전통 공예의 아름다움이 가득한 공방 8곳",
                "http://naver.me/xzVISbSK"
            )
        )



        titleListData.add(
            TitleData(
                "https://post-phinf.pstatic.net/MjAxNzAyMDhfMTUg/MDAxNDg2NTM1MzQ4Njcz.Gw2i1l4CHcQsb3tZlGoctLI8iofD92zlDp4_VldIEacg.VoLRH7NXhRsrHsYMYr_LO2M4tUlrrWmtNBEyaBvkNk8g.JPEG/007.jpg?type=w1200",
                "북촌 한옥 마을의 숨겨진 비밀",
                "‘건축왕’이라 불리는 그가 만들어낸\n" +
                        "경성, 북촌 한옥 마을의 뜨거운 역사 이야기\n" +
                        "- 건축왕, 경성을 만들다",
                "http://naver.me/5az4AAdB"
            )
        )
        titleListData.add(
            TitleData(
                "https://post-phinf.pstatic.net/MjAxNzA5MjBfMTAx/MDAxNTA1ODc3OTc0NDEz.Kvi6RAECepI8fweR4ddrgFEdRJzU2KC-WLmFRTmuSEEg.BkaL2u6ZTT-wn7agPveSnOYSwxodVIeKzUc_pL5PRrgg.JPEG/trd032tg13012.jpg?type=w800_q75",
                "다양한 브랜드가 공존하는, 북촌",
                "골목 곳곳에서 눈길을 사로잡는,\n" +
                        "빈티지하고 유니크한 패션샵을 소개합니다.",
                "http://naver.me/F1jurbnS"
            )
        )


        titleListData.add(
            TitleData(
                "https://post-phinf.pstatic.net/MjAxNzA5MjBfMTAx/MDAxNTA1ODc3OTc0NDEz.Kvi6RAECepI8fweR4ddrgFEdRJzU2KC-WLmFRTmuSEEg.BkaL2u6ZTT-wn7agPveSnOYSwxodVIeKzUc_pL5PRrgg.JPEG/trd032tg13012.jpg?type=w800_q75",
                "북촌 한옥 마을에서 만나는 맛집 10선",
                "구경도 하고, 배도 채우고 !\n" +
                        "입 안 가득 채워지는 우리의 맛\n",
                "http://naver.me/5l8Meuhk"
            )
        )




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
                }else
                    Log.e("mainPrograms fail", ":::test")

            }
        })
    }

    fun settingListData(mainPrograms: MainPrograms) {

        for (i in mainPrograms.hanokList.indices) {
            val programData = ProgramData(
                mainPrograms.hanokList.get(i).hanokIdx,
                mainPrograms.hanokList.get(i).thumnail,
                mainPrograms.hanokList.get(i).name
            )
            arrayListData.add(programData)
            Log.e("hanokList Data", ":::confirm")

        }

        for (i in mainPrograms.classList.indices) {
            val programData = ProgramData(
                mainPrograms.classList.get(i).classIdx,
                mainPrograms.classList.get(i).thumnail,
                mainPrograms.classList.get(i).name
            )
            arrayListClassData.add(programData)
            Log.e("classList Data", ":::confirm")
        }

        if(!isVisited){
            isVisited = !isVisited
            setClassProgramRecyclerView(arrayListData);
        }
    }

}
