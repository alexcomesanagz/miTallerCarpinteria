package com.carpinteria.mitallercarpinteria.ui.componentes

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.carpinteria.mitallercarpinteria.ui.theme.*

@Composable
fun TarjetaAccion(
    titulo: String,
    subtitulo: String,
    icon: ImageVector,
    bgColor: Color,
    borderColor: Color,
    contentColor: Color = TextDarkBrown,
    onClick: () -> Unit = {}
) {
    // Usa el contenedor principal como base
    TarjetaAccionContenedor(
        bgColor = bgColor,
        borderColor = borderColor,
        onClick = {
            Log.d("BotonTarjetaAccion", "Acción pulsada: $titulo")
            onClick()
        }
    ) {
        // Fila interna que organiza los subcomponentes
        Row(
            modifier = Modifier
                .padding(14.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Icono de la izquierda
            TarjetaAccionIcono(
                icon = icon,
                contentColor = contentColor
            )

            Spacer(modifier = Modifier.width(13.dp))

            // Textos del centro (Título y Subtítulo)
            TarjetaAccionInfo(
                titulo = titulo,
                subtitulo = subtitulo,
                contentColor = contentColor,
                modifier = Modifier.weight(1f)
            )

            // Flecha indicativa de la derecha
            Flecha(
                contentColor = contentColor
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFF8E8)
@Composable
fun TarjetaAccionPreview() {
    MiTallerCarpinteriaTheme {
        Box(modifier = Modifier.padding(16.dp)) {
            TarjetaAccion(
                titulo = "Acceder trabajo",
                subtitulo = "Ver o modificar",
                icon = Icons.Default.Edit,
                bgColor = YellowButtonBg,
                borderColor = YellowButtonDark,
                onClick = { Log.d("BotonTarjetaAccionPreview", "Pulsado desde preview") }
            )
        }
    }
}