package com.example.kishanmitraapp.data.model

import com.google.gson.annotations.SerializedName

data class MspPrice(
    val crop: String,
    val season: String,
    val year: String,
    @SerializedName("msp_per_quintal") val mspPerQuintal: Double,
    val unit: String
)

data class MspData(
    val total: Int,
    val prices: List<MspPrice>
)
