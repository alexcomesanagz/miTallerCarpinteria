package com.carpinteria.mitallercarpinteria.ui.componentes

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.HelpOutline
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.carpinteria.mitallercarpinteria.ui.theme.*

@Composable
fun TarjetaHerramienta(
    titulo: String,
    icon: ImageVector,
    isEnabled: Boolean = false,
    urlDestino: String? = null, // Opcional: solo se usa si se le pasa explícitamente
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val uriHandler = LocalUriHandler.current

    val backgroundColor = if (isEnabled) Color(0xFFF8DC88) else Color(0xFFEEE5D6)
    val borderColor = if (isEnabled) Color(0xFF936416) else Color(0xFFA9977F)

    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(120.dp),
        shape = RoundedCornerShape(17.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        border = BorderStroke(3.dp, borderColor),
        onClick = {
            if (!urlDestino.isNullOrBlank()) {
                // Si la tarjeta tiene una URL configurada (ej. Ayuda -> YouTube), abre el enlace
                uriHandler.openUri(urlDestino)
            } else {
                // Si no tiene URL, ejecuta su acción normal (para el resto de herramientas a futuro)
                onClick()
            }
        }
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
fun TarjetaHerramientaAyudaPreview() {
    MiTallerCarpinteriaTheme {
        Box(modifier = Modifier.padding(16.dp)) {
            TarjetaHerramienta(
                titulo = "Ayuda",
                icon = Icons.AutoMirrored.Filled.HelpOutline,
                isEnabled = true,
                urlDestino = "https://www.youtube.com/@midudev" // Enlace de prueba para YouTube
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFF8E8)
@Composable
fun TarjetaHerramientaInactivaPreview() {
    MiTallerCarpinteriaTheme {
        Box(modifier = Modifier.padding(16.dp)) {
            TarjetaHerramienta(
                titulo = "Mejorar plan",
                icon = Icons.Default.Work,
                isEnabled = false // Deshabilitada por defecto para mostrar "Próximamente"
            )
        }
    }
}