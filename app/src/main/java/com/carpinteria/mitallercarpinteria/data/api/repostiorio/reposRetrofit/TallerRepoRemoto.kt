package com.carpinteria.mitallercarpinteria.data.repositorio.reposRetrofit

import com.carpinteria.mitallercarpinteria.data.api.TallerApiService
import com.carpinteria.mitallercarpinteria.data.model.ClienteDTO
import com.carpinteria.mitallercarpinteria.data.model.EncargoDTO
import com.carpinteria.mitallercarpinteria.data.repositorio.interfacesRepo.IRepoTaller
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
}