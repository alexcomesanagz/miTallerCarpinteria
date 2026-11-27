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
    private var actividadExterna: Map<Long, Long> = emptyMap()

    init {
        cargarClientes()
    }

    fun aplicarOrdenActividad(mapaActividad: Map<Long, Long>) {
        actividadExterna = mapaActividad
        _uiState.update { current ->
            val clientesOrdenados = ordenarClientesPorActividad(current.clientes, actividadExterna)
            current.copy(
                clientes = clientesOrdenados,
                clientesFiltrados = filtrarClientes(
                    clientes = clientesOrdenados,
                    encargos = current.encargos,
                    texto = current.textoBusqueda
                )
            )
        }
    }

    fun registrarActividadCliente(idCliente: Long?) {
        if (idCliente == null) return
        val siguientePuntaje = (actividadExterna.values.maxOrNull() ?: 0L) + 1L
        aplicarOrdenActividad(actividadExterna + (idCliente to siguientePuntaje))
    }

    fun cargarClientes() {
        _uiState.update { it.copy(isLoading = true) }
        repositorio.obtenerClientes(
            onSuccess = { lista ->
                val ordenada = ordenarClientesPorActividad(lista, actividadExterna)
                _uiState.update { current ->
                    current.copy(
                        clientes = ordenada,
                        clientesFiltrados = filtrarClientes(
                            clientes = ordenada,
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

    fun establecerModo(modo: ModoTarjetaTrabajo) {
        _uiState.update { it.copy(modoActual = modo) }
    }

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

    private fun ordenarClientesPorActividad(
        clientes: List<ClienteDTO>,
        mapaActividad: Map<Long, Long>
    ): List<ClienteDTO> {
        if (mapaActividad.isEmpty()) return clientes
        return clientes.mapIndexed { indiceOriginal, cliente ->
            val puntaje = mapaActividad[cliente.id] ?: 0L
            Triple(cliente, puntaje, indiceOriginal)
        }.sortedWith(
            compareByDescending<Triple<ClienteDTO, Long, Int>> { it.second }
                .thenBy { it.third }
        ).map { it.first }
    }

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
                    actividadExterna = actividadExterna - idCliente
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