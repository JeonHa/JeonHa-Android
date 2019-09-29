package com.song2.jeonha.Class.data

import com.song2.jeonha.Hanok.data.PhotoItem

data class ClassDetailedData(
    val classIdx: Int,
    val name: String,
    val address: String,
    val detail: String,
    val transport: String,
    val img: ArrayList<ImgData>,
    val schedule: ArrayList<ScheduleData>
)