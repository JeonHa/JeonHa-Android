package com.song2.jeonha.Network.Get

import com.song2.jeonha.Map.data.MapData

class GetHanokMapResponse(
    val success : Boolean,
    val status : Int,
    val resMessage: String,
    val data : ArrayList<MapData>
    )