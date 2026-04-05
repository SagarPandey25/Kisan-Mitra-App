package com.example.kishanmitraapp.viewmodel

import android.app.Application
import android.util.Log
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
    private val TAG = "AuthViewModel"

    var isLoading by mutableStateOf(false)
    var loginResult by mutableStateOf<Result<AuthResponse>?>(null)
    var registerResult by mutableStateOf<Result<AuthResponse>?>(null)
    var userProfile by mutableStateOf<UserProfile?>(null)
    var updateResult by mutableStateOf<Result<UserProfile>?>(null)
    var errorMsg by mutableStateOf<String?>(null)

    fun login(request: LoginRequest) {
        viewModelScope.launch {
            isLoading = true
            Log.d(TAG, "Login Request: email=${request.email}")
            try {
                val response = repository.login(request)
                Log.d(TAG, "Login Response Code: ${response.code()}")
                handleAuthResponse(response) { loginResult = it }
            } catch (e: Exception) {
                Log.e(TAG, "Login Exception: ${e.localizedMessage}", e)
                loginResult = Result.failure(e)
            } finally {
                isLoading = false
            }
        }
    }

    fun register(request: RegistrationRequest) {
        viewModelScope.launch {
            isLoading = true
            Log.d(TAG, "Register Request: email=${request.email}, name=${request.fullName}")
            try {
                val response = repository.register(request)
                Log.d(TAG, "Register Response Code: ${response.code()}")
                handleAuthResponse(response) { registerResult = it }
            } catch (e: Exception) {
                Log.e(TAG, "Register Exception: ${e.localizedMessage}", e)
                registerResult = Result.failure(e)
            } finally {
                isLoading = false
            }
        }
    }

    fun getProfile() {
        viewModelScope.launch {
            isLoading = true
            Log.d(TAG, "Fetching Profile...")
            try {
                val response = repository.getProfile()
                Log.d(TAG, "Get Profile Response Code: ${response.code()}")
                if (response.isSuccessful && response.body()?.success == true) {
                    userProfile = response.body()!!.data
                    Log.d(TAG, "Profile Data: $userProfile")
                } else {
                    errorMsg = parseError(response)
                    Log.e(TAG, "Get Profile Error: $errorMsg")
                }
            } catch (e: Exception) {
                Log.e(TAG, "Get Profile Exception: ${e.localizedMessage}", e)
                errorMsg = e.localizedMessage
            } finally {
                isLoading = false
            }
        }
    }

    fun updateProfile(updates: Map<String, Any?>) {
        viewModelScope.launch {
            isLoading = true
            Log.d(TAG, "Updating Profile: $updates")
            try {
                val response = repository.updateProfile(updates)
                Log.d(TAG, "Update Profile Response Code: ${response.code()}")
                if (response.isSuccessful && response.body()?.success == true) {
                    userProfile = response.body()!!.data
                    updateResult = Result.success(userProfile!!)
                    Log.d(TAG, "Profile Updated Successfully: $userProfile")
                } else {
                    val error = parseError(response)
                    updateResult = Result.failure(Exception(error))
                    Log.e(TAG, "Update Profile Error: $error")
                }
            } catch (e: Exception) {
                Log.e(TAG, "Update Profile Exception: ${e.localizedMessage}", e)
                updateResult = Result.failure(e)
            } finally {
                isLoading = false
            }
        }
    }

    fun logout(onComplete: () -> Unit) {
        viewModelScope.launch {
            isLoading = true
            Log.d(TAG, "Logging out...")
            try {
                val response = repository.logout()
                Log.d(TAG, "Logout Response Code: ${response.code()}")
            } catch (e: Exception) {
                Log.e(TAG, "Logout Exception: ${e.localizedMessage}", e)
            } finally {
                tokenManager.clearTokens()
                userProfile = null
                isLoading = false
                Log.d(TAG, "Tokens cleared, logout complete.")
                onComplete()
            }
        }
    }

    private fun handleAuthResponse(
        response: Response<ApiResponse<AuthResponse>>,
        onResult: (Result<AuthResponse>) -> Unit
    ) {
        if (response.isSuccessful && response.body()?.success == true) {
            val authData = response.body()!!.data!!
            Log.d(TAG, "Auth Success: $authData")
            tokenManager.saveTokens(authData.tokens.accessToken, authData.tokens.refreshToken)
            userProfile = authData.user
            onResult(Result.success(authData))
        } else {
            val error = parseError(response)
            Log.e(TAG, "Auth Failed: $error")
            onResult(Result.failure(Exception(error)))
        }
    }

    private fun <T> parseError(response: Response<ApiResponse<T>>): String {
        return try {
            val errorBody = response.errorBody()?.string()
            Log.e(TAG, "Raw Error Body: $errorBody")
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
            "Error parsing server response"
        }
    }
}
