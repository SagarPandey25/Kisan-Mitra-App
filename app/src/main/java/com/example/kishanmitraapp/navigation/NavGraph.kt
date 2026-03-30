package com.example.kishanmitraapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.*
import com.example.kishanmitraapp.ui.screens.home.HomeScreen
import com.example.kishanmitraapp.ui.screens.chatbot.ChatbotScreen
import com.example.kishanmitraapp.ui.screens.weather.WeatherScreen
import com.example.kishanmitraapp.ui.screens.mandi.MandiScreen
import com.example.kishanmitraapp.ui.screens.crop.CropRecommendationScreen
import com.example.kishanmitraapp.ui.screens.disease.DiseaseDetectionScreen
import com.example.kishanmitraapp.ui.screens.schemes.GovtSchemesScreen
import com.example.kishanmitraapp.ui.screens.community.CommunityScreen
import com.example.kishanmitraapp.ui.screens.profile.ProfileScreen
import com.example.kishanmitraapp.ui.screens.splash.SplashScreen
import com.example.kishanmitraapp.ui.screens.auth.LoginScreen
import com.example.kishanmitraapp.ui.screens.auth.RegisterScreen
import com.example.kishanmitraapp.utils.AppLanguage

@Composable
fun AppNavGraph(onLanguageChange: (AppLanguage) -> Unit) {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {

        composable(Screen.Splash.route) {
            SplashScreen(onTimeout = {
                navController.navigate(Screen.Login.route) {
                    popUpTo(Screen.Splash.route) { inclusive = true }
                }
            })
        }

        composable(Screen.Login.route) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
                onRegisterClick = { navController.navigate(Screen.Register.route) }
            )
        }

        composable(Screen.Register.route) {
            RegisterScreen(
                onRegisterSuccess = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Register.route) { inclusive = true }
                    }
                },
                onLoginClick = { navController.navigate(Screen.Login.route) },
                onLanguageSelected = onLanguageChange
            )
        }

        composable(Screen.Home.route) {
            HomeScreen(
                onChatClick = { navController.navigate(Screen.Chatbot.route) },
                onWeatherClick = { navController.navigate(Screen.Weather.route) },
                onMandiClick = { navController.navigate(Screen.Mandi.route) },
                onCropClick = { navController.navigate(Screen.Crop.route) },
                onDiseaseClick = { navController.navigate(Screen.Disease.route) },
                onSchemesClick = { navController.navigate(Screen.Schemes.route) },
                onProfileClick = { navController.navigate(Screen.Profile.route) }
            )
        }

        composable(Screen.Chatbot.route) { ChatbotScreen() }
        composable(Screen.Weather.route) { 
            WeatherScreen(onBackClick = { navController.popBackStack() }) 
        }
        composable(Screen.Mandi.route) { 
            MandiScreen(onBackClick = { navController.popBackStack() }) 
        }
        composable(Screen.Crop.route) { 
            CropRecommendationScreen(onBackClick = { navController.popBackStack() }) 
        }
        composable(Screen.Disease.route) { 
            DiseaseDetectionScreen(onBackClick = { navController.popBackStack() }) 
        }
        composable(Screen.Schemes.route) { 
            GovtSchemesScreen(onBackClick = { navController.popBackStack() }) 
        }
        composable(Screen.Community.route) { CommunityScreen() }
        composable(Screen.Profile.route) { ProfileScreen() }
    }
}
