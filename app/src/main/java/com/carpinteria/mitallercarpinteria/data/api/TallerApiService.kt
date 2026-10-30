package com.carpinteria.mitallercarpinteria.data.api

import com.carpinteria.mitallercarpinteria.data.model.ClienteDTO
import com.carpinteria.mitallercarpinteria.data.model.EncargoDTO
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface TallerApiService {

    /**
     * Obtiene la lista de clientes desde el servidor remoto usando Retrofit Call.
     */
    @GET("api/clientes")
    fun obtenerClientesCall(): Call<List<ClienteDTO>>

    @POST("api/clientes")
    fun crearClienteCall(@Body cliente: ClienteDTO): Call<ClienteDTO>

    /**
     * Obtiene la lista de encargos desde el servidor remoto usando Retrofit Call.
     */
    @GET("api/encargos")
    fun obtenerEncargosCall(): Call<List<EncargoDTO>>

    @POST("api/encargos")
    fun crearEncargoCall(@Body encargo: EncargoDTO): Call<EncargoDTO>
}