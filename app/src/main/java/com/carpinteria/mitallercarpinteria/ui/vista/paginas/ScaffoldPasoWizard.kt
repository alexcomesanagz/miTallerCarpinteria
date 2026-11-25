package com.carpinteria.mitallercarpinteria.ui.vista.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.carpinteria.mitallercarpinteria.ui.componentes.BarraNavegacionInferior
import com.carpinteria.mitallercarpinteria.ui.componentes.HeaderSeccion
import com.carpinteria.mitallercarpinteria.ui.componentes.IndicadorPasos
import com.carpinteria.mitallercarpinteria.ui.theme.*

@Composable
fun ScaffoldPasoWizard(
    paso: Int,
    esEdicion: Boolean,
    onAtrasClick: () -> Unit,
    onSiguienteOGuardarClick: () -> Unit,
    contenido: @Composable ColumnScope.() -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = FondoCrema,
        topBar = {
            HeaderSeccion(
                titleTop = "PASO $paso DE 3",
                titleMain = if (esEdicion) "Editar trabajo" else "Nuevo trabajo",
                showBackButton = true,
                onBackClick = onAtrasClick
            )
        },
        bottomBar = {
            when (paso) {
                1 -> BarraNavegacionInferior(
                    textoBotonSecundario = null,
                    textoBotonPrincipal = "Siguiente",
                    iconoPrincipal = Icons.AutoMirrored.Filled.ArrowForward,
                    onPrincipalClick = onSiguienteOGuardarClick
                )
                2 -> BarraNavegacionInferior(
                    textoBotonSecundario = "Anterior",
                    iconoSecundario = Icons.AutoMirrored.Filled.ArrowBack,
                    onSecundarioClick = onAtrasClick,
                    textoBotonPrincipal = "Siguiente",
                    iconoPrincipal = Icons.AutoMirrored.Filled.ArrowForward,
                    onPrincipalClick = onSiguienteOGuardarClick
                )
                else -> BarraNavegacionInferior(
                    textoBotonSecundario = "Anterior",
                    iconoSecundario = Icons.AutoMirrored.Filled.ArrowBack,
                    onSecundarioClick = onAtrasClick,
                    textoBotonPrincipal = "Guardar trabajo",
                    iconoPrincipal = Icons.Default.Check,
                    colorFondoPrincipal = GreenPrimary,
                    colorBordePrincipal = GreenStatusText,
                    colorTextoPrincipal = Color.White,
                    onPrincipalClick = onSiguienteOGuardarClick
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
            contenido()
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}