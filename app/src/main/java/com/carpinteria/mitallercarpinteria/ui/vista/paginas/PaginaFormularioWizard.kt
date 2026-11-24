package com.carpinteria.mitallercarpinteria.ui.vista.paginas

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.carpinteria.mitallercarpinteria.data.model.ClienteDTO
import com.carpinteria.mitallercarpinteria.ui.componentes.*
import com.carpinteria.mitallercarpinteria.ui.theme.*
import com.carpinteria.mitallercarpinteria.ui.viewModels.Uis.EstadoFormularioTrabajo

@Composable
fun PaginaFormularioTrabajoWizard(
    clienteInicial: ClienteDTO? = null,
    fotosDisponiblesEjemplo: List<String> = emptyList(),
    onCancelarClick: () -> Unit = {},
    onGuardarClick: (EstadoFormularioTrabajo) -> Unit = {}
) {
    val esEdicion = clienteInicial != null
    var paso by remember { mutableIntStateOf(1) }
    var estado by remember(clienteInicial) {
        mutableStateOf(EstadoFormularioTrabajo.desdeCliente(clienteInicial))
    }
    var errorTitulo by remember { mutableStateOf<String?>(null) }
    var errorCliente by remember { mutableStateOf<String?>(null) }
    var errorTelefono by remember { mutableStateOf<String?>(null) }

    fun avanzarPaso() {
        if (paso == 1) {
            errorTitulo =
                if (estado.tituloProyecto.isBlank()) "Escriba un título para continuar" else null
            errorCliente =
                if (estado.nombreCliente.isBlank()) "Escriba el nombre del cliente" else null
            errorTelefono =
                if (estado.telefonoCliente.isBlank()) "Escriba un teléfono de contacto" else null
            if (errorTitulo != null || errorCliente != null || errorTelefono != null) return
        }
        if (paso < 3) paso += 1
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = FondoCrema,
        topBar = {
            HeaderSeccion(
                titleTop = "PASO $paso DE 3",
                titleMain = if (esEdicion) "Editar trabajo" else "Nuevo trabajo",
                showBackButton = true,
                onBackClick = {
                    if (paso == 1) onCancelarClick() else paso -= 1
                }
            )
        },
        bottomBar = {
            when (paso) {
                1 -> BarraNavegacionInferior(
                    textoBotonSecundario = null,
                    textoBotonPrincipal = "Siguiente",
                    iconoPrincipal = Icons.AutoMirrored.Filled.ArrowForward,
                    onPrincipalClick = { avanzarPaso() }
                )

                2 -> BarraNavegacionInferior(
                    textoBotonSecundario = "Anterior",
                    iconoSecundario = Icons.AutoMirrored.Filled.ArrowBack,
                    onSecundarioClick = { paso -= 1 },
                    textoBotonPrincipal = "Siguiente",
                    iconoPrincipal = Icons.AutoMirrored.Filled.ArrowForward,
                    onPrincipalClick = { avanzarPaso() }
                )

                else -> BarraNavegacionInferior(
                    textoBotonSecundario = "Anterior",
                    iconoSecundario = Icons.AutoMirrored.Filled.ArrowBack,
                    onSecundarioClick = { paso -= 1 },
                    textoBotonPrincipal = "Guardar trabajo",
                    iconoPrincipal = Icons.Default.Check,
                    colorFondoPrincipal = GreenPrimary,
                    colorBordePrincipal = GreenStatusText,
                    colorTextoPrincipal = Color.White,
                    onPrincipalClick = { onGuardarClick(estado) }
                )
            }
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
            IndicadorPasos(pasoActual = paso)

            when (paso) {
                1 -> PaginaPaso1Cliente(
                    estado = estado,
                    errorTitulo = errorTitulo,
                    errorCliente = errorCliente,
                    errorTelefono = errorTelefono,
                    onTituloChange = {
                        estado = estado.copy(tituloProyecto = it)
                        errorTitulo = null
                    },
                    onClienteChange = {
                        estado = estado.copy(nombreCliente = it)
                        errorCliente = null
                    },
                    onTelefonoChange = {
                        estado = estado.copy(telefonoCliente = it)
                        errorTelefono = null
                    },
                    onEstadoChange = { estado = estado.copy(estadoTrabajo = it) }
                )

                2 -> PaginaPaso2Detalles(
                    mediciones = estado.mediciones,
                    materialesTexto = estado.materialesTexto,
                    onMedicionesChange = { estado = estado.copy(mediciones = it) },
                    onMaterialesChange = { estado = estado.copy(materialesTexto = it) }
                )

                else -> PaginaPaso3Fotos(
                    estado = estado,
                    onAdjuntarFotoClick = {
                        if (fotosDisponiblesEjemplo.isNotEmpty()) {
                            val siguienteFoto =
                                fotosDisponiblesEjemplo[estado.rutasImagenes.size % fotosDisponiblesEjemplo.size]
                            estado =
                                estado.copy(rutasImagenes = estado.rutasImagenes + siguienteFoto)
                        }
                    },
                    onEliminarFotoClick = { index ->
                        estado = estado.copy(
                            rutasImagenes = estado.rutasImagenes.filterIndexed { i, _ -> i != index }
                        )
                    }
                )
            }
        }
    }
}


@Preview(showBackground = true, backgroundColor = 0xFFFFF8E8)
@Composable
fun PaginaFormularioTrabajoWizardPreview() {
    MiTallerCarpinteriaTheme {
        PaginaFormularioTrabajoWizard()
    }
}


