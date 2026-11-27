package com.carpinteria.mitallercarpinteria.ui.viewModels.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.carpinteria.mitallercarpinteria.data.model.ClienteDTO
import com.carpinteria.mitallercarpinteria.data.model.EncargoDTO
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
                _uiState.update { current ->
                    current.copy(
                        clientes = lista,
                        clientesFiltrados = filtrarClientes(
                            clientes = lista,
                            encargos = current.encargos,
                            texto = current.textoBusqueda
                        ),
                        isLoading = false
                    )
                }
            },
            onError = {
                _uiState.update { it.copy(isLoading = false) }
            }
        )

        repositorio.obtenerEncargos(
            onSuccess = { listaEncargos ->
                _uiState.update { current ->
                    current.copy(
                        encargos = listaEncargos,
                        clientesFiltrados = filtrarClientes(
                            clientes = current.clientes,
                            encargos = listaEncargos,
                            texto = current.textoBusqueda
                        )
                    )
                }
            },
            onError = {}
        )
    }

    // Configurar si entra en modo normal o eliminar
    fun establecerModo(modo: ModoTarjetaTrabajo) {
        _uiState.update { it.copy(modoActual = modo) }
    }

    // Filtrar la lista al escribir en el buscador
    fun onBuscarChange(texto: String) {
        _uiState.update { currentState ->
            currentState.copy(
                textoBusqueda = texto,
                clientesFiltrados = filtrarClientes(
                    clientes = currentState.clientes,
                    encargos = currentState.encargos,
                    texto = texto
                )
            )
        }
    }

    fun limpiarBusqueda() {
        onBuscarChange("")
    }

    private fun filtrarClientes(
        clientes: List<ClienteDTO>,
        encargos: List<EncargoDTO>,
        texto: String
    ): List<ClienteDTO> {
        val consulta = texto.trim()
        if (consulta.isBlank()) return clientes

        return clientes.filter { cliente ->
            val coincideNombre = cliente.nombre?.contains(consulta, ignoreCase = true) == true
            val coincideTitulo = cliente.titulosEncargos?.any {
                it.contains(consulta, ignoreCase = true)
            } == true
            val coincideEncargo = encargos.any { encargo ->
                encargo.clienteId == cliente.id && (
                        encargo.titulo.contains(consulta, ignoreCase = true) ||
                                encargo.estado.contains(consulta, ignoreCase = true)
                        )
            }
            coincideNombre || coincideTitulo || coincideEncargo
        }
    }

    // Manejar el diálogo de eliminación
    fun seleccionarClienteParaEliminar(cliente: ClienteDTO?) {
        _uiState.update { it.copy(clienteAEliminar = cliente) }
    }

    fun confirmarEliminacion(cliente: ClienteDTO) {
        val idCliente = cliente.id ?: return
        val nombreTrabajo = cliente.titulosEncargos?.firstOrNull()?.takeIf { it.isNotBlank() }
            ?: cliente.nombre
            ?: "Trabajo"

        viewModelScope.launch {
            repositorio.eliminarCliente(
                idCliente = idCliente,
                onSuccess = {
                    _uiState.update { currentState ->
                        val nuevaListaClientes = currentState.clientes.filter { it.id != idCliente }
                        val nuevaListaFiltrada = currentState.clientesFiltrados.filter { it.id != idCliente }
                        currentState.copy(
                            clientes = nuevaListaClientes,
                            clientesFiltrados = nuevaListaFiltrada,
                            clienteAEliminar = null,
                            avisoEliminacion = nombreTrabajo
                        )
                    }
                },
                onError = {
                    _uiState.update { it.copy(clienteAEliminar = null) }
                }
            )
        }
    }

    fun limpiarAvisoEliminacion() {
        _uiState.update { it.copy(avisoEliminacion = null) }
    }
}