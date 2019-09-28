package com.song2.jeonha.Login

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import com.song2.jeonha.Main.MainActivity
import com.song2.jeonha.Network.ApplicationController
import com.song2.jeonha.Network.NetworkService
import com.song2.jeonha.Network.Post.PostUserLogin
import com.song2.jeonha.Network.Post.Response.PostUserLoginResponse
import com.song2.jeonha.R
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        setOnClickListener()
        setTextChangedListenerEmail()
        setTextChangedListenerPassword()


        et_ac_login_id.setOnFocusChangeListener { v, hasFocus ->
            if(hasFocus) v_login_id.setBackgroundColor(Color.parseColor("#f3505a"))
            else v_login_id.setBackgroundColor(Color.parseColor("#2e394a"))
        }
        et_ac_login_pw.setOnFocusChangeListener { v, hasFocus ->

            if(hasFocus) v_login_pw.setBackgroundColor(Color.parseColor("#f3505a"))
            else v_login_pw.setBackgroundColor(Color.parseColor("#2e394a"))
        }


    }

    private fun setTextChangedListenerPassword() {

        et_ac_login_pw.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s!!.length != 0) {
                    v_login_pw.setBackgroundColor(Color.parseColor("#f3505a"))

                } else {
                    v_login_id.setBackgroundColor(Color.parseColor("#2e394a"))
                }
            }
        })

    }

    private fun setTextChangedListenerEmail() {
        et_ac_login_id.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s!!.length != 0) {
                    v_login_pw.setBackgroundColor(Color.parseColor("#f3505a"))
                } else {
                    v_login_id.setBackgroundColor(Color.parseColor("#2e394a"))
                }
            }
        })
    }

    private fun setOnClickListener() {

        btn_ac_login_signin.setOnClickListener {
            PostLoginResponse()
        }

        btn_ac_login_signup.setOnClickListener {
            startActivity<SignUpActivity>()
        }


    }




    private fun PostLoginResponse() {


        if (et_ac_login_id.text.toString().isNotEmpty() && et_ac_login_pw.text.toString().isNotEmpty()) {

            val input_login_id: String = et_ac_login_id.text.toString()
            val input_login_pw: String = et_ac_login_pw.text.toString()


            v_login_id.setBackgroundColor(Color.parseColor("#f3505a"))
            v_login_pw.setBackgroundColor(Color.parseColor("#f3505a"))


            val postLoginResponse: Call<PostUserLoginResponse> = networkService.postUserLogin(PostUserLogin(input_login_id, input_login_pw))
            postLoginResponse.enqueue(object : Callback<PostUserLoginResponse> {
                override fun onFailure(call: Call<PostUserLoginResponse>, t: Throwable) {
                    Log.e("Sign In Fail", t.toString())

                }

                //통신 성공 시 수행되는 메소드
                override fun onResponse(call: Call<PostUserLoginResponse>, response: Response<PostUserLoginResponse>) {
                    if (response.isSuccessful) {


                        response.body()!!.status
                        startActivity<MainActivity>()


                    } else {
                        var resMessage: String = response.message()
                        var test = response.code()
                        Log.e("login error",test.toString()+"로그인 에러"+resMessage)

                    }
                }
            })
        }

        else {
            toast("빈칸을 다 채워주세요")
        }
    }
}
