package com.example.carisurau.data

import com.example.carisurau.model.Surau
import com.example.carisurau.network.CarisurauApiService

interface CarisurauRepository {
    suspend fun getSuraus(): List<Surau>
}

class NetworkCarisurauRepository(
    private val carisurauApiService: CarisurauApiService
) : CarisurauRepository {
    /** Fetches list of Surau from carisurauapi*/
    override suspend fun getSuraus(): List<Surau> = carisurauApiService.getSuraus()
}
