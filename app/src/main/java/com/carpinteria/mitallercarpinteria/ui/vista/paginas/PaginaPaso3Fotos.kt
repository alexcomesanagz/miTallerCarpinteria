package com.carpinteria.mitallercarpinteria.ui.vista.paginas

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
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
import com.carpinteria.mitallercarpinteria.ui.componentes.FotoAdjunta
import com.carpinteria.mitallercarpinteria.ui.componentes.TarjetaContenedorDetalle
import com.carpinteria.mitallercarpinteria.ui.componentes.TarjetaImagenAmpliable
import com.carpinteria.mitallercarpinteria.ui.theme.*
import com.carpinteria.mitallercarpinteria.ui.viewModels.Uis.EstadoFormularioTrabajo

@Composable
fun SeccionFotosResumenPaso3(
    estado: EstadoFormularioTrabajo,
    onAdjuntarFotoClick: () -> Unit,
    onEliminarFotoClick: (Int) -> Unit,
    @DrawableRes imagenErrorResId: Int = R.drawable.ic_launcher_background,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(
                text = "Fotografías",
                fontSize = 22.sp,
                fontWeight = FontWeight.Black,
                color = WoodPrimary
            )
            Text(
                text = "Añada fotos para reconocer el trabajo fácilmente.",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = MutedBrown
            )
        }

        Button(
            onClick = onAdjuntarFotoClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp),
            colors = ButtonDefaults.buttonColors(containerColor = YellowButtonBg),
            border = BorderStroke(2.5.dp, YellowButtonDark)
        ) {
            Icon(
                imageVector = Icons.Outlined.PhotoCamera,
                contentDescription = null,
                tint = TextDarkBrown
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Adjuntar fotografía",
                color = TextDarkBrown,
                fontWeight = FontWeight.Black,
                fontSize = 16.sp
            )
        }

        estado.rutasImagenes.forEachIndexed { index, ruta ->
            FotoAdjunta(
                numeroFoto = index + 1,
                rutaGaleria = ruta,
                imagenErrorResId = imagenErrorResId,
                onEliminarClick = { onEliminarFotoClick(index) }
            )
        }

        TarjetaContenedorDetalle {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = "RESUMEN",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Black,
                    color = WoodPrimary
                )
                Text(
                    text = estado.tituloProyecto.ifBlank { "Sin título" },
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Black,
                    color = TextDarkBrown
                )
                Text(
                    text = estado.nombreCliente.ifBlank { "Sin cliente" },
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = MutedBrown
                )
                Text(
                    text = "Estado: ${estado.estadoTrabajo}",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextDarkBrown
                )
            }
        }
    }
}

@Composable
fun PaginaPaso3Fotos(
    estado: EstadoFormularioTrabajo,
    onAdjuntarFotoClick: () -> Unit,
    onEliminarFotoClick: (Int) -> Unit,
    @DrawableRes imagenErrorResId: Int = R.drawable.ic_launcher_background,
    modifier: Modifier = Modifier
) {
    SeccionFotosResumenPaso3(
        estado = estado,
        onAdjuntarFotoClick = onAdjuntarFotoClick,
        onEliminarFotoClick = onEliminarFotoClick,
        imagenErrorResId = imagenErrorResId,
        modifier = modifier
    )
}

@Preview(showBackground = true, backgroundColor = 0xFFFFF8E8)
@Composable
fun PaginaPaso3FotosPreview() {
    MiTallerCarpinteriaTheme {
        Box(modifier = Modifier.padding(16.dp)) {
            PaginaPaso3Fotos(
                estado = EstadoFormularioTrabajo(
                    tituloProyecto = "Mesa de comedor",
                    nombreCliente = "Manuel Ortega",
                    estadoTrabajo = "Pendiente"
                ),
                onAdjuntarFotoClick = {},
                onEliminarFotoClick = {}
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFF8E8, name = "Paso 3 - Con varias fotos")
@Composable
fun PaginaPaso3FotosConImagenesPreview() {
    MiTallerCarpinteriaTheme {
        Box(modifier = Modifier.padding(16.dp)) {
            PaginaPaso3Fotos(
                estado = EstadoFormularioTrabajo(
                    tituloProyecto = "Armario de roble",
                    nombreCliente = "Carmen Ruiz",
                    estadoTrabajo = "En curso",
                    rutasImagenes = listOf(
                        "https://images.unsplash.com/photo-1659930087003-2d64e33181f7?auto=format&fit=crop&w=900&q=82",
                        "https://images.unsplash.com/photo-1679797850019-3d0d8659a695?auto=format&fit=crop&w=900&q=82"
                    )
                ),
                onAdjuntarFotoClick = {},
                onEliminarFotoClick = {},
                imagenErrorResId = android.R.drawable.ic_menu_report_image
            )
        }
    }
}