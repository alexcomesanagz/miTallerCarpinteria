package com.carpinteria.mitallercarpinteria.ui.componentes

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.carpinteria.mitallercarpinteria.ui.theme.GreenPrimary
import com.carpinteria.mitallercarpinteria.ui.theme.GreenStatusBg
import com.carpinteria.mitallercarpinteria.ui.theme.GreenStatusText
import com.carpinteria.mitallercarpinteria.ui.theme.MiTallerCarpinteriaTheme
import com.carpinteria.mitallercarpinteria.ui.theme.YellowButtonBg
import com.carpinteria.mitallercarpinteria.ui.theme.YellowButtonDark

fun resolverColoresEstado(estado: String): Pair<Color, Color> {
    return when (estado.trim().lowercase()) {
        "pendiente" -> YellowButtonBg.copy(alpha = 0.45f) to YellowButtonDark
        "terminado" -> GreenPrimary to Color.White
        else -> GreenStatusBg to GreenStatusText // "en curso" y estados por defecto
    }
}

@Composable
fun PildoraEstadoProyecto(
    estado: String,
    estadoBg: Color? = null,
    estadoTextColor: Color? = null
) {
    val (fondoCalculado, textoCalculado) = resolverColoresEstado(estado)
    val fondoFinal = estadoBg ?: fondoCalculado
    val textoFinal = estadoTextColor ?: textoCalculado

    Box(
        modifier = Modifier
            .background(fondoFinal, shape = RoundedCornerShape(99.dp))
            .border(2.dp, textoFinal.copy(alpha = 0.6f), RoundedCornerShape(99.dp))
            .padding(horizontal = 10.dp, vertical = 4.dp)
    ) {
        Text(
            text = estado,
            color = textoFinal,
            fontSize = 12.sp,
            fontWeight = FontWeight.Black
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PildoraEstadoProyectoPreview() {
    MiTallerCarpinteriaTheme {
        Box(modifier = Modifier.padding(16.dp)) {
            PildoraEstadoProyecto(
                estado = "En curso",
                estadoBg = GreenStatusBg,
                estadoTextColor = GreenStatusText
            )
        }
    }
}