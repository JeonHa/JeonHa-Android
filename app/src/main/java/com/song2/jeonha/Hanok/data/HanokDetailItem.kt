package com.song2.jeonha.Hanok.data

data class HanokDetailItem(
    val hanokIdx: Int,
    val name: String,
    val type: String,
    val place: String,
    val latitude: Double,
    val longitude: Double,
    val address: String,
    val detail: String,
    val option: String,
    val transport: String,
    val img: ArrayList<PhotoItem>
)