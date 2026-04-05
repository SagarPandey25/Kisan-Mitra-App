package com.example.kishanmitraapp.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.kishanmitraapp.data.model.SchemeNewsData
import com.example.kishanmitraapp.data.model.SchemesData
import com.example.kishanmitraapp.data.remote.RetrofitInstance
import com.example.kishanmitraapp.data.repository.SchemesRepository
import kotlinx.coroutines.launch

class SchemesViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = SchemesRepository(RetrofitInstance.getApi(application))

    var schemesData by mutableStateOf<SchemesData?>(null)
    var newsData by mutableStateOf<SchemeNewsData?>(null)
    var isLoading by mutableStateOf(false)
    var errorMsg by mutableStateOf<String?>(null)

    fun fetchSchemes(category: String? = null) {
        viewModelScope.launch {
            isLoading = true
            errorMsg = null
            try {
                val response = repository.getGovtSchemes(category)
                if (response.isSuccessful && response.body()?.success == true) {
                    schemesData = response.body()?.data
                } else {
                    errorMsg = response.body()?.message ?: "Failed to fetch schemes"
                }
            } catch (e: Exception) {
                errorMsg = e.localizedMessage ?: "An error occurred"
            } finally {
                isLoading = false
            }
        }
    }

    fun fetchNews() {
        viewModelScope.launch {
            isLoading = true
            errorMsg = null
            try {
                val response = repository.getSchemeNews()
                if (response.isSuccessful && response.body()?.success == true) {
                    newsData = response.body()?.data
                } else {
                    errorMsg = response.body()?.message ?: "Failed to fetch news"
                }
            } catch (e: Exception) {
                errorMsg = e.localizedMessage ?: "An error occurred"
            } finally {
                isLoading = false
            }
        }
    }
}
