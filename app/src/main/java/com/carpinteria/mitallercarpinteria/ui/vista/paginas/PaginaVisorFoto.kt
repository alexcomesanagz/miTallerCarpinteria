package com.carpinteria.mitallercarpinteria.ui.vista.paginas

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.carpinteria.mitallercarpinteria.R
import com.carpinteria.mitallercarpinteria.ui.componentes.CabeceraVisorFoto
import com.carpinteria.mitallercarpinteria.ui.componentes.ControlesZoomFoto
import com.carpinteria.mitallercarpinteria.ui.componentes.LienzoFotoAmpliable
import com.carpinteria.mitallercarpinteria.ui.theme.MiTallerCarpinteriaTheme

@Composable
fun PaginaVisorFoto(
    rutaFoto: String?,
    onCerrarClick: () -> Unit = {},
    @DrawableRes imagenErrorResId: Int = R.drawable.ic_launcher_background
) {
    val minZoom = 1f
    val maxZoom = 3f
    val pasoZoom = 0.5f
    var zoom by remember { mutableFloatStateOf(minZoom) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Color(0xFF17100C),
        topBar = {
            CabeceraVisorFoto(onCerrarClick = onCerrarClick)
        },
        bottomBar = {
            ControlesZoomFoto(
                zoom = zoom,
                minZoom = minZoom,
                maxZoom = maxZoom,
                onAlejarClick = { zoom = (zoom - pasoZoom).coerceAtLeast(minZoom) },
                onAcercarClick = { zoom = (zoom + pasoZoom).coerceAtMost(maxZoom) }
            )
        }
    ) { innerPadding ->
        LienzoFotoAmpliable(
            rutaFoto = rutaFoto,
            zoom = zoom,
            imagenErrorResId = imagenErrorResId,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PaginaVisorFotoPreview() {
    MiTallerCarpinteriaTheme {
        PaginaVisorFoto(rutaFoto = null)
    }
}