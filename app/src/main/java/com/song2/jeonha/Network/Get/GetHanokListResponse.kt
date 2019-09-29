package com.song2.jeonha.Network.Get

import com.song2.jeonha.UI.Hanok.data.HanokItem

class GetHanokListResponse(
    val success : Boolean,
    val status : Int,
    val resMessage: String,
    val data : ArrayList<HanokItem>
    )