package com.carpinteria.mitallercarpinteria.data.repositorio.interfacesRepo

import com.carpinteria.mitallercarpinteria.data.model.ClienteDTO
import com.carpinteria.mitallercarpinteria.data.model.EncargoDTO
import com.carpinteria.mitallercarpinteria.data.model.FotoEncargoDTO
import com.carpinteria.mitallercarpinteria.ui.viewModels.Uis.EstadoFormularioTrabajo

interface IRepoTaller {
    /**
     * Obtiene todos los clientes del taller de forma asíncrona mediante callbacks.
     * @param onSuccess Callback que devuelve la lista de clientes si la operación es exitosa.
     * @param onError Callback que se ejecuta si ocurre algún error en la petición.
     */
    fun obtenerClientes(
        onSuccess: (List<ClienteDTO>) -> Unit,
        onError: () -> Unit
    )

    /**
     * Obtiene todos los encargos del taller de forma asíncrona mediante callbacks.
     * @param onSuccess Callback que devuelve la lista de encargos si la operación es exitosa.
     * @param onError Callback que se ejecuta si ocurre algún error en la petición.
     */
    fun obtenerEncargos(
        onSuccess: (List<EncargoDTO>) -> Unit,
        onError: () -> Unit
    )

    fun guardarTrabajoCompleto(
        formulario: EstadoFormularioTrabajo,
        onSuccess: (EncargoDTO) -> Unit,
        onError: () -> Unit = {}
    )

    fun obtenerFotosEncargo(
        idEncargo: Long,
        onSuccess: (List<FotoEncargoDTO>) -> Unit,
        onError: () -> Unit = {}
    )

    fun guardarFotosEncargo(
        idEncargo: Long,
        rutasFotos: List<String>,
        onSuccess: () -> Unit = {},
        onError: () -> Unit = {}
    )

    fun eliminarCliente(
        idCliente: Long,
        onSuccess: () -> Unit = {},
        onError: () -> Unit = {}
    )
}