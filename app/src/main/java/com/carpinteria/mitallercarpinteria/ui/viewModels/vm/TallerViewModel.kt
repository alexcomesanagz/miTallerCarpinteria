package com.carpinteria.mitallercarpinteria.ui.viewModels.vm

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.carpinteria.mitallercarpinteria.data.model.ClienteDTO
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

    private val _actividadClientes = MutableStateFlow(emptyMap<Long, Long>())
    val actividadClientes: StateFlow<Map<Long, Long>> = _actividadClientes.asStateFlow()

    private val context: Context?
        get() = getApplication<Application>().applicationContext

    val tallerRepo = TallerRepoGeneral.repo

    private var contadorActividad = 0L

    init {
        cargarDatosTaller()
    }

    fun registrarActividadCliente(idCliente: Long?) {
        if (idCliente == null) return
        contadorActividad += 1L
        val nuevoMapa = _actividadClientes.value + (idCliente to contadorActividad)
        _actividadClientes.value = nuevoMapa

        _uiState.update { current ->
            current.copy(
                clientes = ordenarClientesPorActividad(current.clientes, nuevoMapa)
            )
        }
    }

    fun cargarDatosTaller() {
        _uiState.update { it.copy(isLoading = true, error = null) }

        tallerRepo.obtenerClientes(
            onSuccess = { listaClientes ->
                val mapaActual = _actividadClientes.value
                _uiState.update {
                    it.copy(
                        clientes = ordenarClientesPorActividad(listaClientes, mapaActual),
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

    fun notificarGuardadoEncargo(
        esEdicion: Boolean,
        detalle: String?,
        idClienteAfectado: Long? = null
    ) {
        val tipo = if (esEdicion) TipoAvisoEncargo.ACTUALIZADO else TipoAvisoEncargo.CREADO
        val nuevoAviso = tipo to (detalle ?: "")
        Log.d("AVISO_DEBUG", "notificarGuardadoEncargo -> esEdicion=$esEdicion, detalle=$detalle")

        val mapaActual = if (idClienteAfectado != null) {
            contadorActividad += 1L
            (_actividadClientes.value + (idClienteAfectado to contadorActividad)).also {
                _actividadClientes.value = it
            }
        } else {
            _actividadClientes.value
        }

        _uiState.update { current ->
            val avisoActualizado = if (current.avisoEncargo == nuevoAviso) {
                current.avisoEncargo
            } else {
                nuevoAviso
            }
            current.copy(avisoEncargo = avisoActualizado)
        }

        tallerRepo.obtenerClientes(
            onSuccess = { listaClientes ->
                val idsPrevios = _uiState.value.clientes.mapNotNull { it.id }.toSet()
                val mapaFinal = if (idClienteAfectado == null) {
                    val nuevosClientes = listaClientes.filter { cliente ->
                        val id = cliente.id
                        id != null && id !in idsPrevios
                    }
                    if (nuevosClientes.isNotEmpty()) {
                        var mapaTemp = _actividadClientes.value
                        nuevosClientes.forEach { nuevo ->
                            nuevo.id?.let { idNuevo ->
                                contadorActividad += 1L
                                mapaTemp = mapaTemp + (idNuevo to contadorActividad)
                            }
                        }
                        _actividadClientes.value = mapaTemp
                        mapaTemp
                    } else {
                        mapaActual
                    }
                } else {
                    mapaActual
                }

                _uiState.update {
                    it.copy(
                        clientes = ordenarClientesPorActividad(listaClientes, mapaFinal),
                        isLoading = false
                    )
                }
            },
            onError = {
                cargarDatosTaller()
            }
        )

        tallerRepo.obtenerEncargos(
            onSuccess = { listaEncargos ->
                _uiState.update { it.copy(encargos = listaEncargos) }
            },
            onError = {}
        )
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

    fun eliminarActividadCliente(idCliente: Long?) {
        if (idCliente == null) return
        _actividadClientes.update { mapa -> mapa - idCliente }
        _uiState.update { current ->
            current.copy(
                clientes = current.clientes.filter { it.id != idCliente }
            )
        }
    }
}