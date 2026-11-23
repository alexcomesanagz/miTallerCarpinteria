package com.carpinteria.mitallercarpinteria.ui.vista.paginas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.carpinteria.mitallercarpinteria.R
import com.carpinteria.mitallercarpinteria.data.model.ClienteDTO
import com.carpinteria.mitallercarpinteria.ui.componentes.*
import com.carpinteria.mitallercarpinteria.ui.theme.*

@Composable
fun PaginaDetalleTrabajo(
    cliente: ClienteDTO?,
    onVolverClick: () -> Unit = {},
    onEditarClick: (ClienteDTO) -> Unit = {}
) {
    PaginaDetalleTrabajoContent(
        cliente = cliente,
        onVolverClick = onVolverClick,
        onEditarClick = onEditarClick
    )
}

@Composable
fun PaginaDetalleTrabajoContent(
    cliente: ClienteDTO?,
    onVolverClick: () -> Unit = {},
    onEditarClick: (ClienteDTO) -> Unit = {}
) {
    var estadoSeleccionado by remember { mutableStateOf("En curso") }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = FondoCrema,
        topBar = {
            HeaderSeccion(
                titleTop = "DATOS DEL ENCARGO",
                titleMain = "Ficha del trabajo",
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // 1. Datos del Cliente
            TarjetaDatosCliente(
                nombreCliente = cliente?.nombre ?: "Carmen Ruiz",
                telefono = cliente?.telefono ?: "612 345 780"
            )

            // 2. Fechas en paralelo (Creación y Entrega)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                TarjetaFecha(
                    titulo = "Fecha creación",
                    dia = "12",
                    mesAnio = "Mayo\n2025",
                    containerColor = YellowButtonBg,
                    borderColor = YellowButtonDark,
                    modifier = Modifier.weight(1f)
                )
                TarjetaFecha(
                    titulo = "Fecha de entrega",
                    dia = "20",
                    mesAnio = "Junio\n2025",
                    modifier = Modifier.weight(1f)
                )
            }

            // 3. Selector de Estado del trabajo
            SeccionEstadoTrabajo(
                estadoActual = estadoSeleccionado,
                onEstadoSeleccionado = { estadoSeleccionado = it }
            )

            // 4. Tarjeta Numerada: Mediciones
            TarjetaSeccionNumerada(
                numero = "1",
                titulo = "MEDICIONES",
                contenido = listOf(
                    "Alto: 240 cm",
                    "Ancho: 180 cm",
                    "Fondo: 60 cm"
                )
            )

            // 5. Tarjeta Numerada: Materiales
            TarjetaSeccionNumerada(
                numero = "2",
                titulo = "MATERIALES",
                contenido = listOf(
                    "Tablero de roble 19 mm",
                    "6 bisagras reforzadas",
                    "Barniz mate"
                )
            )

            // 6. Sección de Fotografías (con soporte para Coil y ampliación modular)
            SeccionFotos(
                rutasImagenes = listOf(
                    "content://media/external/images/media/1",
                    "content://media/external/images/media/2"
                ),
            )

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

// PREVIEW DE LA PÁGINA COMPLETA
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
            )
        )
    }
}