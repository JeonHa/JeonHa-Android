package com.song2.jeonha.Main.Mypage

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.song2.jeonha.R
import kotlinx.android.synthetic.main.activity_mypage.*
import org.jetbrains.anko.startActivity

class MypageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mypage)
        tv_mypage_name.setOnClickListener {
            startActivity<ApplyHistoryActivity>()
        }
    }

}
