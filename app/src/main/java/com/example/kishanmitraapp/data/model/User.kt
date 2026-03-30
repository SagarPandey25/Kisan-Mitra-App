package com.example.kishanmitraapp.data.model

import com.google.gson.annotations.SerializedName

data class RegistrationRequest(
    @SerializedName("full_name") val fullName: String,
    @SerializedName("email") val email: String,
    @SerializedName("phone_number") val phoneNumber: String,
    @SerializedName("password") val password: String,
    @SerializedName("preferred_language") val preferredLanguage: String,
    @SerializedName("farm_info") val farmInfo: FarmInfo?,
    @SerializedName("preferences") val preferences: UserPreferences
)

data class LoginRequest(
    val email: String,
    val password: String
)

data class RefreshRequest(
    @SerializedName("refresh_token") val refreshToken: String
)

data class FarmInfo(
    @SerializedName("irrigation_method") val irrigationMethod: String?,
    @SerializedName("water_sources") val waterSources: List<String>?,
    @SerializedName("soil_type") val soilType: String?,
    @SerializedName("land_area_acres") val landAreaAcres: Double?,
    @SerializedName("preferred_seasons") val preferredSeasons: List<String>?
)

data class UserPreferences(
    @SerializedName("water_savings_report") val waterSavingsReport: Boolean,
    @SerializedName("weather_alerts") val weatherAlerts: Boolean,
    @SerializedName("expert_consultation") val expertConsultation: Boolean
)
