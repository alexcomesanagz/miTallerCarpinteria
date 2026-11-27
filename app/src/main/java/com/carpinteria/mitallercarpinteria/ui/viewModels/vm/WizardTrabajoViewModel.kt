package com.carpinteria.mitallercarpinteria.ui.viewModels.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.carpinteria.mitallercarpinteria.data.model.ClienteDTO
import com.carpinteria.mitallercarpinteria.data.repositorio.objetosRepo.TallerRepoGeneral
import com.carpinteria.mitallercarpinteria.ui.viewModels.Uis.EstadoFormularioTrabajo
import com.carpinteria.mitallercarpinteria.ui.viewModels.Uis.WizardTrabajoUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class WizardTrabajoViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(WizardTrabajoUiState())
    val uiState: StateFlow<WizardTrabajoUiState> = _uiState.asStateFlow()

    private val repositorio = TallerRepoGeneral.repo

    private var guardadoEnCurso = false
    private var sesionInicializada = false
    private var idClienteInicializado: Long? = null

    fun inicializar(clienteInicial: ClienteDTO?, forzarReinicio: Boolean = false) {
        val nuevoId = clienteInicial?.id
        if (!forzarReinicio && sesionInicializada && idClienteInicializado == nuevoId) {
            return
        }

        sesionInicializada = true
        idClienteInicializado = nuevoId
        guardadoEnCurso = false

        _uiState.value = WizardTrabajoUiState(
            pasoActual = 1,
            esEdicion = clienteInicial != null,
            formulario = EstadoFormularioTrabajo.desdeCliente(clienteInicial)
        )

        if (clienteInicial != null) {
            repositorio.obtenerEncargos(
                onSuccess = { encargos ->
                    val encargo = encargos.find { it.clienteId == clienteInicial.id }
                    _uiState.update { actual ->
                        actual.copy(
                            formulario = actual.formulario.copy(
                                tituloProyecto = encargo?.titulo
                                    ?: clienteInicial.titulosEncargos?.firstOrNull().orEmpty(),
                                estadoTrabajo = encargo?.estado ?: "En curso",
                                fechaEntregaEstimada = encargo?.fechaEntregaEstimada.orEmpty(),
                                fechaCreacion = encargo?.fechaCreacion.orEmpty(),
                                mediciones = encargo?.mediciones.orEmpty(),
                                materialesTexto = encargo?.materiales.orEmpty()
                            )
                        )
                    }

                    if (encargo != null) {
                        repositorio.obtenerFotosEncargo(
                            idEncargo = encargo.id,
                            onSuccess = { fotos ->
                                _uiState.update { actual ->
                                    actual.copy(
                                        formulario = actual.formulario.copy(
                                            rutasImagenes = fotos.map { it.rutaFoto }
                                        )
                                    )
                                }
                            }
                        )
                    }
                },
                onError = {}
            )
        }
    }

    // --- PASO 1: CLIENTE Y DATOS PRINCIPALES ---
    fun onTituloChange(nuevoTitulo: String) {
        _uiState.update {
            it.copy(
                formulario = it.formulario.copy(tituloProyecto = nuevoTitulo),
                errorTitulo = null
            )
        }
    }

    fun onClienteChange(nuevoNombre: String) {
        _uiState.update {
            it.copy(
                formulario = it.formulario.copy(nombreCliente = nuevoNombre),
                errorCliente = null
            )
        }
    }

    fun onTelefonoChange(nuevoTelefono: String) {
        _uiState.update {
            it.copy(
                formulario = it.formulario.copy(telefonoCliente = nuevoTelefono),
                errorTelefono = null
            )
        }
    }

    fun onEstadoChange(nuevoEstado: String) {
        _uiState.update {
            it.copy(formulario = it.formulario.copy(estadoTrabajo = nuevoEstado))
        }
    }

    fun onFechaEntregaChange(nuevaFecha: String) {
        _uiState.update {
            it.copy(formulario = it.formulario.copy(fechaEntregaEstimada = nuevaFecha))
        }
    }

    // --- PASO 2: DETALLES (MEDICIONES Y MATERIALES) ---
    fun onMedicionesChange(nuevasMediciones: String) {
        _uiState.update {
            it.copy(formulario = it.formulario.copy(mediciones = nuevasMediciones))
        }
    }

    fun onMaterialesChange(nuevosMateriales: String) {
        _uiState.update {
            it.copy(formulario = it.formulario.copy(materialesTexto = nuevosMateriales))
        }
    }

    // --- PASO 3: FOTOGRAFÍAS (RUTAS) ---
    fun agregarRutasFotos(nuevasRutas: List<String>) {
        if (nuevasRutas.isEmpty()) return
        _uiState.update {
            it.copy(
                formulario = it.formulario.copy(
                    rutasImagenes = it.formulario.rutasImagenes + nuevasRutas
                )
            )
        }
    }

    fun eliminarFotoEnIndice(index: Int) {
        _uiState.update {
            it.copy(
                formulario = it.formulario.copy(
                    rutasImagenes = it.formulario.rutasImagenes.filterIndexed { i, _ -> i != index }
                )
            )
        }
    }

    // --- NAVEGACIÓN ENTRE PASOS ---
    fun retrocederPaso(onSalirWizard: () -> Unit) {
        val pasoActual = _uiState.value.pasoActual
        if (pasoActual <= 1) {
            onSalirWizard()
        } else {
            _uiState.update { it.copy(pasoActual = pasoActual - 1) }
        }
    }

    fun avanzarPasoOGuardar(onGuardadoExitoso: () -> Unit) {
        val actual = _uiState.value
        when (actual.pasoActual) {
            1 -> {
                val form = actual.formulario
                val errTitulo = if (form.tituloProyecto.isBlank()) "Escriba un título para continuar" else null
                val errCliente = if (form.nombreCliente.isBlank()) "Escriba el nombre del cliente" else null
                val errTelefono = if (form.telefonoCliente.isBlank()) "Escriba un teléfono de contacto" else null

                if (errTitulo != null || errCliente != null || errTelefono != null) {
                    _uiState.update {
                        it.copy(
                            errorTitulo = errTitulo,
                            errorCliente = errCliente,
                            errorTelefono = errTelefono
                        )
                    }
                    return
                }
                _uiState.update { it.copy(pasoActual = 2) }
            }

            2 -> _uiState.update { it.copy(pasoActual = 3) }

            3 -> guardarEnBaseDeDatos(onGuardadoExitoso)
        }
    }

    private fun guardarEnBaseDeDatos(onGuardadoExitoso: () -> Unit) {
        if (guardadoEnCurso || _uiState.value.guardando) return
        guardadoEnCurso = true

        val form = _uiState.value.formulario
        viewModelScope.launch {
            _uiState.update { it.copy(guardando = true) }
            repositorio.guardarTrabajoCompleto(
                formulario = form,
                onSuccess = {
                    _uiState.update { it.copy(guardando = false) }
                    onGuardadoExitoso()
                },
                onError = {
                    _uiState.update { it.copy(guardando = false) }
                    guardadoEnCurso = false
                }
            )
        }
    }
}