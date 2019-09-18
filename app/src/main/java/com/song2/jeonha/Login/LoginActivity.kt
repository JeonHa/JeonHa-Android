package com.song2.jeonha.Login

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.song2.jeonha.Main.MainActivity
import com.song2.jeonha.R
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.startActivity

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        tv_next.setOnClickListener {
            startActivity<MainActivity>()
            finish()
        }
    }
}
