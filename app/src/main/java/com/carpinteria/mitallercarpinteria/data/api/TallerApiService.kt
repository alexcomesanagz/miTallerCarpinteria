package com.carpinteria.mitallercarpinteria.data.api

import com.carpinteria.mitallercarpinteria.data.model.ClienteDTO
import com.carpinteria.mitallercarpinteria.data.model.EncargoDTO
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface TallerApiService {

    @GET("api/clientes")
    suspend fun obtenerClientes(): List<ClienteDTO>

    @POST("api/clientes")
    suspend fun crearCliente(@Body cliente: ClienteDTO): ClienteDTO

    @GET("api/encargos")
    suspend fun obtenerEncargos(): List<EncargoDTO>

    @POST("api/encargos")
    suspend fun crearEncargo(@Body encargo: EncargoDTO): EncargoDTO
}