package com.carpinteria.mitallercarpinteria.data.repositorio.reposRetrofit

import com.carpinteria.mitallercarpinteria.data.api.TallerApiService
import com.carpinteria.mitallercarpinteria.data.model.ClienteDTO
import com.carpinteria.mitallercarpinteria.data.model.EncargoDTO
import com.carpinteria.mitallercarpinteria.data.model.FotoEncargoDTO
import com.carpinteria.mitallercarpinteria.data.repositorio.interfacesRepo.IRepoTaller
import com.carpinteria.mitallercarpinteria.ui.viewModels.Uis.EstadoFormularioTrabajo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TallerRepoRemoto(private val apiService: TallerApiService) : IRepoTaller {

    override fun obtenerClientes(
        onSuccess: (List<ClienteDTO>) -> Unit,
        onError: () -> Unit
    ) {
        apiService.obtenerClientesCall().enqueue(object : Callback<List<ClienteDTO>> {
            override fun onResponse(
                call: Call<List<ClienteDTO>>,
                response: Response<List<ClienteDTO>>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    onSuccess(response.body()!!)
                } else {
                    onError()
                }
            }

            override fun onFailure(call: Call<List<ClienteDTO>>, t: Throwable) {
                onError()
            }
        })
    }

    override fun obtenerEncargos(
        onSuccess: (List<EncargoDTO>) -> Unit,
        onError: () -> Unit
    ) {
        apiService.obtenerEncargosCall().enqueue(object : Callback<List<EncargoDTO>> {
            override fun onResponse(
                call: Call<List<EncargoDTO>>,
                response: Response<List<EncargoDTO>>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    onSuccess(response.body()!!)
                } else {
                    onError()
                }
            }

            override fun onFailure(call: Call<List<EncargoDTO>>, t: Throwable) {
                onError()
            }
        })
    }

    override fun obtenerFotosEncargo(
        idEncargo: Long,
        onSuccess: (List<FotoEncargoDTO>) -> Unit,
        onError: () -> Unit
    ) {
        apiService.obtenerFotosEncargoCall(idEncargo).enqueue(object : Callback<List<FotoEncargoDTO>> {
            override fun onResponse(call: Call<List<FotoEncargoDTO>>, response: Response<List<FotoEncargoDTO>>) {
                if (response.isSuccessful && response.body() != null) onSuccess(response.body()!!)
                else onError()
            }
            override fun onFailure(call: Call<List<FotoEncargoDTO>>, t: Throwable) = onError()
        })
    }

    override fun guardarTrabajoCompleto(
        formulario: EstadoFormularioTrabajo,
        onSuccess: (EncargoDTO) -> Unit,
        onError: () -> Unit
    ) {
        val clienteNuevo = ClienteDTO(
            id = formulario.idCliente ?: 0L,
            nombre = formulario.nombreCliente,
            telefono = formulario.telefonoCliente,
            titulosEncargos = listOf(formulario.tituloProyecto)
        )

        apiService.crearClienteCall(clienteNuevo).enqueue(object : Callback<ClienteDTO> {
            override fun onResponse(call: Call<ClienteDTO>, respCliente: Response<ClienteDTO>) {
                val clienteGuardado = respCliente.body()
                if (!respCliente.isSuccessful || clienteGuardado == null) {
                    onError()
                    return
                }

                val encargoNuevo = EncargoDTO(
                    id = 0L,
                    titulo = formulario.tituloProyecto,
                    estado = formulario.estadoTrabajo,
                    mediciones = formulario.mediciones.ifBlank { null },
                    materiales = formulario.materialesTexto.ifBlank { null },
                    fechaCreacion = null,
                    fechaEntregaEstimada = null,
                    clienteId = clienteGuardado.id,
                    nombreCliente = clienteGuardado.nombre
                )

                apiService.crearEncargoCall(encargoNuevo).enqueue(object : Callback<EncargoDTO> {
                    override fun onResponse(call: Call<EncargoDTO>, respEncargo: Response<EncargoDTO>) {
                        val encargoGuardado = respEncargo.body()
                        if (!respEncargo.isSuccessful || encargoGuardado == null) {
                            onError()
                            return
                        }

                        val fotosDto = formulario.rutasImagenes
                            .map { it.trim() }
                            .filter { it.isNotEmpty() }
                            .distinct()
                            .map { ruta ->
                                FotoEncargoDTO(
                                    idEncargo = encargoGuardado.id,
                                    rutaFoto = ruta
                                )
                            }

                        if (fotosDto.isEmpty()) {
                            onSuccess(encargoGuardado)
                            return
                        }

                        apiService.guardarFotosEncargoCall(encargoGuardado.id, fotosDto)
                            .enqueue(object : Callback<List<FotoEncargoDTO>> {
                                override fun onResponse(
                                    call: Call<List<FotoEncargoDTO>>,
                                    respFotos: Response<List<FotoEncargoDTO>>
                                ) {
                                    if (respFotos.isSuccessful) onSuccess(encargoGuardado) else onError()
                                }
                                override fun onFailure(call: Call<List<FotoEncargoDTO>>, t: Throwable) = onError()
                            })
                    }
                    override fun onFailure(call: Call<EncargoDTO>, t: Throwable) = onError()
                })
            }
            override fun onFailure(call: Call<ClienteDTO>, t: Throwable) = onError()
        })
    }

    override fun guardarFotosEncargo(
        idEncargo: Long,
        rutasFotos: List<String>,
        onSuccess: () -> Unit,
        onError: () -> Unit
    ) {
        val fotosDto = rutasFotos
            .map { it.trim() }
            .filter { it.isNotEmpty() }
            .distinct()
            .map { ruta ->
                FotoEncargoDTO(
                    idEncargo = idEncargo,
                    rutaFoto = ruta
                )
            }

        if (fotosDto.isEmpty()) {
            onSuccess()
            return
        }

        apiService.guardarFotosEncargoCall(idEncargo, fotosDto)
            .enqueue(object : Callback<List<FotoEncargoDTO>> {
                override fun onResponse(
                    call: Call<List<FotoEncargoDTO>>,
                    response: Response<List<FotoEncargoDTO>>
                ) {
                    if (response.isSuccessful) {
                        onSuccess()
                    } else {
                        onError()
                    }
                }

                override fun onFailure(call: Call<List<FotoEncargoDTO>>, t: Throwable) {
                    onError()
                }
            })
    }

    override fun eliminarCliente(
        idCliente: Long,
        onSuccess: () -> Unit,
        onError: () -> Unit
    ) {
        apiService.eliminarClienteCall(idCliente)
            .enqueue(object : Callback<Unit> {
                override fun onResponse(
                    call: Call<Unit>,
                    response: Response<Unit>
                ) {
                    if (response.isSuccessful) {
                        onSuccess()
                    } else {
                        onError()
                    }
                }

                override fun onFailure(call: Call<Unit>, t: Throwable) {
                    onError()
                }
            })
    }
}