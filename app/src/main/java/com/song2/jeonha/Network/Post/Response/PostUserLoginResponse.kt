package com.song2.jeonha.Network.Post.Response

import com.song2.jeonha.UI.Hanok.data.Authorization

class PostUserLoginResponse(
    val success : Boolean,
    val status : Int,
    val resMessage : String,
    val data: Authorization
)

