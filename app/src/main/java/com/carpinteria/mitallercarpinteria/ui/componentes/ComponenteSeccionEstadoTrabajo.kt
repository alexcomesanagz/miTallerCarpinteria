package com.carpinteria.mitallercarpinteria.ui.componentes

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.carpinteria.mitallercarpinteria.ui.theme.*

@Composable
fun SeccionEstadoTrabajo(
    estadoActual: String,
    onEstadoSeleccionado: (String) -> Unit,
    modifier: Modifier = Modifier,
    estadosDisponibles: List<String> = listOf("Pendiente", "En curso", "Terminado")
) {
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Estado del trabajo",
            fontSize = 15.sp,
            fontWeight = FontWeight.Black,
            color = TextDarkBrown
        )

        Box(modifier = Modifier.fillMaxWidth()) {
            Surface(
                onClick = { expanded = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                shape = RoundedCornerShape(14.dp),
                color = Color(0xFFFFF2C2),
                border = BorderStroke(2.5.dp, YellowButtonDark)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = estadoActual,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Black,
                        color = TextDarkBrown
                    )
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowDown,
                        contentDescription = "Cambiar estado",
                        tint = TextDarkBrown,
                        modifier = Modifier.size(26.dp)
                    )
                }
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                containerColor = CardBackground
            ) {
                estadosDisponibles.forEach { estado ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = estado,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextDarkBrown
                            )
                        },
                        onClick = {
                            onEstadoSeleccionado(estado)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFF8E8)
@Composable
fun SeccionEstadoTrabajoPreview() {
    MiTallerCarpinteriaTheme {
        var estado by remember { mutableStateOf("En curso") }
        Box(modifier = Modifier.padding(16.dp)) {
            SeccionEstadoTrabajo(
                estadoActual = estado,
                onEstadoSeleccionado = { estado = it }
            )
        }
    }
}