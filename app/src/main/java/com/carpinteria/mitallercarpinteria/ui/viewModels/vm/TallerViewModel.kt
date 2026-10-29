package com.carpinteria.mitallercarpinteria.ui.viewModels.vm

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.carpinteria.mitallercarpinteria.data.api.RetrofitClient
import com.carpinteria.mitallercarpinteria.ui.viewModels.Uis.TallerUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TallerViewModel(application: Application) : AndroidViewModel(application) {

    private val _uiState = MutableStateFlow(TallerUiState())
    val uiState: StateFlow<TallerUiState> = _uiState.asStateFlow()

    private val context: Context?
        get() = getApplication<Application>().applicationContext

    fun cargarClientes() {
        viewModelScope.launch {
            try {
                // Opcional: _uiState.value = _uiState.value.copy(isLoading = true)
                val lista = RetrofitClient.apiService.obtenerClientes()

                _uiState.value = _uiState.value.copy(
                    clientes = lista,
                    isLoading = false
                )

                Log.d("TALLER_APP", "Clientes cargados con éxito: ${lista.size}")
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    mensajeError = e.localizedMessage
                )
                Log.e("TALLER_APP", "Error al conectar con el backend", e)
            }
        }
    }
}