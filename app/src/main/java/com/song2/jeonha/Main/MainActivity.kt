package com.song2.jeonha.Main

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult
import com.song2.jeonha.Main.Mypage.MypageActivity
import com.song2.jeonha.Main.QRcode.QRcodeActivity
import com.song2.jeonha.R
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        iv_main_mypage.setOnClickListener {
            startActivity<MypageActivity>()
        }


        iv_main_QRtest.setOnClickListener {
            onQrcodeScanner()
            startActivity<QRcodeActivity>()
        }

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
