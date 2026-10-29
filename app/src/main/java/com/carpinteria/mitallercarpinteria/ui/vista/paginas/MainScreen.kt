package com.carpinteria.mitallercarpinteria.ui.vista.paginas

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Work
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.carpinteria.mitallercarpinteria.data.model.ClienteDTO
import com.carpinteria.mitallercarpinteria.ui.componentes.*
import com.carpinteria.mitallercarpinteria.ui.theme.*
import com.carpinteria.mitallercarpinteria.ui.viewModels.vm.TallerViewModel

@Composable
fun HomeScreen(
    viewModel: TallerViewModel = viewModel(),
    onVerTodosClick: () -> Unit = {},
    onAccederTrabajoClick: () -> Unit = {},
    onEliminarTrabajoClick: () -> Unit = {},
    onCrearTrabajoClick: () -> Unit = {},
    onClienteClick: (ClienteDTO) -> Unit = {}
) {
    // Recogemos el estado del ViewModel de forma segura y reactiva
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    HomeScreenContent(
        clientes = uiState.clientes,
        isLoading = uiState.isLoading,
        onVerTodosClick = onVerTodosClick,
        onAccederTrabajoClick = onAccederTrabajoClick,
        onEliminarTrabajoClick = onEliminarTrabajoClick,
        onCrearTrabajoClick = onCrearTrabajoClick,
        onClienteClick = onClienteClick
    )
}

@Composable
fun HomeScreenContent(
    clientes: List<ClienteDTO>,
    isLoading: Boolean,
    onVerTodosClick: () -> Unit,
    onAccederTrabajoClick: () -> Unit,
    onEliminarTrabajoClick: () -> Unit,
    onCrearTrabajoClick: () -> Unit,
    onClienteClick: (ClienteDTO) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(FondoCrema)
            .verticalScroll(rememberScrollState())
            .padding(bottom = 40.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(28.dp)
        ) {
            // 1. Sección: Clientes / Proyectos recientes
            Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
                TituloSeccion(
                    title = "Clientes recientes",
                    icon = Icons.Default.Schedule,
                    action = {
                        LinkAccion(
                            text = "Ver todos",
                            onClick = onVerTodosClick
                        )
                    }
                )

                // Si está cargando, podemos mostrar un indicador o pintar la lista vacía
                if (isLoading) {
                    // Opcional: CircularProgressIndicator() o texto de carga
                } else if (clientes.isEmpty()) {
                    // Tarjeta o texto de respaldo si no hay datos aún
                    ProjectCard(
                        titulo = "Sin clientes",
                        cliente = "Cargando o vacío",
                        estado = "Pendiente",
                        estadoBg = YellowButtonBg,
                        estadoTextColor = YellowButtonDark
                    )
                } else {
                    // Pintamos los elementos que vienen de la API dinámicamente
                    clientes.forEach { cliente ->
                        ProjectCard(
                            titulo = cliente.nombre ?: "Sin nombre", // Ajusta según las propiedades de tu ClienteDTO
                            cliente = cliente.correo ?: "",
                            estado = "Activo",
                            estadoBg = GreenStatusBg,
                            estadoTextColor = GreenStatusText,
                            onClick = {
                                Log.d("HomeScreen", "Clic en cliente: ${cliente.nombre}")
                                onClienteClick(cliente)
                            }
                        )
                    }
                }
            }

            // 2. Sección: Acciones principales (Tus botones grandes)
            Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
                TituloSeccion(
                    title = "Acciones principales",
                    icon = Icons.Default.Work
                )

                ActionButtonCard(
                    titulo = "Acceder trabajo",
                    subtitulo = "Ver o modificar",
                    icon = Icons.Default.Edit,
                    bgColor = YellowButtonBg,
                    borderColor = YellowButtonDark,
                    onClick = onAccederTrabajoClick
                )

                ActionButtonCard(
                    titulo = "Eliminar trabajo",
                    subtitulo = "Borrar de la lista",
                    icon = Icons.Default.Delete,
                    bgColor = RedAlertBg,
                    borderColor = RedAlertText,
                    contentColor = RedAlertText,
                    onClick = onEliminarTrabajoClick
                )

                ActionButtonCard(
                    titulo = "Crear trabajo",
                    subtitulo = "Empezar uno nuevo",
                    icon = Icons.Default.Add,
                    bgColor = GreenStatusBg,
                    borderColor = GreenPrimary,
                    contentColor = GreenPrimary,
                    onClick = onCrearTrabajoClick
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    MiTallerCarpinteriaTheme {
        HomeScreenContent(
            clientes = listOf(
                ClienteDTO(), // Puedes mockear datos aquí para la preview si lo deseas
            ),
            isLoading = false,
            onVerTodosClick = {},
            onAccederTrabajoClick = {},
            onEliminarTrabajoClick = {},
            onCrearTrabajoClick = {},
            onClienteClick = {}
        )
    }
}
