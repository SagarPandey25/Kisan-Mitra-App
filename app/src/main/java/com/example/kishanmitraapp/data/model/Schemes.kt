package com.example.kishanmitraapp.data.model

import com.google.gson.annotations.SerializedName

data class Scheme(
    val id: String,
    val name: String,
    @SerializedName("short_name") val shortName: String,
    val description: String,
    val eligibility: String,
    val benefits: String,
    @SerializedName("apply_url") val applyUrl: String,
    val category: String
)

data class SchemesData(
    val total: Int,
    val schemes: List<Scheme>
)

data class NewsItem(
    val title: String,
    val url: String,
    val source: String,
    @SerializedName("published_at") val publishedAt: String,
    val description: String
)

data class SchemeNewsData(
    val total: Int,
    @SerializedName("cached_at") val cached_at: String,
    val items: List<NewsItem>
)
