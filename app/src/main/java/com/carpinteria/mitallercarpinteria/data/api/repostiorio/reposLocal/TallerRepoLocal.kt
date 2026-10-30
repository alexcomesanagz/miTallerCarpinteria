package com.carpinteria.mitallercarpinteria.data.repositorio.reposLocal

import com.carpinteria.mitallercarpinteria.data.model.ClienteDTO
import com.carpinteria.mitallercarpinteria.data.model.EncargoDTO
import com.carpinteria.mitallercarpinteria.data.repositorio.interfacesRepo.IRepoTaller

class TallerRepoLocal : IRepoTaller {
    private val listaClientesFalsos = listOf(
        ClienteDTO(id = 1L, nombre = "Carmen Ruiz", telefono = "600111222", titulosEncargos = listOf("Armario de roble")),
        ClienteDTO(id = 2L, nombre = "Manuel Ortega", telefono = "600333444", titulosEncargos = listOf("Mesa de comedor")),
        ClienteDTO(id = 3L, nombre = "Pilar Gómez", telefono = "600555666", titulosEncargos = listOf("Puerta de entrada"))
    )

    private val listaEncargosFalsos = listOf(
        EncargoDTO(id = 1L, titulo = "Armario de roble", estado = "En curso", mediciones = null, materiales = "Roble", fechaCreacion = "2026-01-01", fechaEntregaEstimada = "2026-02-01", clienteId = 1L, nombreCliente = "Carmen Ruiz")
    )

    override fun obtenerClientes(
        onSuccess: (List<ClienteDTO>) -> Unit,
        onError: () -> Unit
    ) {
        if (listaClientesFalsos.isNotEmpty()) {
            onSuccess(listaClientesFalsos)
        } else {
            onError()
        }
    }

    override fun obtenerEncargos(
        onSuccess: (List<EncargoDTO>) -> Unit,
        onError: () -> Unit
    ) {
        if (listaEncargosFalsos.isNotEmpty()) {
            onSuccess(listaEncargosFalsos)
        } else {
            onError()
        }
    }
}