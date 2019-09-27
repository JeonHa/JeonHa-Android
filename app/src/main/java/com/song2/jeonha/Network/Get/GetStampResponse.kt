package com.song2.jeonha.Network.Get

import com.song2.jeonha.Map.data.MapData
import com.song2.jeonha.NetworkDataClass.StampData

class GetStampResponse(
    val success : Boolean,
    val status : Int,
    val resMessage: String,
    val data : ArrayList<StampData?>
)