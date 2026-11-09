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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.carpinteria.mitallercarpinteria.ui.theme.GreenStatusBg
import com.carpinteria.mitallercarpinteria.ui.theme.GreenStatusText
import com.carpinteria.mitallercarpinteria.ui.theme.MiTallerCarpinteriaTheme
@Composable
fun PildoraEstadoProyecto(
    estado: String,
    estadoBg: Color,
    estadoTextColor: Color
) {
    Box(
        modifier = Modifier
            .background(estadoBg, shape = RoundedCornerShape(99.dp))
            .border(2.dp, estadoTextColor.copy(alpha = 0.6f), RoundedCornerShape(99.dp))
            .padding(horizontal = 10.dp, vertical = 4.dp)
    ) {
        Text(
            text = estado,
            color = estadoTextColor,
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