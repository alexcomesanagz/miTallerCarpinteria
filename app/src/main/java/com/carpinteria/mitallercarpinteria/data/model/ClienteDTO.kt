package com.carpinteria.mitallercarpinteria.data.model

data class ClienteDTO(
    val id: Long,
    val nombre: String,
    val telefono: String?,
    val titulosEncargos: List<String>?
)