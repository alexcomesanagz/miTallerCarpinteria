package com.carpinteria.mitallercarpinteria.ui.componentes

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.carpinteria.mitallercarpinteria.ui.theme.*

@Composable
fun TarjetaContenedorDetalle(
    modifier: Modifier = Modifier,
    containerColor: Color = CardBackground,
    borderColor: Color = CardBorder,
    borderWidth: Dp = 3.dp,
    cornerRadius: Dp = 18.dp,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(cornerRadius),
        colors = CardDefaults.cardColors(containerColor = containerColor),
        border = BorderStroke(borderWidth, borderColor)
    ) {
        androidx.compose.foundation.layout.Column(
            modifier = Modifier.fillMaxWidth(),
            content = content
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFF8E8, name = "Tarjeta Contenedor Detalle")
@Composable
fun TarjetaContenedorDetallePreview() {
    MiTallerCarpinteriaTheme {
        Box(modifier = Modifier.padding(16.dp)) {
            TarjetaContenedorDetalle {
                androidx.compose.foundation.layout.Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "CONTENEDOR DE SECCIÓN",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Black,
                        color = WoodPrimary
                    )
                    Text(
                        text = "Contenido interior modular",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextDarkBrown
                    )
                }
            }
        }
    }
}
