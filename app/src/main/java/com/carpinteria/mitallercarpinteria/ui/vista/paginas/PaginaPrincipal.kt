package com.carpinteria.mitallercarpinteria.ui.vista.paginas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Work
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.carpinteria.mitallercarpinteria.ui.componentes.ListaTarjetasTrabajo
import com.carpinteria.mitallercarpinteria.ui.theme.MiTallerCarpinteriaTheme
import com.carpinteria.mitallercarpinteria.ui.viewModels.vm.TallerViewModel
import com.carpinteria.mitallercarpinteria.data.model.ClienteDTO
import com.carpinteria.mitallercarpinteria.ui.componentes.HeaderSeccion
import com.carpinteria.mitallercarpinteria.ui.componentes.LinkAccion
import com.carpinteria.mitallercarpinteria.ui.componentes.ListaTarjetasAccion
import com.carpinteria.mitallercarpinteria.ui.componentes.ListaTarjetasHerramienta
import com.carpinteria.mitallercarpinteria.ui.componentes.TituloSeccion
import com.carpinteria.mitallercarpinteria.ui.theme.FondoCrema
import com.carpinteria.mitallercarpinteria.ui.viewModels.Uis.TallerUiState

// Composable contenedor que se usa en producción (obtiene datos del ViewModel)
@Composable
fun PaginaPrincipal(
    viewModel: TallerViewModel = viewModel(),
    onVerTodosClick: () -> Unit = {},
    onEditarTrabajosClick: () -> Unit = {},
    onEliminarTrabajosClick: () -> Unit = {},
    onCreateProjectClick: () -> Unit = {},
    onClienteClick: (Any) -> Unit = {},
    onHerramientaClick: (String) -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    PaginaPrincipalContent(
        uiState = uiState,
        onVerTodosClick = onVerTodosClick,
        onClienteClick = onClienteClick,
        onEditarTrabajosClick = onEditarTrabajosClick,
        onEliminarTrabajosClick = onEliminarTrabajosClick,
        onCreateProjectClick = onCreateProjectClick,
        onHerramientaClick = onHerramientaClick
    )
}

@Composable
fun PaginaPrincipalContent(
    uiState: TallerUiState,
    onVerTodosClick: () -> Unit = {},
    onClienteClick: (Any) -> Unit = {},
    onEditarTrabajosClick: () -> Unit = {},
    onEliminarTrabajosClick: () -> Unit = {},
    onCreateProjectClick: () -> Unit = {},
    onHerramientaClick: (String) -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(FondoCrema)
            .verticalScroll(rememberScrollState())
    ) {
        // 1. CABECERA SUPERIOR
        HeaderSeccion(
            titleTop = "TALLER DE CARPINTERÍA",
            titleMain = "La Buena Madera"
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            // 2. PROYECTOS RECIENTES
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                TituloSeccion(
                    title = "Proyectos recientes",
                    icon = Icons.Default.Schedule,
                    action = {
                        LinkAccion(
                            text = "Ver todos",
                            onClick = onVerTodosClick
                        )
                    }
                )

                // Muestra los primeros 3 proyectos usando el componente ListaTarjetasProyecto
                val proyectosRecientes = uiState.clientes.take(3)
                ListaTarjetasTrabajo(
                    clientes = proyectosRecientes,
                    onClienteClick = { cliente ->
                        onClienteClick(cliente)
                    }
                )
            }
            // 3. ACCIONES PRINCIPALES
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                TituloSeccion(
                    title = "Acciones principales",
                    icon = Icons.Default.Work
                )

                // Muestra las acciones usando el componente ListaTarjetasAccion
                ListaTarjetasAccion(
                    onAccederClick = onEditarTrabajosClick,
                    onEliminarClick = onEliminarTrabajosClick,
                    onCreateClick = onCreateProjectClick
                )
            }

            // 4. HERRAMIENTAS
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                TituloSeccion(
                    title = "Herramientas",
                    icon = Icons.Default.Build
                )

                ListaTarjetasHerramienta(
                    onHerramientaClick = onHerramientaClick
                )
            }
        }
    }
}

// Preview que usa el composable sin ViewModel y datos mockeados
@Preview(showBackground = true, backgroundColor = 0xFFFFF8E8)
@Composable
fun PaginaPrincipalPreview() {
    MiTallerCarpinteriaTheme {
        PaginaPrincipalContent(
            uiState = TallerUiState(
                clientes = listOf(
                    ClienteDTO(id = 1L, nombre = "Carmen Ruiz", telefono = "600111222", titulosEncargos = listOf("Armario de roble")),
                    ClienteDTO(id = 2L, nombre = "Manuel Ortega", telefono = "600333444", titulosEncargos = listOf("Mesa de comedor")),
                    ClienteDTO(id = 3L, nombre = "Pilar Gómez", telefono = "600555666", titulosEncargos = listOf("Puerta de entrada"))
                )
            )
        )
    }
}