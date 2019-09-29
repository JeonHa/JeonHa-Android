package com.song2.jeonha.Network.Get

import com.song2.jeonha.UI.Main.Mypage.MyPageFragment.GetMyBookingList.ClassListItemData


class GetClassListResponse(
    val success: Boolean,
    val status: Int,
    val resMessage: String,
    val data: ArrayList<ClassListItemData>
)