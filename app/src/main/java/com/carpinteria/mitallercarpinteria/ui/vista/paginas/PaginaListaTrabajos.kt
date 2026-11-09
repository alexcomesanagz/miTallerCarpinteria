package com.carpinteria.mitallercarpinteria.ui.vista.paginas

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.carpinteria.mitallercarpinteria.data.model.ClienteDTO
import com.carpinteria.mitallercarpinteria.ui.componentes.*
import com.carpinteria.mitallercarpinteria.ui.theme.FondoCrema
import com.carpinteria.mitallercarpinteria.ui.theme.MiTallerCarpinteriaTheme
import com.carpinteria.mitallercarpinteria.ui.viewModels.Uis.TrabajosUiState
import com.carpinteria.mitallercarpinteria.ui.viewModels.vm.TrabajosViewModel

@Composable
fun PaginaListaTrabajos(
    viewModel: TrabajosViewModel = viewModel(),
    modoInicial: ModoTarjetaTrabajo = ModoTarjetaTrabajo.COMPLETA,
    onVolverClick: () -> Unit = {},
    onClienteSeleccionado: (ClienteDTO) -> Unit = {},
    onEditarCliente: (ClienteDTO) -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    // Sincroniza el modo con el que se abrió la pantalla
    LaunchedEffect(modoInicial) {
        viewModel.establecerModo(modoInicial)
    }

    PaginaListaTrabajosContent(
        uiState = uiState,
        onVolverClick = onVolverClick,
        onBuscarChange = { texto -> viewModel.onBuscarChange(texto) },
        onClienteSeleccionado = onClienteSeleccionado,
        onEditarCliente = onEditarCliente,
        onEliminarClick = { cliente -> viewModel.seleccionarClienteParaEliminar(cliente) },
        onConfirmarEliminacion = { cliente -> viewModel.confirmarEliminacion(cliente) },
        onDescartarEliminacion = { viewModel.seleccionarClienteParaEliminar(null) }
    )
}

@Composable
fun PaginaListaTrabajosContent(
    uiState: TrabajosUiState,
    onVolverClick: () -> Unit = {},
    onBuscarChange: (String) -> Unit = {},
    onClienteSeleccionado: (ClienteDTO) -> Unit = {},
    onEditarCliente: (ClienteDTO) -> Unit = {},
    onEliminarClick: (ClienteDTO) -> Unit = {},
    onConfirmarEliminacion: (ClienteDTO) -> Unit = {},
    onDescartarEliminacion: () -> Unit = {}
) {
    val tipoAvisoActual = when (uiState.modoActual) {
        ModoTarjetaTrabajo.COMPLETA -> TipoAviso.ACCEDER
        ModoTarjetaTrabajo.SOLO_EDITAR -> TipoAviso.EDITAR
        ModoTarjetaTrabajo.SOLO_ELIMINAR -> TipoAviso.ELIMINAR
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(FondoCrema)
    ) {
        HeaderSeccion(
            titleTop = "GESTIÓN DE TRABAJOS",
            titleMain = when (uiState.modoActual) {
                ModoTarjetaTrabajo.SOLO_ELIMINAR -> "Eliminar Trabajos"
                ModoTarjetaTrabajo.SOLO_EDITAR -> "Editar Trabajos"
                ModoTarjetaTrabajo.COMPLETA -> "Lista de Trabajos"
            },
            showBackButton = true,
            onBackClick = onVolverClick
        )

        Spacer(modifier = Modifier.height(16.dp))

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            SeccionBusquedaYAviso(
                tituloBusqueda = "Buscar un trabajo",
                onBuscarChange = onBuscarChange,
                Tooltip = {
                    TarjetaAvisoInstruccion(tipo = tipoAvisoActual)
                }
            )

            ListaTarjetasTrabajo(
                clientes = uiState.clientesFiltrados,
                modo = uiState.modoActual,
                onClienteClick = onClienteSeleccionado,
                onEditarClick = onEditarCliente,
                onEliminarClick = onEliminarClick
            )
        }
    }

    // Diálogo de confirmación de borrado
    uiState.clienteAEliminar?.let { cliente ->
        DialogoConfirmarEliminar(
            tituloProyecto = cliente.titulosEncargos?.firstOrNull() ?: "Trabajo sin título",
            onConfirmar = { onConfirmarEliminacion(cliente) },
            onDescartar = onDescartarEliminacion
        )
    }
}

// PREVIEWS

@Preview(showBackground = true, backgroundColor = 0xFFFFF8E8)
@Composable
fun PaginaListaTrabajosCompletaPreview() {
    MiTallerCarpinteriaTheme {
        PaginaListaTrabajosContent(
            uiState = TrabajosUiState(
                clientesFiltrados = listOf(
                    ClienteDTO(1L, "Carmen Ruiz", "600111222", listOf("Armario de roble")),
                    ClienteDTO(2L, "Manuel Ortega", "600333444", listOf("Mesa de comedor"))
                ),
                modoActual = ModoTarjetaTrabajo.COMPLETA
            )
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFF8E8)
@Composable
fun PaginaListaTrabajosEliminarPreview() {
    MiTallerCarpinteriaTheme {
        PaginaListaTrabajosContent(
            uiState = TrabajosUiState(
                clientesFiltrados = listOf(
                    ClienteDTO(1L, "Carmen Ruiz", "600111222", listOf("Armario de roble")),
                    ClienteDTO(2L, "Manuel Ortega", "600333444", listOf("Mesa de comedor"))
                ),
                modoActual = ModoTarjetaTrabajo.SOLO_ELIMINAR
            )
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFF8E8)
@Composable
fun PaginaListaTrabajosEditarPreview() {
    MiTallerCarpinteriaTheme {
        PaginaListaTrabajosContent(
            uiState = TrabajosUiState(
                clientesFiltrados = listOf(
                    ClienteDTO(1L, "Carmen Ruiz", "600111222", listOf("Armario de roble")),
                    ClienteDTO(2L, "Manuel Ortega", "600333444", listOf("Mesa de comedor"))
                ),
                modoActual = ModoTarjetaTrabajo.SOLO_EDITAR
            )
        )
    }
}