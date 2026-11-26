package com.carpinteria.mitallercarpinteria.ui.vista.paginas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
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
    avisoEncargo: Pair<TipoAvisoEncargo, String>? = null,
    onLimpiarAvisoEncargo: () -> Unit = {},
    onVolverClick: () -> Unit = {},
    onClienteSeleccionado: (ClienteDTO) -> Unit = {},
    onEditarCliente: (ClienteDTO) -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(modoInicial) {
        viewModel.establecerModo(modoInicial)
    }

    PaginaListaTrabajosContent(
        uiState = uiState,
        avisoEncargo = avisoEncargo,
        onLimpiarAvisoEncargo = onLimpiarAvisoEncargo,
        onVolverClick = onVolverClick,
        onBuscarChange = { texto -> viewModel.onBuscarChange(texto) },
        onClienteSeleccionado = onClienteSeleccionado,
        onEditarCliente = onEditarCliente,
        onEliminarClick = { cliente -> viewModel.seleccionarClienteParaEliminar(cliente) },
        onConfirmarEliminacion = { cliente -> viewModel.confirmarEliminacion(cliente) },
        onDescartarEliminacion = { viewModel.seleccionarClienteParaEliminar(null) },
        onLimpiarAvisoEliminacion = viewModel::limpiarAvisoEliminacion
    )
}

@Composable
fun PaginaListaTrabajosContent(
    uiState: TrabajosUiState,
    avisoEncargo: Pair<TipoAvisoEncargo, String>? = null,
    onLimpiarAvisoEncargo: () -> Unit = {},
    onVolverClick: () -> Unit = {},
    onBuscarChange: (String) -> Unit = {},
    onClienteSeleccionado: (ClienteDTO) -> Unit = {},
    onEditarCliente: (ClienteDTO) -> Unit = {},
    onEliminarClick: (ClienteDTO) -> Unit = {},
    onConfirmarEliminacion: (ClienteDTO) -> Unit = {},
    onDescartarEliminacion: () -> Unit = {},
    onLimpiarAvisoEliminacion: () -> Unit = {}
) {
    val tipoAvisoActual = when (uiState.modoActual) {
        ModoTarjetaTrabajo.COMPLETA -> TipoAviso.ACCEDER
        ModoTarjetaTrabajo.SOLO_EDITAR -> TipoAviso.EDITAR
        ModoTarjetaTrabajo.SOLO_ELIMINAR -> TipoAviso.ELIMINAR
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(FondoCrema)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
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
                    .verticalScroll(rememberScrollState())
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
                    encargos = uiState.encargos,
                    modo = uiState.modoActual,
                    onClienteClick = onClienteSeleccionado,
                    onEditarClick = onEditarCliente,
                    onEliminarClick = onEliminarClick
                )
            }
        }

        val mostrarEliminado = uiState.avisoEliminacion != null
        val tipoAvisoFeedback = if (mostrarEliminado) {
            TipoAvisoEncargo.ELIMINADO
        } else {
            avisoEncargo?.first ?: TipoAvisoEncargo.ACTUALIZADO
        }
        val detalleAvisoFeedback = if (mostrarEliminado) {
            uiState.avisoEliminacion
        } else {
            avisoEncargo?.second?.takeIf { it.isNotBlank() }
        }

        AvisoFeedbackEncargo(
            visible = mostrarEliminado || avisoEncargo != null,
            tipo = tipoAvisoFeedback,
            detalle = detalleAvisoFeedback,
            onDismiss = {
                if (mostrarEliminado) {
                    onLimpiarAvisoEliminacion()
                } else {
                    onLimpiarAvisoEncargo()
                }
            },
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(horizontal = 16.dp, vertical = 14.dp)
        )
    }

    uiState.clienteAEliminar?.let { cliente ->
        DialogoConfirmarEliminar(
            tituloProyecto = cliente.titulosEncargos?.firstOrNull() ?: "Trabajo sin título",
            onConfirmar = { onConfirmarEliminacion(cliente) },
            onDescartar = onDescartarEliminacion
        )
    }
}

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