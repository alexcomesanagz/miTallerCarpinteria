package com.carpinteria.mitallercarpinteria.data.repositorio.interfacesRepo

import com.carpinteria.mitallercarpinteria.data.model.ClienteDTO
import com.carpinteria.mitallercarpinteria.data.model.EncargoDTO

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
}