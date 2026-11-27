package com.carpinteria.mitallercarpinteria.ui.componentes

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.PhotoCamera
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.carpinteria.mitallercarpinteria.R
import com.carpinteria.mitallercarpinteria.ui.theme.*

@Composable
fun SeccionFotos(
    rutasImagenes: List<String>,
    onAmpliarFoto: (String) -> Unit = {},
    @DrawableRes imagenErrorResId: Int = R.drawable.ic_launcher_background,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = CardBackground),
        border = BorderStroke(3.dp, CardBorder)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Cabecera con icono de cámara y texto
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Outlined.PhotoCamera,
                    contentDescription = null,
                    tint = WoodPrimary,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "FOTOGRAFÍAS",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Black,
                    color = WoodPrimary,
                    letterSpacing = 0.5.sp
                )
            }

            if (rutasImagenes.isEmpty()) {
                Text(
                    text = "No hay fotografías adjuntas en este trabajo.",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = MutedBrown
                )
            } else {
                rutasImagenes.forEachIndexed { index, ruta ->
                    TarjetaImagenAmpliable(
                        rutaGaleria = ruta,
                        descripcion = "Fotografía ${index + 1} del trabajo",
                        imagenErrorResId = imagenErrorResId,
                        onAmpliarClick = { onAmpliarFoto(ruta) }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFF8E8)
@Composable
fun SeccionFotosContenedorPreview() {
    MiTallerCarpinteriaTheme {
        Box(modifier = Modifier.padding(16.dp)) {
            SeccionFotos(
                rutasImagenes = listOf(
                    "content://media/external/images/media/101", // Ruta simulada (mostrará error en preview)
                    "" // Ruta vacía (mostrará error por defecto)
                )
            )
        }
    }
}