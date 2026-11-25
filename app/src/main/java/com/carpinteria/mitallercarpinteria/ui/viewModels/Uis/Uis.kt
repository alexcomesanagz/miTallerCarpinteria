package com.carpinteria.mitallercarpinteria.ui.viewModels.Uis

import com.carpinteria.mitallercarpinteria.data.model.ClienteDTO
import com.carpinteria.mitallercarpinteria.data.model.EncargoDTO
import com.carpinteria.mitallercarpinteria.ui.componentes.ModoTarjetaTrabajo

/**
 * Objeto de estado que utiliza la pantalla principal del taller
 * @param isLoading indica si se están cargando datos de la red o base de datos
 * @param clientes lista de clientes obtenidos del taller
 * @param encargos lista de encargos o proyectos activos
 * @param error mensaje de error opcional en caso de fallo
 */
data class TallerUiState(
    val isLoading: Boolean = false,
    val clientes: List<ClienteDTO> = listOf(),
    val encargos: List<EncargoDTO> = listOf(),
    val error: String? = null
)

data class TrabajosUiState(
    val isLoading: Boolean = false,
    val clientes: List<ClienteDTO> = emptyList(),
    val clientesFiltrados: List<ClienteDTO> = emptyList(),
    val encargos: List<EncargoDTO> = emptyList(),
    val modoActual: ModoTarjetaTrabajo = ModoTarjetaTrabajo.COMPLETA,
    val textoBusqueda: String = "",
    val clienteAEliminar: ClienteDTO? = null
)

data class EstadoFormularioTrabajo(
    val idCliente: Long? = null,
    val tituloProyecto: String = "",
    val nombreCliente: String = "",
    val telefonoCliente: String = "",
    val estadoTrabajo: String = "En curso",
    val fechaEntregaEstimada: String = "",
    val fechaCreacion: String = "",
    val mediciones: String = "",
    val materialesTexto: String = "",
    val rutasImagenes: List<String> = emptyList()
) {
    companion object {
        fun desdeCliente(cliente: ClienteDTO?): EstadoFormularioTrabajo {
            if (cliente == null) return EstadoFormularioTrabajo()
            return EstadoFormularioTrabajo(
                idCliente = cliente.id,
                tituloProyecto = cliente.titulosEncargos?.firstOrNull().orEmpty(),
                nombreCliente = cliente.nombre.orEmpty(),
                telefonoCliente = cliente.telefono.orEmpty()
            )
        }
    }
}

data class DetalleTrabajoUiState(
    val cliente: ClienteDTO? = null,
    val tituloProyecto: String = "",
    val estadoTrabajo: String = "En curso",
    val fechaCreacion: String = "",
    val fechaEntrega: String = "",
    val mediciones: List<String> = emptyList(),
    val materiales: List<String> = emptyList(),
    val rutasImagenes: List<String> = emptyList(),
    val isLoading: Boolean = false
)

data class WizardTrabajoUiState(
    val pasoActual: Int = 1,
    val esEdicion: Boolean = false,
    val formulario: EstadoFormularioTrabajo = EstadoFormularioTrabajo(),
    val errorTitulo: String? = null,
    val errorCliente: String? = null,
    val errorTelefono: String? = null,
    val guardando: Boolean = false
)
