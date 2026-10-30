package com.carpinteria.mitallercarpinteria.ui.viewModels.Uis

import com.carpinteria.mitallercarpinteria.data.model.ClienteDTO
import com.carpinteria.mitallercarpinteria.data.model.EncargoDTO

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