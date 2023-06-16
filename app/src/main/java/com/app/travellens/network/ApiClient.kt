package com.app.travellens.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiClient {
    const val BASE_URL1 = "https://api-dot-travellensapp.et.r.appspot.com/"
    const val BASE_URL2 = "https://model-jz64a73lpq-uc.a.run.app/"

    // Create an OkHttpClient with disabled timeout
    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(0, TimeUnit.SECONDS) // Set the connect timeout to 0
        .readTimeout(0, TimeUnit.SECONDS) // Set the read timeout to 0
        .writeTimeout(0, TimeUnit.SECONDS) // Set the write timeout to 0
        .build()

    val instance1: ApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL1)
            .client(okHttpClient) // Use the custom OkHttpClient
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit.create(ApiService::class.java)
    }

    val instance2: ApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL2)
            .client(okHttpClient) // Use the custom OkHttpClient
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit.create(ApiService::class.java)
    }
}
