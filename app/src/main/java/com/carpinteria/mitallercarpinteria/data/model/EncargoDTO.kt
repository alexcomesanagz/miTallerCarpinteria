package com.carpinteria.mitallercarpinteria.data.model

data class EncargoDTO(
    val id: Long,
    val titulo: String,
    val estado: String,
    val mediciones: String?,
    val materiales: String?,
    val fechaCreacion: String?,
    val fechaEntregaEstimada: String?,
    val clienteId: Long?,
    val nombreCliente: String?
)