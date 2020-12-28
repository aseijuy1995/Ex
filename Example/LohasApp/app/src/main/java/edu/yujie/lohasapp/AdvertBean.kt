package edu.yujie.lohasapp

import com.google.gson.annotations.SerializedName

data class AdvertBean(
    @SerializedName("go_to") val goTo: String?,
    @SerializedName("img") var img: String?,
    @SerializedName("prod_url") val prodUrl: String?,
    @SerializedName("url_kind") val urlKind: String?
)