package com.example.kishanmitraapp.data.repository

import com.example.kishanmitraapp.data.model.ApiResponse
import com.example.kishanmitraapp.data.model.ChatRequest
import com.example.kishanmitraapp.data.model.ChatResponse
import com.example.kishanmitraapp.data.remote.ApiService
import retrofit2.Response

class ChatbotRepository(private val apiService: ApiService) {
    suspend fun chatWithAi(message: String): Response<ApiResponse<ChatResponse>> {
        return apiService.chatWithAi(ChatRequest(message))
    }
}
