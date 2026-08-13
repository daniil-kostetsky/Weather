package com.example.weather.data.network.api

import com.example.weather.BuildConfig
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.create
import java.util.Locale

object ApiFactory {

    private const val KEY_PARAM = "key"
    private const val PARAM_LANG = "lang"
    private const val BASE_URL = "https://api.weatherapi.com/v1/"
    private val json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
        isLenient = true
    }
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val originalRequest = chain.request()
            val newUrl = originalRequest
                .url
                .newBuilder()
                .addQueryParameter(KEY_PARAM, BuildConfig.WEATHER_API_KEY)
                .addQueryParameter(PARAM_LANG, Locale.getDefault().language)
                .build()
            val newRequest = originalRequest.newBuilder()
                .url(newUrl)
                .build()
            chain.proceed(newRequest)
        }.build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .client(okHttpClient)
        .build()

    val apiService: ApiService = retrofit.create()
}