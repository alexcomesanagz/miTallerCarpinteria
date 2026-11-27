package com.carpinteria.mitallercarpinteria.ui.vista.paginas

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.PhotoCamera
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.carpinteria.mitallercarpinteria.R
import com.carpinteria.mitallercarpinteria.ui.componentes.FotoAdjunta
import com.carpinteria.mitallercarpinteria.ui.componentes.TarjetaContenedorDetalle
import com.carpinteria.mitallercarpinteria.ui.theme.*
import com.carpinteria.mitallercarpinteria.ui.viewModels.Uis.EstadoFormularioTrabajo

@Composable
fun SeccionFotosResumenPaso3(
    estado: EstadoFormularioTrabajo,
    onAdjuntarFotoClick: () -> Unit,
    onEliminarFotoClick: (Int) -> Unit,
    onAmpliarFotoClick: (String) -> Unit = {},
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
                onEliminarClick = { onEliminarFotoClick(index) },
                onAmpliarClick = { onAmpliarFotoClick(ruta) }
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
    onAmpliarFotoClick: (String) -> Unit = {},
    @DrawableRes imagenErrorResId: Int = R.drawable.ic_launcher_background,
    modifier: Modifier = Modifier
) {
    SeccionFotosResumenPaso3(
        estado = estado,
        onAdjuntarFotoClick = onAdjuntarFotoClick,
        onEliminarFotoClick = onEliminarFotoClick,
        onAmpliarFotoClick = onAmpliarFotoClick,
        imagenErrorResId = imagenErrorResId,
        modifier = modifier
    )
}