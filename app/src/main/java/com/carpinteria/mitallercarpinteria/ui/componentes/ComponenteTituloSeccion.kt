package com.carpinteria.mitallercarpinteria.ui.componentes

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.carpinteria.mitallercarpinteria.ui.theme.DarkBrownHeader
import com.carpinteria.mitallercarpinteria.ui.theme.LineColor
import com.carpinteria.mitallercarpinteria.ui.theme.MiTallerCarpinteriaTheme
import com.carpinteria.mitallercarpinteria.ui.theme.WoodPrimary

@Composable
fun TituloSeccion(
    title: String,
    icon: ImageVector,
    action: @Composable (() -> Unit)? = null // Parte opcional para cualquier tipo de acción
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icono y Título (Parte izquierda)
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = DarkBrownHeader,
                    modifier = Modifier.size(27.dp)
                )
                Spacer(modifier = Modifier.width(9.dp))
                Text(
                    text = title.uppercase(),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Black,
                    color = DarkBrownHeader
                )
            }

            // Acción añadida en caso de existir (Parte derecha)
            if (action != null) {
                action()
            }
        }

        // Línea inferior de separación
        HorizontalDivider(
            thickness = 3.dp,
            color = LineColor
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFF8E8)
@Composable
fun TituloSeccionAccionPreview() {
    MiTallerCarpinteriaTheme {
        Box(modifier = Modifier.padding(16.dp)) {
            TituloSeccion(
                title = "Proyectos recientes",
                icon = Icons.Default.Schedule,
                action = {
                    LinkAccion(
                        text = "Ver todos",
                        onClick = { /* Navegar a la lista completa */ }
                    )
                }
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFF8E8)
@Composable
fun TituloSeccionPreview() {
    MiTallerCarpinteriaTheme {
        Box(modifier = Modifier.padding(16.dp)) {
            TituloSeccion(
                title = "Acciones principales",
                icon = Icons.Default.Work
            )
        }
    }
}