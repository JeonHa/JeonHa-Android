package com.song2.jeonha.Network

import com.google.gson.JsonObject
import com.song2.jeonha.Network.Get.GetHanokMapResponse
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
        @Header("Content-Type") content_type : String
    ): Call<GetHanokMapResponse>

}