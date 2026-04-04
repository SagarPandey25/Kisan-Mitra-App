package com.example.kishanmitraapp.data.remote

import com.example.kishanmitraapp.data.model.*
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @GET("health")
    suspend fun healthCheck(): Response<ApiResponse<Map<String, Any>>>

    @POST("auth/register")
    suspend fun register(@Body request: RegistrationRequest): Response<ApiResponse<AuthResponse>>

    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): Response<ApiResponse<AuthResponse>>

    @POST("auth/refresh")
    suspend fun refreshTokens(@Body request: RefreshRequest): Response<ApiResponse<AuthTokens>>

    @POST("auth/logout")
    suspend fun logout(): Response<ApiResponse<Unit>>

    @GET("auth/me")
    suspend fun getProfile(): Response<ApiResponse<UserProfile>>

    @PATCH("auth/me")
    suspend fun updateProfile(@Body request: Map<String, Any?>): Response<ApiResponse<UserProfile>>
}
