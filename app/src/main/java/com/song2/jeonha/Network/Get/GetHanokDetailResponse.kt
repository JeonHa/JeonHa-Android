package com.song2.jeonha.Network.Get

import com.song2.jeonha.UI.Hanok.data.HanokDetailItem

class GetHanokDetailResponse(
    val success : Boolean,
    val status : Int,
    val resMessage: String,
    val data : HanokDetailItem
    )