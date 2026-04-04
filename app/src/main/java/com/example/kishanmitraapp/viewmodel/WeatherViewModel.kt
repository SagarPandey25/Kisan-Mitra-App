package com.example.kishanmitraapp.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kishanmitraapp.data.model.Weather
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class WeatherViewModel : ViewModel() {
    
    var weatherState by mutableStateOf(Weather("32°C", "45%"))
        private set

    var isRefreshing by mutableStateOf(false)
        private set

    private var lastLocation = ""

    fun updateWeather(location: String) {
        lastLocation = location
        weatherState = getWeatherData(location)
    }

    fun refreshWeather() {
        viewModelScope.launch {
            isRefreshing = true
            // Simulate network delay
            delay(1500)
            weatherState = getWeatherData(lastLocation)
            isRefreshing = false
        }
    }

    private fun getWeatherData(location: String): Weather {
        return when {
            location.contains("Pune", ignoreCase = true) -> Weather("28°C", "60%")
            location.contains("Mumbai", ignoreCase = true) -> Weather("30°C", "85%")
            location.contains("Delhi", ignoreCase = true) -> Weather("38°C", "20%")
            location.contains("Bangalore", ignoreCase = true) -> Weather("24°C", "55%")
            else -> Weather("32°C", "45%")
        }
    }
}
