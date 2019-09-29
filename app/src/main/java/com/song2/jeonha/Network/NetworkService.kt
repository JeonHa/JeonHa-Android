package com.song2.jeonha.Network

import com.google.gson.JsonObject
import com.song2.jeonha.Main.Mypage.MyPageFragment.GetMyBookingList.GetBookingClassListResponse
import com.song2.jeonha.Main.Mypage.MyPageFragment.GetMyBookingList.GetBookingHanokListResponse
import com.song2.jeonha.Network.Get.GetHanokListResponse
import com.song2.jeonha.Network.Get.GetHanokMapResponse
import com.song2.jeonha.Network.Get.GetStampResponse
import com.song2.jeonha.Network.Post.PostQrcodeScanResponse
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
    @GET("/hanok?type=list&sort={sort}")
    fun getHanokListResponse(
        @Header("Content-Type") content_type: String,
        @Path("sort") sort: Int
    ): Call<GetHanokListResponse>

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
    //qr코드
    @POST("/qr")
    fun postQrcodeScanResponse(
        @Header("authorization") token: String,
        @Body() body: JsonObject
    ): Call<PostQrcodeScanResponse>
}