package com.carpinteria.mitallercarpinteria.ui.componentes

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ZoomIn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.carpinteria.mitallercarpinteria.ui.theme.*

@Composable
fun TarjetaImagenAmpliable(
    rutaGaleria: String?,
    descripcion: String = "Fotografía del trabajo",
    @DrawableRes imagenErrorResId: Int = android.R.drawable.ic_menu_report_image, //vUsa un icono de sistema de error para distinguirlo
    onAmpliarClick: () -> Unit = {}, // Aquí se pasara la navegación en el futuro
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    val modeloImagen = remember(rutaGaleria) {
        ImageRequest.Builder(context)
            .data(rutaGaleria.takeIf { !it.isNullOrBlank() })
            .crossfade(true)
            .error(imagenErrorResId)
            .fallback(imagenErrorResId)
            .build()
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(180.dp)
            .clip(RoundedCornerShape(14.dp))
            .border(2.5.dp, CardBorder, RoundedCornerShape(14.dp))
            .clickable { onAmpliarClick() } // Lanza la acción de ir a la otra página
    ) {
        AsyncImage(
            model = modeloImagen,
            contentDescription = descripcion,
            error = painterResource(id = imagenErrorResId),
            fallback = painterResource(id = imagenErrorResId),
            // Añadimos un color de fondo temporal o placeholder para que nunca se quede en blanco
            placeholder = painterResource(id = imagenErrorResId),
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFEFE6D8)),
            contentScale = ContentScale.Crop
        )

        // Botón ampliar infeior derecho
        Surface(
            onClick = onAmpliarClick, // Lanza la acción aquí también
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(10.dp),
            shape = RoundedCornerShape(8.dp),
            color = Color(0xCC24170E)
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.ZoomIn,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(15.dp)
                )
                Text(
                    text = "Pulse para ampliar",
                    color = Color.White,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Black
                )
            }
        }
    }
}

// 1. Preview para cuando no hay ruta (fuerza el estado de error por defecto)
@Preview(showBackground = true, backgroundColor = 0xFFFFF8E8, name = "Sin imagen (Error)")
@Composable
fun TarjetaImagenAmpliableSinRutaPreview() {
    MiTallerCarpinteriaTheme {
        Box(modifier = Modifier.padding(16.dp)) {
            TarjetaImagenAmpliable(
                rutaGaleria = null, // Esto activa el error
            )
        }
    }
}