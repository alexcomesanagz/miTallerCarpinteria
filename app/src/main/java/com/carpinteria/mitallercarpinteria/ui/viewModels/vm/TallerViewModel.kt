package com.carpinteria.mitallercarpinteria.ui.viewModels.vm

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import com.carpinteria.mitallercarpinteria.data.repositorio.objetosRepo.TallerRepoGeneral
import com.carpinteria.mitallercarpinteria.ui.viewModels.Uis.TallerUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class TallerViewModel(application: Application) : AndroidViewModel(application) {

    private val _uiState = MutableStateFlow(TallerUiState())
    val uiState: StateFlow<TallerUiState> = _uiState.asStateFlow()

    private val context: Context?
        get() = getApplication<Application>().applicationContext

    // Repositorio general unificado (permite cambiar entre local y remoto fácilmente)
    val tallerRepo = TallerRepoGeneral.repo

    init {
        cargarDatosTaller()
    }

    /**
     * Carga los clientes y encargos del taller utilizando los repositorios y callbacks
     */
    fun cargarDatosTaller() {
        // Activa el indicador de carga en la UI
        _uiState.value = _uiState.value.copy(isLoading = true, error = null)

        // Obtiene los clientes
        tallerRepo.obtenerClientes(
            onSuccess = { listaClientes ->
                _uiState.value = _uiState.value.copy(
                    clientes = listaClientes,
                    isLoading = false
                )
            },
            onError = {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = "Error al cargar los clientes"
                )
            }
        )

        // Obtiene los encargos
        tallerRepo.obtenerEncargos(
            onSuccess = { listaEncargos ->
                _uiState.value = _uiState.value.copy(
                    encargos = listaEncargos
                )
            },
            onError = {
                // Manejo opcional de error para encargos
            }
        )
    }
}