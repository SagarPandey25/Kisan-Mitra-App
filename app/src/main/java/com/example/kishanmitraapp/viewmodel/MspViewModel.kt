package com.example.kishanmitraapp.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.kishanmitraapp.data.model.MspData
import com.example.kishanmitraapp.data.remote.RetrofitInstance
import com.example.kishanmitraapp.data.repository.MspRepository
import kotlinx.coroutines.launch

class MspViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = MspRepository(RetrofitInstance.getApi(application))

    var mspData by mutableStateOf<MspData?>(null)
    var isLoading by mutableStateOf(false)
    var errorMsg by mutableStateOf<String?>(null)

    fun fetchMspPrices(season: String? = null, year: String? = null, crop: String? = null) {
        viewModelScope.launch {
            isLoading = true
            errorMsg = null
            try {
                val response = repository.getMspPrices(season, year, crop)
                if (response.isSuccessful && response.body()?.success == true) {
                    mspData = response.body()?.data
                } else {
                    errorMsg = response.body()?.message ?: "Failed to fetch MSP prices"
                }
            } catch (e: Exception) {
                errorMsg = e.localizedMessage ?: "An error occurred"
            } finally {
                isLoading = false
            }
        }
    }
}
