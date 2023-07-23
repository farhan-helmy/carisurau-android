package com.example.carisurau.network

import com.example.carisurau.model.Surau
import retrofit2.http.GET

interface CarisurauApiService {
    @GET("surau")
    suspend fun getSuraus(): List<Surau>
}