package com.song2.jeonha.UI.Login

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.song2.jeonha.UI.Main.MainActivity
import com.song2.jeonha.Network.ApplicationController
import com.song2.jeonha.Network.NetworkService
import com.song2.jeonha.Network.Post.PostUserSignUp
import com.song2.jeonha.Network.Post.Response.PostUserSignUpResponse
import com.song2.jeonha.R
import kotlinx.android.synthetic.main.activity_sign_up.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpActivity : AppCompatActivity() {


    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)


        setOnClickListener()
    }

    private fun setOnClickListener() {
        btn_ac_signup_ok.setOnClickListener {

          PostUserSignUpResponse()

      }




    }



    private fun PostUserSignUpResponse() {

        //edittext에 있는 값 받기


        if (et_ac_sign_up_id.text.toString().isNotEmpty() && et_ac_sign_up_pw.text.toString().isNotEmpty()
            && et_ac_sign_up_pw_check.text.toString().isNotEmpty() && et_ac_sign_up_name.text.isNotEmpty() && et_ac_sign_up_phone.text.isNotEmpty()
        ) {

            val input_id: String = et_ac_sign_up_id.text.toString()
            val input_pw: String = et_ac_sign_up_pw.text.toString()
            val input_name: String = et_ac_sign_up_name.text.toString()
            val input_phone: String = et_ac_sign_up_phone.text.toString()






            //통신 시작
            val postSignUpResponse: Call<PostUserSignUpResponse> =
                networkService.postUserSignUp(PostUserSignUp(input_id, input_pw,input_name,input_phone))
            postSignUpResponse.enqueue(object : Callback<PostUserSignUpResponse> {
                override fun onFailure(call: Call<PostUserSignUpResponse>, t: Throwable) {
                    Log.e("Sign Up Fail", t.toString())

                }


                // 세모입 다예 화이팅 !! ❤️

                //통신 성공 시 수행되는 메소드
                override fun onResponse(
                    call: Call<PostUserSignUpResponse>,
                    response: Response<PostUserSignUpResponse>
                ) {
                    if (response.isSuccessful) {

                        startActivity<MainActivity>()
                        finish()


                    } else {
                        var message: String = response.body()!!.resMessage
                        Log.e("signup error","회원가입 에러"+message)
                    }
                }
            })
        }
        else{
            toast("빈칸을 다 채워주세요 :)")
        }

    }


}
