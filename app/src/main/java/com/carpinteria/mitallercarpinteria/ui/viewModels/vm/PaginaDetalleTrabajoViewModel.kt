package com.carpinteria.mitallercarpinteria.ui.viewModels.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.carpinteria.mitallercarpinteria.data.model.ClienteDTO
import com.carpinteria.mitallercarpinteria.data.repositorio.objetosRepo.TallerRepoGeneral
import com.carpinteria.mitallercarpinteria.ui.viewModels.Uis.DetalleTrabajoUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DetalleTrabajoViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(DetalleTrabajoUiState())
    val uiState: StateFlow<DetalleTrabajoUiState> = _uiState.asStateFlow()

    private val repositorio = TallerRepoGeneral.repo

    fun cargarDetalle(cliente: ClienteDTO?) {
        if (cliente == null) return
        _uiState.update {
            it.copy(
                cliente = cliente,
                tituloProyecto = cliente.titulosEncargos?.firstOrNull().orEmpty(),
                isLoading = true
            )
        }

        repositorio.obtenerEncargos(
            onSuccess = { encargos ->
                val encargo = encargos.find { it.clienteId == cliente.id }
                val listaMediciones = encargo?.mediciones
                    ?.split("\n", ",")
                    ?.map { it.trim() }
                    ?.filter { it.isNotEmpty() }
                    .orEmpty()

                val listaMateriales = encargo?.materiales
                    ?.split("\n", ",")
                    ?.map { it.trim() }
                    ?.filter { it.isNotEmpty() }
                    .orEmpty()

                _uiState.update { actual ->
                    actual.copy(
                        tituloProyecto = encargo?.titulo ?: cliente.titulosEncargos?.firstOrNull().orEmpty(),
                        estadoTrabajo = encargo?.estado ?: "En curso",
                        fechaCreacion = encargo?.fechaCreacion.orEmpty(),
                        fechaEntrega = encargo?.fechaEntregaEstimada.orEmpty(),
                        mediciones = listaMediciones,
                        materiales = listaMateriales,
                        isLoading = false
                    )
                }

                if (encargo != null) {
                    repositorio.obtenerFotosEncargo(
                        idEncargo = encargo.id,
                        onSuccess = { fotos ->
                            _uiState.update { actual ->
                                actual.copy(
                                    rutasImagenes = fotos.map { it.rutaFoto }
                                )
                            }
                        }
                    )
                }
            },
            onError = {
                _uiState.update { it.copy(isLoading = false) }
            }
        )
    }

    fun cambiarEstadoTrabajo(nuevoEstado: String) {
        _uiState.update { it.copy(estadoTrabajo = nuevoEstado) }
        viewModelScope.launch {
            val actual = _uiState.value
            val cliente = actual.cliente ?: return@launch
            repositorio.obtenerEncargos(
                onSuccess = { encargos ->
                    val encargoActual = encargos.find { it.clienteId == cliente.id }
                    repositorio.guardarTrabajoCompleto(
                        formulario = com.carpinteria.mitallercarpinteria.ui.viewModels.Uis.EstadoFormularioTrabajo(
                            idCliente = cliente.id,
                            tituloProyecto = actual.tituloProyecto.ifBlank {
                                encargoActual?.titulo ?: cliente.titulosEncargos?.firstOrNull().orEmpty()
                            },
                            nombreCliente = cliente.nombre.orEmpty(),
                            telefonoCliente = cliente.telefono.orEmpty(),
                            estadoTrabajo = nuevoEstado,
                            mediciones = actual.mediciones.joinToString("\n"),
                            materialesTexto = actual.materiales.joinToString(", "),
                            rutasImagenes = actual.rutasImagenes
                        ),
                        onSuccess = {},
                        onError = {}
                    )
                },
                onError = {}
            )
        }
    }
}