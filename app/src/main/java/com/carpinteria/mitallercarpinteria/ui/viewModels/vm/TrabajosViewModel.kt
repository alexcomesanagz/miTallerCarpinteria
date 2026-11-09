package com.carpinteria.mitallercarpinteria.ui.viewModels.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.carpinteria.mitallercarpinteria.data.model.ClienteDTO
import com.carpinteria.mitallercarpinteria.data.repositorio.objetosRepo.TallerRepoGeneral
import com.carpinteria.mitallercarpinteria.ui.componentes.ModoTarjetaTrabajo
import com.carpinteria.mitallercarpinteria.ui.viewModels.Uis.TrabajosUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TrabajosViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(TrabajosUiState())
    val uiState: StateFlow<TrabajosUiState> = _uiState.asStateFlow()

    private val repositorio = TallerRepoGeneral.repo

    init {
        cargarClientes()
    }

    // Carga los clientes directamente desde el repositorio del taller
    fun cargarClientes() {
        _uiState.update { it.copy(isLoading = true) }
        repositorio.obtenerClientes(
            onSuccess = { lista ->
                _uiState.update {
                    it.copy(
                        clientes = lista,
                        clientesFiltrados = lista,
                        isLoading = false
                    )
                }
            },
            onError = {
                _uiState.update { it.copy(isLoading = false) }
            }
        )
    }

    // Configurar si entra en modo normal o eliminar
    fun establecerModo(modo: ModoTarjetaTrabajo) {
        _uiState.update { it.copy(modoActual = modo) }
    }

    // Filtrar la lista al escribir en el buscador
    fun onBuscarChange(texto: String) {
        _uiState.update { currentState ->
            val filtrados = if (texto.isBlank()) {
                currentState.clientes
            } else {
                currentState.clientes.filter { cliente ->
                    cliente.nombre?.contains(texto, ignoreCase = true) == true ||
                            cliente.titulosEncargos?.any { it.contains(texto, ignoreCase = true) } == true
                }
            }
            currentState.copy(
                textoBusqueda = texto,
                clientesFiltrados = filtrados
            )
        }
    }

    // Manejar el diálogo de eliminación
    fun seleccionarClienteParaEliminar(cliente: ClienteDTO?) {
        _uiState.update { it.copy(clienteAEliminar = cliente) }
    }

    fun confirmarEliminacion(cliente: ClienteDTO) {
        viewModelScope.launch {
            // Aquí llamar a repositorio para borrarlo en BD (ej: repositorio.eliminarCliente(cliente.id))

            // Actualiza la lista localmente tras borrar
            _uiState.update { currentState ->
                val nuevaLista = currentState.clientes.filter { it.id != cliente.id }
                currentState.copy(
                    clientes = nuevaLista,
                    clientesFiltrados = nuevaLista,
                    clienteAEliminar = null
                )
            }
        }
    }
}