package com.song2.jeonha.UI.Class.data

data class ClassDetailedData(
    val classIdx: Int,
    val name: String,
    val address: String,
    val detail: String,
    val transport: String,
    val img: ArrayList<ImgData>,
    val schedule: ArrayList<ScheduleData>
)