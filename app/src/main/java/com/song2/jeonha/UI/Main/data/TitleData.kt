package com.song2.jeonha.UI.Main.data

import android.net.Uri
import retrofit2.http.Url

data class TitleData(
    var title_img: String?,
    var title_name: String?,
    var sub_contents: String?,
    var uri_address : String?
)