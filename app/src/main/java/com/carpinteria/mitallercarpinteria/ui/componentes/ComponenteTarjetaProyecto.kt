package com.carpinteria.mitallercarpinteria.ui.componentes

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.carpinteria.mitallercarpinteria.ui.theme.*

// Modos configurables para la tarjeta de proyecto
enum class ModoTarjetaProyecto {
    COMPLETA,       // Vista original con flecha
    SOLO_EDITAR,    // Franja lateral con Lápiz
    SOLO_ELIMINAR   // Franja lateral con Papelera
}

@Composable
fun TarjetaProyecto(
    titulo: String,
    cliente: String,
    estado: String,
    estadoBg: Color = GreenStatusBg,
    estadoTextColor: Color = GreenStatusText,
    modo: ModoTarjetaProyecto = ModoTarjetaProyecto.COMPLETA,
    onClick: () -> Unit = {},
    onEditarClick: () -> Unit = {},
    onEliminarClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    TarjetaContenedorProyecto(onClick = onClick, modifier = modifier) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Información del proyecto (Izquierda)
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(17.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                TarjetaInfoProyecto(
                    titulo = titulo,
                    cliente = cliente
                )
                Spacer(modifier = Modifier.height(12.dp))
                PildoraEstadoProyecto(
                    estado = estado,
                    estadoBg = estadoBg,
                    estadoTextColor = estadoTextColor
                )
            }

            // Panel de Acción (Derecha)
            when (modo) {
                ModoTarjetaProyecto.COMPLETA -> {
                    Column(
                        horizontalAlignment = Alignment.End,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.padding(end = 17.dp)
                    ) {
                        BotonRedondoFlecha()
                    }
                }

                ModoTarjetaProyecto.SOLO_EDITAR -> {
                    Spacer(
                        modifier = Modifier
                            .width(3.dp)
                            .fillMaxHeight()
                            .background(CardBorder)
                    )
                    Box(
                        modifier = Modifier
                            .width(72.dp)
                            .fillMaxHeight()
                            .background(YellowButtonBg)
                            .clickable { onEditarClick() },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Editar proyecto",
                            tint = YellowButtonDark,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                }

                ModoTarjetaProyecto.SOLO_ELIMINAR -> {
                    Spacer(
                        modifier = Modifier
                            .width(3.dp)
                            .fillMaxHeight()
                            .background(CardBorder)
                    )
                    Box(
                        modifier = Modifier
                            .width(72.dp)
                            .fillMaxHeight()
                            .background(RedAlertText)
                            .clickable { onEliminarClick() },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Eliminar proyecto",
                            tint = Color.White,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                }
            }
        }
    }
}

// PREVIEWS

@Preview(showBackground = true, backgroundColor = 0xFFFFF8E8)
@Composable
fun TarjetaProyectoCompletaPreview() {
    MiTallerCarpinteriaTheme {
        Box(modifier = Modifier.padding(16.dp)) {
            TarjetaProyecto(
                titulo = "Armario de roble",
                cliente = "Carmen Ruiz",
                estado = "En curso",
                modo = ModoTarjetaProyecto.COMPLETA
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFF8E8)
@Composable
fun TarjetaProyectoSoloEditarPreview() {
    MiTallerCarpinteriaTheme {
        Box(modifier = Modifier.padding(16.dp)) {
            TarjetaProyecto(
                titulo = "Estantería salón",
                cliente = "Antonio León",
                estado = "En curso",
                modo = ModoTarjetaProyecto.SOLO_EDITAR
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFF8E8)
@Composable
fun TarjetaProyectoSoloEliminarPreview() {
    MiTallerCarpinteriaTheme {
        Box(modifier = Modifier.padding(16.dp)) {
            TarjetaProyecto(
                titulo = "Puerta de entrada",
                cliente = "Pilar Gómez",
                estado = "Terminado",
                modo = ModoTarjetaProyecto.SOLO_ELIMINAR
            )
        }
    }
}