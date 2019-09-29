package com.song2.jeonha.UI.Main

import android.app.Activity
import android.content.Intent
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
import android.webkit.WebSettings
import android.webkit.WebViewClient
import android.R
import android.webkit.WebView
import android.provider.Contacts.Intents.UI

class WebViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)

        uriAddress = intent.getData("uriAddress")

        // 웹뷰 시작
        mWebView = findViewById(R.id.webView) as WebView

        mWebView.setWebViewClient(WebViewClient()) // 클릭시 새창 안뜨게
        mWebSettings = mWebView.getSettings() //세부 세팅 등록
        mWebSettings.setJavaScriptEnabled(true) // 웹페이지 자바스클비트 허용 여부
        mWebSettings.setSupportMultipleWindows(false) // 새창 띄우기 허용 여부
        mWebSettings.setJavaScriptCanOpenWindowsAutomatically(false) // 자바스크립트 새창 띄우기(멀티뷰) 허용 여부
        mWebSettings.setLoadWithOverviewMode(true) // 메타태그 허용 여부
        mWebSettings.setUseWideViewPort(true) // 화면 사이즈 맞추기 허용 여부
        mWebSettings.setSupportZoom(false) // 화면 줌 허용 여부
        mWebSettings.setBuiltInZoomControls(false) // 화면 확대 축소 허용 여부
        mWebSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN) // 컨텐츠 사이즈 맞추기
        mWebSettings.setCacheMode(WebSettings.LOAD_NO_CACHE) // 브라우저 캐시 허용 여부
        mWebSettings.setDomStorageEnabled(true) // 로컬저장소 허용 여부

        mWebView.loadUrl(uriAddress) // 웹뷰에 표시할 웹사이트 주소, 웹뷰 시작
    }

}
