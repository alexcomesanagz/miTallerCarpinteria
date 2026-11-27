package com.carpinteria.mitallercarpinteria.ui.componentes

import androidx.annotation.DrawableRes
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.carpinteria.mitallercarpinteria.R
import com.carpinteria.mitallercarpinteria.ui.theme.MiTallerCarpinteriaTheme

@Composable
fun LienzoFotoAmpliable(
    rutaFoto: String?,
    zoom: Float,
    descripcion: String = "Vista ampliada de la fotografía",
    @DrawableRes imagenErrorResId: Int = R.drawable.ic_launcher_background,
    modifier: Modifier = Modifier
) {
    var desplazamiento by remember { mutableStateOf(Offset.Zero) }

    LaunchedEffect(zoom) {
        if (zoom <= 1f) {
            desplazamiento = Offset.Zero
        }
    }

    val context = LocalContext.current
    val modeloImagen = remember(rutaFoto) {
        ImageRequest.Builder(context)
            .data(rutaFoto.takeIf { !it.isNullOrBlank() })
            .crossfade(true)
            .error(imagenErrorResId)
            .fallback(imagenErrorResId)
            .build()
    }

    Box(
        modifier = modifier
            .clipToBounds()
            .pointerInput(zoom) {
                if (zoom > 1f) {
                    detectDragGestures { change, dragAmount ->
                        change.consume()
                        desplazamiento += dragAmount
                    }
                }
            }
            .padding(18.dp),
        contentAlignment = Alignment.Center
    ) {
        AsyncImage(
            model = modeloImagen,
            contentDescription = descripcion,
            error = painterResource(id = imagenErrorResId),
            fallback = painterResource(id = imagenErrorResId),
            placeholder = painterResource(id = imagenErrorResId),
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer(
                    scaleX = zoom,
                    scaleY = zoom,
                    translationX = desplazamiento.x,
                    translationY = desplazamiento.y
                )
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF17100C)
@Composable
fun LienzoFotoAmpliablePreview() {
    MiTallerCarpinteriaTheme {
        LienzoFotoAmpliable(
            rutaFoto = null,
            zoom = 1f,
            modifier = Modifier.fillMaxSize()
        )
    }
}