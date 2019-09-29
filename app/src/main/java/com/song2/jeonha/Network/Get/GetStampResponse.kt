package com.song2.jeonha.Network.Get

import com.song2.jeonha.NetworkDataClass.StampAndNameData

class GetStampResponse(
    val success : Boolean,
    val status : Int,
    val resMessage: String,
    val data : StampAndNameData
)