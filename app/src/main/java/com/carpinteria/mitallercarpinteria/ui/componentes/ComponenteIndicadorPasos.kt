package com.carpinteria.mitallercarpinteria.ui.componentes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.carpinteria.mitallercarpinteria.ui.theme.*

@Composable
fun IndicadorPasos(
    pasoActual: Int,
    modifier: Modifier = Modifier,
    etiquetas: List<String> = listOf("Cliente", "Detalles", "Fotos")
) {
    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.TopCenter
    ) {
        HorizontalDivider(
            thickness = 3.dp,
            color = CardBorder,
            modifier = Modifier
                .padding(top = 20.dp, start = 48.dp, end = 48.dp)
                .fillMaxWidth()
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            etiquetas.forEachIndexed { index, etiqueta ->
                val pasoNumero = index + 1
                val completado = pasoNumero < pasoActual
                val activo = pasoNumero <= pasoActual

                val colorCirculo = when {
                    completado || activo -> GreenPrimary
                    else -> Color(0xFFDDD1BF)
                }
                val colorBorde = when {
                    completado || activo -> GreenStatusText
                    else -> MutedBrown
                }
                val colorTextoCirculo = if (activo) Color.White else TextDarkBrown
                val colorEtiqueta = if (activo) TextDarkBrown else MutedBrown

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(6.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Box(
                        modifier = Modifier
                            .size(42.dp)
                            .background(colorCirculo, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        androidx.compose.foundation.Canvas(modifier = Modifier.fillMaxSize()) {
                            drawCircle(
                                color = colorBorde,
                                style = androidx.compose.ui.graphics.drawscope.Stroke(width = 3.dp.toPx())
                            )
                        }
                        if (completado) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = null,
                                tint = colorTextoCirculo,
                                modifier = Modifier.size(20.dp)
                            )
                        } else {
                            Text(
                                text = pasoNumero.toString(),
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Black,
                                color = colorTextoCirculo
                            )
                        }
                    }

                    Text(
                        text = etiqueta,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Black,
                        color = colorEtiqueta
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFF8E8)
@Composable
fun StepperPasosPreview() {
    MiTallerCarpinteriaTheme {
        Box(modifier = Modifier.padding(16.dp)) {
            IndicadorPasos(pasoActual = 2)
        }
    }
}