package com.carpinteria.mitallercarpinteria.ui.componentes

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.carpinteria.mitallercarpinteria.R
import com.carpinteria.mitallercarpinteria.ui.theme.*

@Composable
fun FotoAdjunta(
    numeroFoto: Int,
    rutaGaleria: String,
    onEliminarClick: () -> Unit,
    onAmpliarClick: () -> Unit = {},
    @DrawableRes imagenErrorResId: Int = R.drawable.ic_launcher_background,
    modifier: Modifier = Modifier
) {
    TarjetaContenedorDetalle(modifier = modifier) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            TarjetaImagenAmpliable(
                rutaGaleria = rutaGaleria,
                descripcion = "Fotografía adjunta $numeroFoto",
                imagenErrorResId = imagenErrorResId,
                onAmpliarClick = onAmpliarClick
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Foto $numeroFoto",
                    fontWeight = FontWeight.Black,
                    color = TextDarkBrown
                )

                TextButton(onClick = onEliminarClick) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.error
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Eliminar",
                        color = MaterialTheme.colorScheme.error,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFF8E8, name = "Elemento Foto Adjunta")
@Composable
fun ElementoFotoAdjuntaPreview() {
    MiTallerCarpinteriaTheme {
        Box(modifier = Modifier.padding(16.dp)) {
            FotoAdjunta(
                numeroFoto = 1,
                rutaGaleria = "",
                onEliminarClick = {}
            )
        }
    }
}