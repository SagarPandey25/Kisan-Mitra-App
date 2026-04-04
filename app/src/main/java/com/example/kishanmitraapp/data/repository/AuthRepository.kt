package com.example.kishanmitraapp.data.repository

import com.example.kishanmitraapp.data.model.*
import com.example.kishanmitraapp.data.remote.ApiService
import retrofit2.Response

class AuthRepository(private val apiService: ApiService) {

    suspend fun healthCheck(): Response<ApiResponse<Map<String, Any>>> {
        return apiService.healthCheck()
    }

    suspend fun register(request: RegistrationRequest): Response<ApiResponse<AuthResponse>> {
        return apiService.register(request)
    }

    suspend fun login(request: LoginRequest): Response<ApiResponse<AuthResponse>> {
        return apiService.login(request)
    }

    suspend fun refreshTokens(refreshToken: String): Response<ApiResponse<AuthTokens>> {
        return apiService.refreshTokens(RefreshRequest(refreshToken))
    }

    suspend fun logout(): Response<ApiResponse<Unit>> {
        return apiService.logout()
    }

    suspend fun getProfile(): Response<ApiResponse<UserProfile>> {
        return apiService.getProfile()
    }

    suspend fun updateProfile(updates: Map<String, Any?>): Response<ApiResponse<UserProfile>> {
        return apiService.updateProfile(updates)
    }
}
