package com.song2.jeonha.Network

import com.google.gson.JsonObject
import com.song2.jeonha.Main.Mypage.MyPageFragment.GetMyBookingList.GetBookingClassListResponse
import com.song2.jeonha.Main.Mypage.MyPageFragment.GetMyBookingList.GetBookingHanokListResponse
import com.song2.jeonha.Network.Get.GetHanokDetailResponse
import com.song2.jeonha.Network.Get.GetHanokListResponse
import com.song2.jeonha.Network.Get.GetHanokMapResponse
import com.song2.jeonha.Network.Get.GetMainResponse
import com.song2.jeonha.Network.Get.GetStampResponse
import com.song2.jeonha.Network.Post.PostUserLogin
import com.song2.jeonha.Network.Post.PostUserSignUp
import com.song2.jeonha.Network.Post.Response.PostUserLoginResponse
import com.song2.jeonha.Network.Post.Response.PostUserSignUpResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface NetworkService {
    /*    @GET("/signup/email")
        fun getEmailRedundancyResponse(
            @Query("email") email: String
        ): Call<GetEmailRedundancyResponse>*/

    /**
     * 소희
     * 맵
     */
    @GET("/hanok?type=map")
    fun getHanokMapResponse(
        @Header("Content-Type") content_type: String
    ): Call<GetHanokMapResponse>

    /**
     * 소희
     * 한옥리스트
     */
    @GET("/hanok?type=list")
    fun getHanokListResponse(
        @Header("Content-Type") content_type: String,
        @Query("sort") sort: Int
    ): Call<GetHanokListResponse>

    /**
     * 소희
     * 한옥디테일
     */
    @GET("/hanok/{hanokIdx}")
    fun getHanokDetailResponse(
        @Header("Content-Type") content_type: String,
        @Path("hanokIdx") hanokIdx: Int
    ): Call<GetHanokDetailResponse>



    //스탬프 조회
    @GET("/user/stamp")
    fun getStampResponse(
        @Header("authorization") token: String
    ): Call<GetStampResponse>

    //클래스 조회
    @GET("/user/reservation?type=class")
    fun getClassBookingListResponse(
        @Header("authorization") token: String
    ): Call<GetBookingClassListResponse>
    //한옥 스테이 조회
    @GET("/user/reservation?type=hanok")
    fun getHanokBookingListResponse(
        @Header("authorization") token: String
    ): Call<GetBookingHanokListResponse>

    /**
     * 다예
     * Sign
     */

    @POST("/user/signin")
    fun postUserLogin(
        @Body authorization: PostUserLogin
    ): Call<PostUserLoginResponse>

    @POST("/user/signup")
    fun postUserSignUp(
        @Body authorization: PostUserSignUp
    ): Call<PostUserSignUpResponse>


    //GET
    @GET("/main")
    fun getMainResponse(
        @Header("authorization") token: String
    ): Call<GetMainResponse>
/*

    @GET("/class")
    fun getaClassListResponse(
        @Header("authorization") token: String,
        @Query ("day") sort : Int
    ): Call<GetClassListResponse>
*/

}

