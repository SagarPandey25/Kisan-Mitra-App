package com.example.kishanmitraapp.data.remote

import android.content.Context
import com.example.kishanmitraapp.data.local.TokenManager
import com.example.kishanmitraapp.utils.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private var apiService: ApiService? = null

    fun getApi(context: Context): ApiService {
        if (apiService == null) {
            val tokenManager = TokenManager(context)
            val authInterceptor = AuthInterceptor(context, tokenManager)
            
            val logging = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }

            val client = OkHttpClient.Builder()
                .addInterceptor(authInterceptor)
                .addInterceptor(logging)
                .build()

            apiService = Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
                .create(ApiService::class.java)
        }
        return apiService!!
    }
}
