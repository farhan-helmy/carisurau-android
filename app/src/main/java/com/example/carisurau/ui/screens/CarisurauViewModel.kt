package com.example.carisurau.ui.screens

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.carisurau.CariSurauApplication
import com.example.carisurau.data.CarisurauRepository
import com.example.carisurau.model.Surau
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

/**
 * UI state for the Home screen
 */
sealed interface CarisurauUiState {
    data class Success(val suraus: List<Surau>) : CarisurauUiState
    object Error : CarisurauUiState
    object Loading : CarisurauUiState
}

class CarisurauViewModel(private val carisurauRepository: CarisurauRepository): ViewModel() {
    var carisurauUiState: CarisurauUiState by mutableStateOf(CarisurauUiState.Loading)
        private set

    init {
        getSuraus()
    }

    fun getSuraus() {
        viewModelScope.launch {

            carisurauUiState = CarisurauUiState.Loading
            carisurauUiState = try {
                CarisurauUiState.Success(carisurauRepository.getSuraus())
            } catch (e: IOException) {
                Log.d("ioexception", "${e}")
                CarisurauUiState.Error
            } catch (e: HttpException) {
                Log.d("http", "${e}")
                CarisurauUiState.Error
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as CariSurauApplication)
                val carisurauRepository = application.container.carisurauRepository
                CarisurauViewModel(carisurauRepository = carisurauRepository)
            }
        }
    }
}