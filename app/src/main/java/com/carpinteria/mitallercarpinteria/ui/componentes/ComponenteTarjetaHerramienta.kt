package com.carpinteria.mitallercarpinteria.ui.componentes

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.HelpOutline
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.carpinteria.mitallercarpinteria.ui.theme.*

@Composable
fun TarjetaHerramienta(
    titulo: String,
    icon: ImageVector,
    isEnabled: Boolean = false, // Por defecto inhabilitadas
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    // Colores que varían dependiendo si una herramienta esta implementada o no
    val backgroundColor = if (isEnabled) Color(0xFFF8DC88) else Color(0xFFEEE5D6)
    val borderColor = if (isEnabled) Color(0xFF936416) else Color(0xFFA9977F)

    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(120.dp),
        shape = RoundedCornerShape(17.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        border = BorderStroke(3.dp, borderColor),
        onClick = onClick
    ) {
        TarjetaHerramientaContenido(
            titulo = titulo,
            icon = icon,
            showProximamente = !isEnabled,
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFF8E8)
@Composable
fun TarjetaHerramientaPreview() {
    MiTallerCarpinteriaTheme {
        Box(modifier = Modifier.padding(16.dp)) {
            TarjetaHerramienta(
                titulo = "Ayuda",
                icon = Icons.AutoMirrored.Filled.HelpOutline,
                isEnabled = true
            )
        }
    }
}