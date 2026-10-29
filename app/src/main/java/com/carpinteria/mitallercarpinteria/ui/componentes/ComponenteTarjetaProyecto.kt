package com.carpinteria.mitallercarpinteria.ui.componentes

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.carpinteria.mitallercarpinteria.ui.theme.GreenStatusBg
import com.carpinteria.mitallercarpinteria.ui.theme.GreenStatusText
import com.carpinteria.mitallercarpinteria.ui.theme.MiTallerCarpinteriaTheme

@Composable
fun TarjetaProyecto(
    titulo: String,
    cliente: String,
    estado: String,
    estadoBg: Color,
    estadoTextColor: Color,
    onClick: () -> Unit = {}
) {
    TarjetaContenedorProyecto(onClick = onClick) {
        Row(
            modifier = Modifier
                .padding(17.dp, 17.dp, 17.dp, 19.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Título y cliente por separado
            TarjetaInfoProyecto(
                titulo = titulo,
                cliente = cliente,
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.width(12.dp))

            // Columna derecha con Estado arriba y Flecha abajo
            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxHeight()
            ) {
                PildoraEstadoProyecto(
                    estado = estado,
                    estadoBg = estadoBg,
                    estadoTextColor = estadoTextColor
                )

                Spacer(modifier = Modifier.height(16.dp))

                BotonRedondoFlecha()
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFF8E8) // Fondo crema de la app
@Composable
fun ProjectCardPreview() {
    MiTallerCarpinteriaTheme {
        Box(modifier = Modifier.padding(16.dp)) {
            TarjetaProyecto(
                titulo = "Armario de roble",
                cliente = "Carmen Ruiz",
                estado = "En curso",
                estadoBg = GreenStatusBg,
                estadoTextColor = GreenStatusText,
                onClick = {
                    // Log exclusivo para pulsar la tarjeta en la Preview
                    Log.d("ProjectCardPreview", "¡Clic en la tarjeta de prueba desde la Preview!")
                }
            )
        }
    }
}