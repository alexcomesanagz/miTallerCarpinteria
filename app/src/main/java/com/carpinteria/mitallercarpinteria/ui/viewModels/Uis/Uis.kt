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
    val modoActual: ModoTarjetaTrabajo = ModoTarjetaTrabajo.COMPLETA,
    val textoBusqueda: String = "",
    val clienteAEliminar: ClienteDTO? = null
)