package com.example.kishanmitraapp.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Login : Screen("login")
    object Register : Screen("register")
    object Home : Screen("home")
    object Chatbot : Screen("chatbot")
    object Weather : Screen("weather")
    object Mandi : Screen("mandi")
    object Crop : Screen("crop")
    object Disease : Screen("disease")
    object Schemes : Screen("schemes")
    object Community : Screen("community")
    object Profile : Screen("profile")
}
