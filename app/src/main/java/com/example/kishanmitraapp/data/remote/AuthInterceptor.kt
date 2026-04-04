package com.example.kishanmitraapp.data.remote

import android.content.Context
import com.example.kishanmitraapp.data.local.TokenManager
import com.example.kishanmitraapp.data.model.RefreshRequest
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val context: Context, private val tokenManager: TokenManager) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val accessToken = tokenManager.getAccessToken()

        val requestBuilder = originalRequest.newBuilder()
        if (accessToken != null) {
            requestBuilder.addHeader("Authorization", "Bearer $accessToken")
        }

        val response = chain.proceed(requestBuilder.build())

        if (response.code == 401) {
            synchronized(this) {
                val currentAccessToken = tokenManager.getAccessToken()
                
                // If token was already refreshed by another thread
                if (currentAccessToken != accessToken && currentAccessToken != null) {
                    return chain.proceed(originalRequest.newBuilder()
                        .header("Authorization", "Bearer $currentAccessToken")
                        .build())
                }

                val refreshToken = tokenManager.getRefreshToken()
                if (refreshToken != null) {
                    // Use getApi(context) to get the instance
                    val refreshResponse = runBlocking {
                        RetrofitInstance.getApi(context).refreshTokens(RefreshRequest(refreshToken))
                    }

                    if (refreshResponse.isSuccessful && refreshResponse.body()?.success == true) {
                        val tokens = refreshResponse.body()!!.data!!
                        tokenManager.saveTokens(tokens.accessToken, tokens.refreshToken)
                        
                        return chain.proceed(originalRequest.newBuilder()
                            .header("Authorization", "Bearer ${tokens.accessToken}")
                            .build())
                    }
                }
            }
        }

        return response
    }
}
