package com.carpinteria.mitallercarpinteria.ui.componentes

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.carpinteria.mitallercarpinteria.ui.theme.*

@Composable
fun ListaTarjetasHerramienta(
    onHerramientaClick: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Fila 1: Iniciar sesión / Cerrar sesión (Próximamente)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(modifier = Modifier.weight(1f)) {
                TarjetaHerramienta(
                    titulo = "Iniciar sesión",
                    icon = Icons.Default.Person,
                    isEnabled = false,
                    onClick = { onHerramientaClick("Iniciar sesión") }
                )
            }
            Box(modifier = Modifier.weight(1f)) {
                TarjetaHerramienta(
                    titulo = "Cerrar sesión",
                    icon = Icons.Default.Lock,
                    isEnabled = false,
                    onClick = { onHerramientaClick("Cerrar sesión") }
                )
            }
        }

        // Fila 2: Crear presupuesto / Establecer precios (Próximamente)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(modifier = Modifier.weight(1f)) {
                TarjetaHerramienta(
                    titulo = "Crear presupuesto",
                    icon = Icons.Default.PhotoCamera,
                    isEnabled = false,
                    onClick = { onHerramientaClick("Crear presupuesto") }
                )
            }
            Box(modifier = Modifier.weight(1f)) {
                TarjetaHerramienta(
                    titulo = "Establecer precios",
                    icon = Icons.Default.LocalOffer,
                    isEnabled = false,
                    onClick = { onHerramientaClick("Establecer precios") }
                )
            }
        }

        // Fila 3: Mejorar plan (Próximamente) / Ayuda (Activa)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(modifier = Modifier.weight(1f)) {
                TarjetaHerramienta(
                    titulo = "Mejorar plan",
                    icon = Icons.Default.Work,
                    isEnabled = false,
                    onClick = { onHerramientaClick("Mejorار plan") }
                )
            }
            Box(modifier = Modifier.weight(1f)) {
                TarjetaHerramienta(
                    titulo = "Ayuda",
                    icon = Icons.Default.HelpOutline,
                    isEnabled = true, // Activa y destacada en amarillo
                    onClick = { onHerramientaClick("Ayuda") }
                )
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFF8E8)
@Composable
fun ListaTarjetasHerramientaPreview() {
    MiTallerCarpinteriaTheme {
        Box(modifier = Modifier.padding(16.dp)) {
            ListaTarjetasHerramienta()
        }
    }
}