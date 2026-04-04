package com.example.kishanmitraapp.data.model

import com.google.gson.annotations.SerializedName

data class RegistrationRequest(
    @SerializedName("full_name") val fullName: String,
    @SerializedName("email") val email: String,
    @SerializedName("phone_number") val phoneNumber: String,
    @SerializedName("password") val password: String,
    @SerializedName("preferred_language") val preferredLanguage: String,
    @SerializedName("farm_info") val farmInfo: FarmInfo? = null,
    @SerializedName("preferences") val preferences: UserPreferences? = null
)

data class LoginRequest(
    val email: String,
    val password: String
)

data class RefreshRequest(
    @SerializedName("refresh_token") val refreshToken: String
)

data class FarmInfo(
    @SerializedName("irrigation_method") val irrigationMethod: String? = null,
    @SerializedName("water_sources") val waterSources: List<String>? = null,
    @SerializedName("soil_type") val soilType: String? = null,
    @SerializedName("land_area_acres") val landAreaAcres: Double? = null,
    @SerializedName("preferred_seasons") val preferredSeasons: List<String>? = null
)

data class UserPreferences(
    @SerializedName("water_savings_report") val waterSavingsReport: Boolean,
    @SerializedName("weather_alerts") val weatherAlerts: Boolean,
    @SerializedName("expert_consultation") val expertConsultation: Boolean
)

object ApiEnums {
    object Language {
        const val ENGLISH = "English"
        const val HINDI = "Hindi"
        const val TAMIL = "Tamil"
        const val TELUGU = "Telugu"
        const val KANNADA = "Kannada"
        const val MALAYALAM = "Malayalam"
        const val MARATHI = "Marathi"
        const val PUNJABI = "Punjabi"
        const val BENGALI = "Bengali"
        const val GUJARATI = "Gujarati"
        const val ODIA = "Odia"
    }

    object IrrigationMethod {
        const val DRIP = "Drip"
        const val SPRINKLER = "Sprinkler"
        const val FLOOD_FURROW = "FloodFurrow"
        const val SURFACE = "Surface"
        const val SUB_SURFACE = "SubSurface"
        const val MANUAL = "Manual"
    }

    object WaterSource {
        const val BOREWELL = "Borewell"
        const val CANAL = "Canal"
        const val RIVER = "River"
        const val RAINWATER = "Rainwater"
        const val POND = "Pond"
        const val DUG_WELL = "DugWell"
    }

    object SoilType {
        const val ALLUVIAL = "Alluvial"
        const val BLACK = "Black"
        const val RED = "Red"
        const val LATERITE = "Laterite"
        const val MOUNTAIN = "Mountain"
        const val DESERT = "Desert"
        const val PEATY = "Peaty"
    }

    object Season {
        const val KHARIF = "Kharif"
        const val RABI = "Rabi"
        const val ZAID = "Zaid"
    }

    object UserRole {
        const val FARMER = "Farmer"
        const val ADMIN = "Admin"
    }
}
