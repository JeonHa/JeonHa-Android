package com.song2.jeonha.Network.Get

import com.song2.jeonha.Main.Mypage.MyPageFragment.GetMyBookingList.ClassListItemData

class GetClassListResponse(
    val success: Boolean,
    val status: Long,
    val resMessage: String,
    val data: ArrayList<ClassListItemData>
)