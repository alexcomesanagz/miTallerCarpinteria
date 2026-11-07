package com.carpinteria.mitallercarpinteria.ui.componentes

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.PlusOne
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.carpinteria.mitallercarpinteria.ui.theme.*

@Composable
fun ListaTarjetasAccion(
    onAccederClick: () -> Unit,
    onEliminarClick: () -> Unit,
    onCreateClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(14.dp) // Espacio simétrico idéntico a las tarjetas proyecto
    ) {
        // 1.Tarjeta: ACCEDER trabajo
        TarjetaAccion(
            titulo = "Acceder trabajo",
            subtitulo = "Ver o modificar",
            icon = Icons.Default.Edit,
            bgColor = YellowButtonBg,
            borderColor = YellowButtonDark,
            onClick = {
                Log.d("ListaAcciones", "Acceder trabajo pulsado")
                onAccederClick()
            }
        )

        // 2.Tarjeta: ELIMINAR trabajo
        TarjetaAccion(
            titulo = "Eliminar trabajo",
            subtitulo = "Borrar de la lista",
            icon = Icons.Default.Delete,
            bgColor = RedAlertBg,
            borderColor = RedAlertText,
            contentColor = RedAlertText,
            onClick = {
                Log.d("ListaAcciones", "Eliminar trabajo pulsado")
                onEliminarClick()
            }
        )

        // 3. Tarjeta: CREAR trabajo
        TarjetaAccion(
            titulo = "Crear trabajo",
            subtitulo = "Empezar uno nuevo",
            icon = Icons.Default.PlusOne,
            bgColor = Color(0xFFBFE6C6),
            borderColor = GreenPrimary,
            contentColor = GreenStatusText,
            onClick = {
                Log.d("ListaAcciones", "Crear trabajo pulsado")
                onCreateClick()
            }
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFF8E8)
@Composable
fun ListaTarjetasAccionPreview() {
    MiTallerCarpinteriaTheme {
        Box(modifier = Modifier.padding(16.dp)) {
            ListaTarjetasAccion(
                onAccederClick = {},
                onEliminarClick = {},
                onCreateClick = {}
            )
        }
    }
}