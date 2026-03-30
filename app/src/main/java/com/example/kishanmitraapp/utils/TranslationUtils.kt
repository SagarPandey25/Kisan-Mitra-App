package com.example.kishanmitraapp.utils

import androidx.compose.runtime.*

enum class AppLanguage(val label: String) {
    ENGLISH("English"),
    HINDI("हिंदी"),
    PUNJABI("ਪੰਜਾਬੀ"),
    MARATHI("मराठी")
}

val LocalAppLanguage = compositionLocalOf { AppLanguage.ENGLISH }

object Translations {
    private val data = mapOf(
        AppLanguage.ENGLISH to mapOf(
            "welcome" to "Welcome back, Farmer!",
            "services" to "Services",
            "chatbot" to "Chatbot",
            "weather" to "Weather",
            "mandi" to "Mandi Prices",
            "crop_suggest" to "Crop Suggest",
            "disease_alert" to "Disease Alert",
            "govt_schemes" to "Govt Schemes",
            "profile" to "Profile",
            "logout" to "Logout"
        ),
        AppLanguage.HINDI to mapOf(
            "welcome" to "नमस्ते, किसान भाई!",
            "services" to "सेवाएं",
            "chatbot" to "चैटबॉट",
            "weather" to "मौसम",
            "mandi" to "मंडी भाव",
            "crop_suggest" to "फसल सुझाव",
            "disease_alert" to "रोग अलर्ट",
            "govt_schemes" to "सरकारी योजनाएं",
            "profile" to "प्रोफ़ाइल",
            "logout" to "लॉग आउट"
        ),
        AppLanguage.PUNJABI to mapOf(
            "welcome" to "ਜੀ ਆਇਆਂ ਨੂੰ, ਕਿਸਾਨ ਭਰਾਵੋ!",
            "services" to "ਸੇਵਾਵਾਂ",
            "chatbot" to "ਚੈਟਬੋਟ",
            "weather" to "ਮੌਸਮ",
            "mandi" to "ਮੰਡੀ ਦੇ ਭਾਅ",
            "crop_suggest" to "ਫਸਲ ਦਾ ਸੁਝਾਅ",
            "disease_alert" to "ਬਿਮਾਰੀ ਦੀ ਚੇਤਾਵਨੀ",
            "govt_schemes" to "ਸਰਕਾਰੀ ਸਕੀਮਾਂ",
            "profile" to "ਪ੍ਰੋਫਾਈਲ",
            "logout" to "ਲੌਗ ਆਉਟ"
        ),
        AppLanguage.MARATHI to mapOf(
            "welcome" to "स्वागत आहे, शेतकरी मित्र!",
            "services" to "सेवा",
            "chatbot" to "चॅटबॉट",
            "weather" to "हवामान",
            "mandi" to "मंडी भाव",
            "crop_suggest" to "पीक सल्ला",
            "disease_alert" to "रोग अलर्ट",
            "govt_schemes" to "सरकारी योजना",
            "profile" to "प्रोफाइल",
            "logout" to "लॉग आउट"
        )
    )

    @Composable
    fun getString(key: String): String {
        val language = LocalAppLanguage.current
        return data[language]?.get(key) ?: data[AppLanguage.ENGLISH]?.get(key) ?: key
    }
}
