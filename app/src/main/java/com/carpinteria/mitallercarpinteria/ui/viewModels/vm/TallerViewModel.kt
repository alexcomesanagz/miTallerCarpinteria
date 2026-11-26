package com.carpinteria.mitallercarpinteria.ui.viewModels.vm

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.carpinteria.mitallercarpinteria.data.repositorio.objetosRepo.TallerRepoGeneral
import com.carpinteria.mitallercarpinteria.ui.componentes.TipoAvisoEncargo
import com.carpinteria.mitallercarpinteria.ui.viewModels.Uis.TallerUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class TallerViewModel(application: Application) : AndroidViewModel(application) {

    private val _uiState = MutableStateFlow(TallerUiState())
    val uiState: StateFlow<TallerUiState> = _uiState.asStateFlow()

    private val context: Context?
        get() = getApplication<Application>().applicationContext

    val tallerRepo = TallerRepoGeneral.repo

    init {
        cargarDatosTaller()
    }

    fun cargarDatosTaller() {
        _uiState.update { it.copy(isLoading = true, error = null) }

        tallerRepo.obtenerClientes(
            onSuccess = { listaClientes ->
                _uiState.update {
                    it.copy(
                        clientes = listaClientes,
                        isLoading = false
                    )
                }
            },
            onError = {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = "Error al cargar los clientes"
                    )
                }
            }
        )

        tallerRepo.obtenerEncargos(
            onSuccess = { listaEncargos ->
                _uiState.update {
                    it.copy(encargos = listaEncargos)
                }
            },
            onError = {}
        )
    }

    fun mostrarAvisoEncargo(tipo: TipoAvisoEncargo, detalle: String? = null) {
        _uiState.update { current ->
            current.copy(avisoEncargo = tipo to (detalle ?: ""))
        }
    }

    fun limpiarAvisoEncargo() {
        _uiState.update { current ->
            if (current.avisoEncargo == null) current
            else current.copy(avisoEncargo = null)
        }
    }

    fun notificarGuardadoEncargo(esEdicion: Boolean, detalle: String?) {
        val tipo = if (esEdicion) TipoAvisoEncargo.ACTUALIZADO else TipoAvisoEncargo.CREADO
        val nuevoAviso = tipo to (detalle ?: "")
        Log.d("AVISO_DEBUG", "notificarGuardadoEncargo -> esEdicion=$esEdicion, detalle=$detalle")

        _uiState.update { current ->
            if (current.avisoEncargo == nuevoAviso) current
            else current.copy(avisoEncargo = nuevoAviso)
        }
        cargarDatosTaller()
    }
}