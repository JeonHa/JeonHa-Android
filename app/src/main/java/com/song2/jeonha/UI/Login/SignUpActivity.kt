package com.song2.jeonha.UI.Login

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.telephony.PhoneNumberFormattingTextWatcher
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import com.song2.jeonha.UI.Main.MainActivity
import com.song2.jeonha.Network.ApplicationController
import com.song2.jeonha.Network.Get.GetUserIdCheckResponse
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


    var cnt = 0


    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)


        setOnClickListener()

        et_ac_sign_up_phone.addTextChangedListener(PhoneNumberFormattingTextWatcher())

        //값들이 채워졌을 때
        setTextChangedListener()


    }

    private fun setOnClickListener() {
        btn_ac_signup_ok.setOnClickListener {

            GetUserIdCheck()


      }


      btn_ac_signup_checkid.setOnClickListener {

          GetUserIdCheck()

      }

    }


    private fun setTextChangedListener() {

        et_ac_sign_up_id.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                btn_ac_signup_checkid.setTextColor(Color.parseColor("#2e394a"))

                if (s!!.length != 0) {
                    v_sign_up_id_line.setBackgroundColor(Color.parseColor("#f3505a"))
                } else {
                    v_sign_up_id_line.setBackgroundColor(Color.parseColor("#2e394a"))
                }
            }
        })


        et_ac_sign_up_name.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s!!.length != 0) {
                    v_sign_up_name_line.setBackgroundColor(Color.parseColor("#f3505a"))
                    iv_ac_signup_name_check.setVisibility(View.VISIBLE)
                    cnt++
                }
                else {
                    v_sign_up_name_line.setBackgroundColor(Color.parseColor("#2e394a"))
                }
            }
        })

        et_ac_sign_up_pw.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s!!.length != 0) {
                    v_sign_up_pw_line.setBackgroundColor(Color.parseColor("#f3505a"))
                    iv_ac_signup_pw_check.setVisibility(View.VISIBLE)
                    cnt++
                }
                else {
                    v_sign_up_pw_line.setBackgroundColor(Color.parseColor("#2e394a"))
                }
            }
        })

        et_ac_sign_up_pw_check.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s!!.length != 0) {
                    v_sign_up_pw_line.setBackgroundColor(Color.parseColor("#f3505a"))
                    et_ac_sign_up_pw.text.toString() == et_ac_sign_up_pw_check.text.toString()
                    iv_ac_signup_pw_check.setVisibility(View.VISIBLE)


                    cnt++
                }
            }
        })

        et_ac_sign_up_phone.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s!!.length != 0) {
                    cnt++
                }
            }
        })
    }



    private fun GetUserIdCheck(){
        if(et_ac_sign_up_id.text.toString().isNotEmpty()){


            val id = et_ac_sign_up_id.text.toString()

            var getUserIdCheckResponse : Call<GetUserIdCheckResponse> = networkService.getUserIdCheck(id)
            getUserIdCheckResponse.enqueue(object : Callback<GetUserIdCheckResponse>{

                override fun onFailure(call: Call<GetUserIdCheckResponse>, t: Throwable) {
                    Log.d("SignUpFail",t.toString())
                }

                override fun onResponse(
                    call: Call<GetUserIdCheckResponse>,
                    response: Response<GetUserIdCheckResponse>
                ) {


                    if(response.isSuccessful){



                        if (response.body()!!.status==200){

                            toast(response.body()!!.resMessage)
                            btn_ac_signup_checkid.setVisibility(View.GONE)
                            iv_ac_signup_id_check.setVisibility(View.VISIBLE)
                            cnt++
                            PostUserSignUpResponse()


                    }
                        else {
                                toast(response.body()!!.message)

                        }


                    }

                    else
                    {

                    }

                }
            })



        }
    }

    private fun PostUserSignUpResponse() {

        //edittext에 있는 값 받기


        if (et_ac_sign_up_id.text.toString().isNotEmpty() && et_ac_sign_up_pw.text.toString().isNotEmpty()
            && et_ac_sign_up_name.text.isNotEmpty() && et_ac_sign_up_phone.text.isNotEmpty()
        ) {

            val input_id: String = et_ac_sign_up_id.text.toString()
            val input_pw: String = et_ac_sign_up_pw.text.toString()
            val input_name: String = et_ac_sign_up_name.text.toString()
            val input_phone: String = et_ac_sign_up_phone.text.toString()



            //통신 시작
            val postSignUpResponse: Call<PostUserSignUpResponse> =
                networkService.postUserSignUp(PostUserSignUp(input_id, input_pw, input_name, input_phone))
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




                        var resMessage: String = response.body()!!.resMessage
                        Log.v("signup Successful","회원가입 성공"+resMessage)
                        startActivity<MainActivity>()
                        finish()


                    } else {
                        var message: String = response.body()!!.message
                        Log.e("signup error","회원가입 에러"+message)
                    }
                }
            })
        }
        else{
            toast("빈칸을 다 채워주세요")
        }

    }

}
