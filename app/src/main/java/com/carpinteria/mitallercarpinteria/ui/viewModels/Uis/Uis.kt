package com.carpinteria.mitallercarpinteria.ui.viewModels.Uis

import com.carpinteria.mitallercarpinteria.data.model.ClienteDTO
import com.carpinteria.mitallercarpinteria.data.model.EncargoDTO

data class TallerUiState(
    val clientes: List<ClienteDTO> = emptyList(),
    val isLoading: Boolean = false,
    val mensajeError: String? = null
)