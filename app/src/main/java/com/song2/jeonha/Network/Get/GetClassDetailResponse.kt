package com.song2.jeonha.Network.Get

import com.song2.jeonha.Class.data.ClassDetailedData

data class GetClassDetailResponse (
    val success: Boolean,
    val status: Int,
    val resMessage: String,
    val data: ClassDetailedData
)