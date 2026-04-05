package com.example.kishanmitraapp.data.repository

import com.example.kishanmitraapp.data.model.ApiResponse
import com.example.kishanmitraapp.data.model.SchemeNewsData
import com.example.kishanmitraapp.data.model.SchemesData
import com.example.kishanmitraapp.data.remote.ApiService
import retrofit2.Response

class SchemesRepository(private val apiService: ApiService) {
    suspend fun getGovtSchemes(category: String? = null): Response<ApiResponse<SchemesData>> {
        return apiService.getGovtSchemes(category)
    }

    suspend fun getSchemeNews(): Response<ApiResponse<SchemeNewsData>> {
        return apiService.getSchemeNews()
    }
}
