package com.carpinteria.mitallercarpinteria.ui.vista.paginas

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.carpinteria.mitallercarpinteria.ui.componentes.ProjectList
import com.carpinteria.mitallercarpinteria.ui.theme.MiTallerCarpinteriaTheme
import com.carpinteria.mitallercarpinteria.ui.viewModels.vm.TallerViewModel
import com.carpinteria.mitallercarpinteria.data.model.ClienteDTO

@Composable
fun PaginaPrincipal(
    viewModel: TallerViewModel = viewModel(),
    onVerTodosClick: () -> Unit = {},
    onClienteClick: (Any) -> Unit = {}
) {
    // Observamos el estado reactivo del ViewModel
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Aquí puedes mostrar la lista de clientes o encargos que vienen del repositorio
        ProjectList(
            clientes = uiState.clientes,
            onClienteClick = { cliente ->
                onClienteClick(cliente)
            }
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFF8E8)
@Composable
fun PaginaPrincipalPreview() {
    MiTallerCarpinteriaTheme {
        // Renderiza la vista directamente con datos simulados para la preview
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            ProjectList(
                clientes = listOf(
                    ClienteDTO(id = 1L, nombre = "Carmen Ruiz", telefono = "600111222", titulosEncargos = listOf("Armario de roble")),
                    ClienteDTO(id = 2L, nombre = "Manuel Ortega", telefono = "600333444", titulosEncargos = listOf("Mesa de comedor")),
                    ClienteDTO(id = 3L, nombre = "Pilar Gómez", telefono = "600555666", titulosEncargos = listOf("Puerta de entrada"))
                ),
                onClienteClick = {}
            )
        }
    }
}