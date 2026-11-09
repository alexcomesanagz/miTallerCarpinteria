package com.carpinteria.mitallercarpinteria.ui.componentes

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.carpinteria.mitallercarpinteria.ui.theme.*

@Composable
fun DialogoConfirmarEliminar(
    tituloProyecto: String,
    onConfirmar: () -> Unit,
    onDescartar: () -> Unit
) {
    Dialog(onDismissRequest = onDescartar) {
        Surface(
            shape = RoundedCornerShape(22.dp),
            color = Color(0xFFFFF9EC),
            border = BorderStroke(6.dp, RedAlertText),
            shadowElevation = 8.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Icono superior circular rojo
                Box(
                    modifier = Modifier
                        .size(68.dp)
                        .background(RedAlertText, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(36.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "¿Eliminar este trabajo?",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Black,
                    color = RedAlertText,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Va a borrar \"$tituloProyecto\". Esta acción no se puede deshacer.",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextDarkBrown,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Botón Confirmar con tipo predefinido PELIGRO
                BotonDialogo(
                    texto = "Sí, eliminar",
                    tipo = TipoBotonDialogo.PELIGRO,
                    onClick = onConfirmar
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Botón Cancelar con tipo predefinido SECUNDARIO
                BotonDialogo(
                    texto = "No, volver atrás",
                    tipo = TipoBotonDialogo.SECUNDARIO,
                    onClick = onDescartar
                )
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF302218)
@Composable
fun DialogoConfirmarEliminarPreview() {
    MiTallerCarpinteriaTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            DialogoConfirmarEliminar(
                tituloProyecto = "Armario de roble",
                onConfirmar = {},
                onDescartar = {}
            )
        }
    }
}