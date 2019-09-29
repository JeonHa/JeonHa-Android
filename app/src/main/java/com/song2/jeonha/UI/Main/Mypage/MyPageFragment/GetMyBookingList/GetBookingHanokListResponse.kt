package com.song2.jeonha.Main.Mypage.MyPageFragment.GetMyBookingList

data class GetBookingHanokListResponse(
    val data: HData,
    val resMessage: String,
    val status: Int,
    val success: Boolean
)