package com.carpinteria.mitallercarpinteria.ui.vista.paginas

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.carpinteria.mitallercarpinteria.data.model.ClienteDTO
import com.carpinteria.mitallercarpinteria.ui.componentes.*
import com.carpinteria.mitallercarpinteria.ui.theme.*
import com.carpinteria.mitallercarpinteria.ui.viewModels.Uis.DetalleTrabajoUiState
import com.carpinteria.mitallercarpinteria.ui.viewModels.vm.DetalleTrabajoViewModel

@Composable
fun PaginaDetalleTrabajo(
    cliente: ClienteDTO?,
    viewModel: DetalleTrabajoViewModel = viewModel(),
    avisoEncargo: Pair<TipoAvisoEncargo, String>? = null,
    onLimpiarAvisoEncargo: () -> Unit = {},
    onVolverClick: () -> Unit = {},
    onEditarClick: (ClienteDTO) -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(cliente) {
        viewModel.cargarDetalle(cliente)
    }

    PaginaDetalleTrabajoContent(
        cliente = cliente,
        uiState = uiState,
        avisoEncargo = avisoEncargo,
        onLimpiarAvisoEncargo = onLimpiarAvisoEncargo,
        onVolverClick = onVolverClick,
        onEditarClick = onEditarClick,
        onEstadoSeleccionado = viewModel::cambiarEstadoTrabajo
    )
}

@Composable
fun PaginaDetalleTrabajoContent(
    cliente: ClienteDTO?,
    uiState: DetalleTrabajoUiState = DetalleTrabajoUiState(),
    avisoEncargo: Pair<TipoAvisoEncargo, String>? = null,
    onLimpiarAvisoEncargo: () -> Unit = {},
    onVolverClick: () -> Unit = {},
    onEditarClick: (ClienteDTO) -> Unit = {},
    onEstadoSeleccionado: (String) -> Unit = {}
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = FondoCrema,
        topBar = {
            HeaderSeccion(
                titleTop = "DATOS DEL ENCARGO",
                titleMain = uiState.tituloProyecto.ifBlank { "Ficha del trabajo" },
                showBackButton = true,
                onBackClick = onVolverClick
            )
        },
        bottomBar = {
            BarraNavegacionInferior(
                textoBotonSecundario = "Volver",
                onSecundarioClick = onVolverClick,
                textoBotonPrincipal = "Editar",
                iconoPrincipal = Icons.Default.Edit,
                onPrincipalClick = { cliente?.let { onEditarClick(it) } }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp, vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                TarjetaDatosCliente(
                    nombreCliente = cliente?.nombre ?: "Sin nombre",
                    telefono = cliente?.telefono ?: "Sin teléfono"
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    TarjetaFecha(
                        titulo = "Fecha creación",
                        dia = uiState.fechaCreacion.takeLast(2).ifBlank { "--" },
                        mesAnio = uiState.fechaCreacion.take(7).ifBlank { "Sin fecha" },
                        containerColor = YellowButtonBg,
                        borderColor = YellowButtonDark,
                        modifier = Modifier.weight(1f)
                    )
                    TarjetaFecha(
                        titulo = "Fecha de entrega",
                        dia = uiState.fechaEntrega.takeLast(2).ifBlank { "--" },
                        mesAnio = uiState.fechaEntrega.take(7).ifBlank { "Pendiente" },
                        modifier = Modifier.weight(1f)
                    )
                }

                SeccionEstadoTrabajo(
                    estadoActual = uiState.estadoTrabajo,
                    onEstadoSeleccionado = onEstadoSeleccionado
                )

                TarjetaSeccionNumerada(
                    numero = "1",
                    titulo = "MEDICIONES",
                    contenido = uiState.mediciones.ifEmpty { listOf("Sin mediciones registradas") }
                )

                TarjetaSeccionNumerada(
                    numero = "2",
                    titulo = "MATERIALES",
                    contenido = uiState.materiales.ifEmpty { listOf("Sin materiales registrados") }
                )

                SeccionFotos(
                    rutasImagenes = uiState.rutasImagenes
                )

                Spacer(modifier = Modifier.height(16.dp))
            }

            AvisoFeedbackEncargo(
                visible = avisoEncargo != null,
                tipo = avisoEncargo?.first ?: TipoAvisoEncargo.ACTUALIZADO,
                detalle = avisoEncargo?.second?.takeIf { it.isNotBlank() },
                onDismiss = onLimpiarAvisoEncargo,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(horizontal = 16.dp, vertical = 14.dp)
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFF8E8)
@Composable
fun PaginaDetalleTrabajoCompletaPreview() {
    MiTallerCarpinteriaTheme {
        PaginaDetalleTrabajoContent(
            cliente = ClienteDTO(
                id = 1L,
                nombre = "Carmen Ruiz",
                telefono = "612 345 780",
                titulosEncargos = listOf("Armario de roble")
            ),
            uiState = DetalleTrabajoUiState(
                tituloProyecto = "Armario de roble",
                estadoTrabajo = "En curso",
                mediciones = listOf("Alto: 240 cm", "Ancho: 180 cm"),
                materiales = listOf("Roble")
            )
        )
    }
}