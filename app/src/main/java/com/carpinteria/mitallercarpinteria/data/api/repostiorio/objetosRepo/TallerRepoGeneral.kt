package com.carpinteria.mitallercarpinteria.data.repositorio.objetosRepo

import com.carpinteria.mitallercarpinteria.data.api.RetrofitClient
import com.carpinteria.mitallercarpinteria.data.repositorio.reposLocal.TallerRepoLocal
import com.carpinteria.mitallercarpinteria.data.repositorio.reposRetrofit.TallerRepoRemoto

object TallerRepoGeneral {
    // Usa temporalmente el repositorio local para pruebas offline
    val repo = TallerRepoLocal()

    // Expone el repositorio remoto para ser consumida en los ViewModels
    //val repo = TallerRepoRemoto(RetrofitClient.apiService)
}