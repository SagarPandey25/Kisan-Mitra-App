package com.example.kishanmitraapp.data.repository

import com.example.kishanmitraapp.data.model.ApiResponse
import com.example.kishanmitraapp.data.model.MspData
import com.example.kishanmitraapp.data.remote.ApiService
import retrofit2.Response

class MspRepository(private val apiService: ApiService) {
    suspend fun getMspPrices(
        season: String? = null,
        year: String? = null,
        crop: String? = null
    ): Response<ApiResponse<MspData>> {
        return apiService.getMspPrices(season, year, crop)
    }
}
