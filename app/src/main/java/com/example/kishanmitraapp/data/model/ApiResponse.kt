package com.example.kishanmitraapp.data.model

import com.google.gson.annotations.SerializedName

data class ApiResponse<T>(
    val success: Boolean,
    val message: String,
    val data: T?,
    val error: ApiError?
)

data class ApiError(
    val code: String,
    val message: String,
    val details: Map<String, String>?
)

data class UserProfile(
    val id: String,
    @SerializedName("full_name") val fullName: String,
    val email: String,
    @SerializedName("phone_number") val phoneNumber: String,
    @SerializedName("preferred_language") val preferredLanguage: String,
    @SerializedName("farm_info") val farmInfo: FarmInfo?,
    val preferences: UserPreferences,
    val role: String,
    @SerializedName("is_active") val isActive: Boolean,
    @SerializedName("is_email_verified") val isEmailVerified: Boolean,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("updated_at") val updatedAt: String
)

data class AuthTokens(
    @SerializedName("access_token") val accessToken: String,
    @SerializedName("refresh_token") val refreshToken: String,
    @SerializedName("token_type") val tokenType: String,
    @SerializedName("expires_in") val expiresIn: Int
)

data class AuthResponse(
    val user: UserProfile,
    val tokens: AuthTokens
)
