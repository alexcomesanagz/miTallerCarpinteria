package com.carpinteria.mitallercarpinteria.ui.componentes

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.carpinteria.mitallercarpinteria.ui.theme.MiTallerCarpinteriaTheme

@Composable
fun TarjetaHerramientaContenido(
    titulo: String,
    icon: ImageVector,
    showProximamente: Boolean = true,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(12.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.Start
    ) {
        // 1. Icono superior
        HerramientaIcono(icon = icon)

        // 2. Contenedor con el título arriba y Píldora abajo
        Column(
            verticalArrangement = Arrangement.spaced4IfNeeded(),
            modifier = Modifier.fillMaxWidth()
        ) {
            HerramientaTitulo(
                titulo = titulo,
                modifier = Modifier.fillMaxWidth()
            )

            if (showProximamente) {
                Spacer(modifier = Modifier.height(2.dp))
                HerramientaProximamente()
            }
        }
    }
}

@Composable
private fun Arrangement.spaced4IfNeeded() = Arrangement.spacedBy(4.dp)

@Preview(showBackground = true, backgroundColor = 0xFFFFF8E8)
@Composable
fun TarjetaHerramientaContenidoPreview() {
    MiTallerCarpinteriaTheme {
        Box(modifier = Modifier.padding(16.dp).size(150.dp, 115.dp)) {
            TarjetaHerramientaContenido(
                titulo = "Iniciar sesión",
                icon = Icons.Default.Person,
            )
        }
    }
}