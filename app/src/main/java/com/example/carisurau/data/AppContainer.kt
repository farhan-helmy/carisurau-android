package com.example.carisurau.data

import com.example.carisurau.network.CarisurauApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

/**
 * Dependency Injection container at the application level.
 */
interface AppContainer {
    val carisurauRepository: CarisurauRepository
}

/**
 * Implementation for the Dependency Injection container at the application level.
 *
 * Variables are initialized lazily and the same instance is shared across the whole app.
 */
class DefaultAppContainer : AppContainer {
    private val baseUrl = "http://192.168.1.231:8000"

    /**
     * Use the Retrofit builder to build a retrofit object using a kotlinx.serialization converter
     */
    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()

    /**
     * Retrofit service object for creating api calls
     */
    private val retrofitService: CarisurauApiService by lazy {
        retrofit.create(CarisurauApiService::class.java)
    }

    /**
     * DI implementation for Mars photos repository
     */
    override val carisurauRepository: CarisurauRepository by lazy {
        NetworkCarisurauRepository(retrofitService)
    }
}
