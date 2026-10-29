package com.carpinteria.mitallercarpinteria.ui.viewModels.vm

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.carpinteria.mitallercarpinteria.data.api.RetrofitClient
import com.carpinteria.mitallercarpinteria.data.model.ClienteDTO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TallerViewModel : ViewModel() {

    private val _clientes = MutableStateFlow<List<ClienteDTO>>(emptyList())
    val clientes: StateFlow<List<ClienteDTO>> = _clientes

    fun cargarClientes() {
        viewModelScope.launch {
            try {
                val lista = RetrofitClient.apiService.obtenerClientes()
                _clientes.value = lista
                Log.d("TALLER_APP", "Clientes cargados con éxito: ${lista.size}")
            } catch (e: Exception) {
                Log.e("TALLER_APP", "Error al conectar con el backend", e)
            }
        }
    }
}