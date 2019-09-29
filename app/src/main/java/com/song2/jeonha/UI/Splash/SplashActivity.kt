package com.song2.jeonha.Splash

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.song2.jeonha.Login.LoginActivity
import com.song2.jeonha.R
import org.jetbrains.anko.startActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val hd = Handler()
        hd.postDelayed(splashhandler(), 1500) // 3000ms=3초후에 핸들러 실행 //딜레이 3000
    }

    private inner class splashhandler : Runnable {
        override fun run() {
            startActivity<LoginActivity>()

            this@SplashActivity.finish() // 스플래쉬 페이지 액티비티 스택에서 제거
        }
    }
}
