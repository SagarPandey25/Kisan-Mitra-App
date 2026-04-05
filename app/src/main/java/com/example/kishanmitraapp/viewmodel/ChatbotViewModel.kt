package com.example.kishanmitraapp.viewmodel

import android.app.Application
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.kishanmitraapp.data.model.Message
import com.example.kishanmitraapp.data.remote.RetrofitInstance
import com.example.kishanmitraapp.data.repository.ChatbotRepository
import kotlinx.coroutines.launch

class ChatbotViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = ChatbotRepository(RetrofitInstance.getApi(application))
    private val TAG = "ChatbotViewModel"

    val messages = mutableStateListOf<Message>(
        Message("Hello! I am your Kisan Sahayak. How can I help you today?", false)
    )

    var isTyping by mutableStateOf(false)
        private set

    var navigateToLogin by mutableStateOf(false)

    fun sendMessage(text: String) {
        if (text.isBlank()) return

        messages.add(Message(text, true))
        isTyping = true
        Log.d(TAG, "Sending message: $text")

        viewModelScope.launch {
            try {
                val response = repository.chatWithAi(text)
                Log.d(TAG, "API Response Code: ${response.code()}")
                
                if (response.isSuccessful && response.body()?.success == true) {
                    val body = response.body()
                    Log.d(TAG, "API Success Response Body: $body")
                    val reply = body?.data?.reply ?: "Sorry, I couldn't process that."
                    messages.add(Message(reply, false))
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e(TAG, "API Error Response Body: $errorBody")
                    
                    val errorCode = response.code()
                    if (errorCode == 401 || errorCode == 403) {
                        messages.add(Message("Your session has expired. Please log in again.", false, isError = true))
                    } else {
                        messages.add(Message("Error: ${response.message()}", false, isError = true))
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "Network Error: ${e.localizedMessage}", e)
                messages.add(Message("Network error: ${e.localizedMessage}", false, isError = true))
            } finally {
                isTyping = false
            }
        }
    }
}
