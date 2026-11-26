package com.carpinteria.mitallercarpinteria.ui.componentes

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DeleteSweep
import androidx.compose.material.icons.filled.EditCalendar
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.carpinteria.mitallercarpinteria.ui.theme.*
import kotlinx.coroutines.delay

enum class TipoAvisoEncargo {
    CREADO,
    ACTUALIZADO,
    ELIMINADO,
    ERROR
}

private data class ConfigAvisoEncargo(
    val titulo: String,
    val mensajeDefecto: String,
    val icono: ImageVector,
    val contenedorColor: Color,
    val bordeColor: Color,
    val iconoTint: Color
)

@Composable
fun AvisoFeedbackEncargo(
    visible: Boolean,
    tipo: TipoAvisoEncargo,
    detalle: String? = null,
    onDismiss: () -> Unit = {},
    duracionMillis: Long = 3500L,
    modifier: Modifier = Modifier
) {
    LaunchedEffect(visible) {
        if (visible && duracionMillis > 0L) {
            delay(duracionMillis)
            onDismiss()
        }
    }

    val errorBg = Color(0xFFFDECEA)
    val errorBorde = Color(0xFFC62828)

    val config = when (tipo) {
        TipoAvisoEncargo.CREADO -> ConfigAvisoEncargo(
            titulo = "¡Encargo creado!",
            mensajeDefecto = "El nuevo trabajo se ha guardado correctamente.",
            icono = Icons.Default.CheckCircle,
            contenedorColor = GreenStatusBg,
            bordeColor = GreenStatusText,
            iconoTint = GreenStatusText
        )
        TipoAvisoEncargo.ACTUALIZADO -> ConfigAvisoEncargo(
            titulo = "¡Encargo actualizado!",
            mensajeDefecto = "Los cambios del trabajo se han guardado correctamente.",
            icono = Icons.Default.EditCalendar,
            contenedorColor = GreenStatusBg,
            bordeColor = GreenStatusText,
            iconoTint = GreenStatusText
        )
        TipoAvisoEncargo.ELIMINADO -> ConfigAvisoEncargo(
            titulo = "¡Encargo eliminado!",
            mensajeDefecto = "El trabajo y sus datos asociados se han eliminado.",
            icono = Icons.Default.DeleteSweep,
            contenedorColor = GreenStatusBg,
            bordeColor = GreenStatusText,
            iconoTint = GreenStatusText
        )
        TipoAvisoEncargo.ERROR -> ConfigAvisoEncargo(
            titulo = "No se pudo completar la acción",
            mensajeDefecto = "Ha ocurrido un error al procesar el encargo.",
            icono = Icons.Default.ErrorOutline,
            contenedorColor = errorBg,
            bordeColor = errorBorde,
            iconoTint = errorBorde
        )
    }

    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(),
        exit = fadeOut(),
        modifier = modifier
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 84.dp),
            shape = RoundedCornerShape(18.dp),
            color = config.contenedorColor,
            border = BorderStroke(3.dp, config.bordeColor),
            shadowElevation = 8.dp
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 18.dp, vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(46.dp)
                        .clip(CircleShape)
                        .background(config.iconoTint.copy(alpha = 0.14f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = config.icono,
                        contentDescription = config.titulo,
                        tint = config.iconoTint,
                        modifier = Modifier.size(30.dp)
                    )
                }

                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = config.titulo,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Black,
                        color = TextDarkBrown
                    )
                    Text(
                        text = detalle?.takeIf { it.isNotBlank() } ?: config.mensajeDefecto,
                        fontSize = 14.sp,
                        lineHeight = 19.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextDarkBrown.copy(alpha = 0.9f)
                    )
                }

                IconButton(
                    onClick = onDismiss,
                    modifier = Modifier.size(34.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Cerrar aviso",
                        tint = TextDarkBrown,
                        modifier = Modifier.size(22.dp)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFF8E8)
@Composable
fun AvisoFeedbackEncargoPreview() {
    MiTallerCarpinteriaTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            AvisoFeedbackEncargo(
                visible = true,
                tipo = TipoAvisoEncargo.CREADO,
                detalle = "Mesa de comedor",
                duracionMillis = 0L
            )
            AvisoFeedbackEncargo(
                visible = true,
                tipo = TipoAvisoEncargo.ACTUALIZADO,
                detalle = "Armario de roble",
                duracionMillis = 0L
            )
            AvisoFeedbackEncargo(
                visible = true,
                tipo = TipoAvisoEncargo.ELIMINADO,
                detalle = "Puerta de entrada",
                duracionMillis = 0L
            )
            AvisoFeedbackEncargo(
                visible = true,
                tipo = TipoAvisoEncargo.ERROR,
                detalle = "No se ha podido completar la acción",
                duracionMillis = 0L
            )
        }
    }
}