package com.example.kishanmitraapp.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.kishanmitraapp.data.local.TokenManager
import com.example.kishanmitraapp.data.model.*
import com.example.kishanmitraapp.data.remote.RetrofitInstance
import com.example.kishanmitraapp.data.repository.AuthRepository
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.Response

class AuthViewModel(application: Application) : AndroidViewModel(application) {
    private val api = RetrofitInstance.getApi(application)
    private val repository = AuthRepository(api)
    private val tokenManager = TokenManager(application)
    private val gson = Gson()

    var isLoading by mutableStateOf(false)
    var loginResult by mutableStateOf<Result<AuthResponse>?>(null)
    var registerResult by mutableStateOf<Result<AuthResponse>?>(null)

    fun login(request: LoginRequest) {
        viewModelScope.launch {
            isLoading = true
            try {
                val response = repository.login(request)
                handleAuthResponse(response) { loginResult = it }
            } catch (e: Exception) {
                loginResult = Result.failure(e)
            } finally {
                isLoading = false
            }
        }
    }

    fun register(request: RegistrationRequest) {
        viewModelScope.launch {
            isLoading = true
            try {
                val response = repository.register(request)
                handleAuthResponse(response) { registerResult = it }
            } catch (e: Exception) {
                registerResult = Result.failure(e)
            } finally {
                isLoading = false
            }
        }
    }

    private fun handleAuthResponse(
        response: Response<ApiResponse<AuthResponse>>,
        onResult: (Result<AuthResponse>) -> Unit
    ) {
        if (response.isSuccessful && response.body()?.success == true) {
            val authData = response.body()!!.data!!
            tokenManager.saveTokens(authData.tokens.accessToken, authData.tokens.refreshToken)
            onResult(Result.success(authData))
        } else {
            val errorMsg = try {
                val errorBody = response.errorBody()?.string()
                if (errorBody.isNullOrEmpty()) {
                    "Server error: ${response.code()}"
                } else {
                    val apiResponse = gson.fromJson(errorBody, ApiResponse::class.java)
                    if (apiResponse?.error != null) {
                        val details = apiResponse.error.details?.values?.joinToString("\n") ?: ""
                        if (details.isNotEmpty()) "${apiResponse.error.message}:\n$details"
                        else apiResponse.error.message
                    } else {
                        apiResponse?.message ?: "Request failed (Code: ${response.code()})"
                    }
                }
            } catch (e: Exception) {
                "Error parsing server response: ${e.localizedMessage}"
            }
            onResult(Result.failure(Exception(errorMsg)))
        }
    }
}
