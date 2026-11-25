package com.carpinteria.mitallercarpinteria.data.repositorio.reposLocal

import com.carpinteria.mitallercarpinteria.data.model.ClienteDTO
import com.carpinteria.mitallercarpinteria.data.model.EncargoDTO
import com.carpinteria.mitallercarpinteria.data.model.FotoEncargoDTO
import com.carpinteria.mitallercarpinteria.data.repositorio.interfacesRepo.IRepoTaller
import com.carpinteria.mitallercarpinteria.ui.viewModels.Uis.EstadoFormularioTrabajo

class TallerRepoLocal : IRepoTaller {
    private val listaClientesFalsos = mutableListOf(
        ClienteDTO(id = 1L, nombre = "Carmen Ruiz", telefono = "600111222", titulosEncargos = listOf("Armario de roble")),
        ClienteDTO(id = 2L, nombre = "Manuel Ortega", telefono = "600333444", titulosEncargos = listOf("Mesa de comedor")),
        ClienteDTO(id = 3L, nombre = "Pilar Gómez", telefono = "600555666", titulosEncargos = listOf("Puerta de entrada"))
    )

    private val listaEncargosFalsos = mutableListOf(
        EncargoDTO(
            id = 1L,
            titulo = "Armario de roble",
            estado = "En curso",
            mediciones = null,
            materiales = "Roble",
            fechaCreacion = "2026-01-01",
            fechaEntregaEstimada = "2026-02-01",
            clienteId = 1L,
            nombreCliente = "Carmen Ruiz"
        )
    )

    private val fotosPorEncargo = mutableMapOf<Long, MutableList<FotoEncargoDTO>>(
        1L to mutableListOf(
            FotoEncargoDTO(
                id = 1L,
                idEncargo = 1L,
                rutaFoto = "https://images.unsplash.com/photo-1659930087003-2d64e33181f7?auto=format&fit=crop&w=900&q=82"
            )
        )
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

    override fun obtenerFotosEncargo(
        idEncargo: Long,
        onSuccess: (List<FotoEncargoDTO>) -> Unit,
        onError: () -> Unit
    ) {
        onSuccess(fotosPorEncargo[idEncargo].orEmpty())
    }

    override fun guardarTrabajoCompleto(
        formulario: EstadoFormularioTrabajo,
        onSuccess: (EncargoDTO) -> Unit,
        onError: () -> Unit
    ) {
        // 1. Crear o actualizar Cliente
        val idCliente = formulario.idCliente ?: ((listaClientesFalsos.maxOfOrNull { it.id } ?: 0L) + 1L)
        val indiceCliente = listaClientesFalsos.indexOfFirst { it.id == idCliente }

        if (indiceCliente >= 0) {
            val existente = listaClientesFalsos[indiceCliente]
            listaClientesFalsos[indiceCliente] = existente.copy(
                nombre = formulario.nombreCliente,
                telefono = formulario.telefonoCliente,
                titulosEncargos = listOf(formulario.tituloProyecto)
            )
        } else {
            listaClientesFalsos.add(
                ClienteDTO(
                    id = idCliente,
                    nombre = formulario.nombreCliente,
                    telefono = formulario.telefonoCliente,
                    titulosEncargos = listOf(formulario.tituloProyecto)
                )
            )
        }

        // 2. Crear o actualizar Encargo
        val indiceEncargo = listaEncargosFalsos.indexOfFirst { it.clienteId == idCliente }
        val idEncargo = if (indiceEncargo >= 0) {
            listaEncargosFalsos[indiceEncargo].id
        } else {
            (listaEncargosFalsos.maxOfOrNull { it.id } ?: 0L) + 1L
        }

        val encargoGuardado = EncargoDTO(
            id = idEncargo,
            titulo = formulario.tituloProyecto,
            estado = formulario.estadoTrabajo,
            mediciones = formulario.mediciones.ifBlank { null },
            materiales = formulario.materialesTexto.ifBlank { null },
            fechaCreacion = if (indiceEncargo >= 0) listaEncargosFalsos[indiceEncargo].fechaCreacion else "2026-11-24",
            fechaEntregaEstimada = if (indiceEncargo >= 0) listaEncargosFalsos[indiceEncargo].fechaEntregaEstimada else null,
            clienteId = idCliente,
            nombreCliente = formulario.nombreCliente
        )

        if (indiceEncargo >= 0) {
            listaEncargosFalsos[indiceEncargo] = encargoGuardado
        } else {
            listaEncargosFalsos.add(encargoGuardado)
        }

        // 3. Guardar rutas de fotos asociadas al encargo
        val fotosLimpias = formulario.rutasImagenes
            .map { it.trim() }
            .filter { it.isNotEmpty() }
            .distinct()
            .mapIndexed { idx, ruta ->
                FotoEncargoDTO(
                    id = (idx + 1).toLong(),
                    idEncargo = idEncargo,
                    rutaFoto = ruta
                )
            }
        fotosPorEncargo[idEncargo] = fotosLimpias.toMutableList()

        onSuccess(encargoGuardado)
    }

    override fun guardarFotosEncargo(
        idEncargo: Long,
        rutasFotos: List<String>,
        onSuccess: () -> Unit,
        onError: () -> Unit
    ) {
        val limpias = rutasFotos
            .map { it.trim() }
            .filter { it.isNotEmpty() }
            .distinct()
            .mapIndexed { index, ruta ->
                FotoEncargoDTO(
                    id = (index + 1).toLong(),
                    idEncargo = idEncargo,
                    rutaFoto = ruta
                )
            }
        fotosPorEncargo[idEncargo] = limpias.toMutableList()
        onSuccess()
    }

    override fun eliminarCliente(
        idCliente: Long,
        onSuccess: () -> Unit,
        onError: () -> Unit
    ) {
        val idsEncargosABorrar = listaEncargosFalsos
            .filter { it.clienteId == idCliente }
            .map { it.id }

        idsEncargosABorrar.forEach { idEncargo ->
            fotosPorEncargo.remove(idEncargo)
        }

        listaEncargosFalsos.removeAll { it.clienteId == idCliente }
        val eliminado = listaClientesFalsos.removeAll { it.id == idCliente }

        if (eliminado) {
            onSuccess()
        } else {
            onError()
        }
    }
}