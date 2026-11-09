package com.carpinteria.mitallercarpinteria.ui.componentes

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.carpinteria.mitallercarpinteria.ui.theme.*

// Define los tipos de botones disponibles para los diálogos
enum class TipoBotonDialogo {
    PELIGRO,     // Acciones destructivas (rojo)
    SECUNDARIO,  // Cancelar o volver (blanco con borde)
    PRINCIPAL    // Acción principal (amarillo)
}

@Composable
fun BotonDialogo(
    texto: String,
    tipo: TipoBotonDialogo = TipoBotonDialogo.PELIGRO,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Asigna colores y bordes automáticos según el tipo elegido
    val (containerColor, contentColor, borderColor) = when (tipo) {
        TipoBotonDialogo.PELIGRO -> Triple(RedAlertText, Color.White, Color(0xFF601713))
        TipoBotonDialogo.SECUNDARIO -> Triple(Color.White, TextDarkBrown, CardBorder)
        TipoBotonDialogo.PRINCIPAL -> Triple(YellowButtonBg, TextDarkBrown, YellowButtonDark)
    }

    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(52.dp),
        shape = RoundedCornerShape(14.dp),
        colors = ButtonDefaults.buttonColors(containerColor = containerColor),
        border = BorderStroke(3.dp, borderColor)
    ) {
        Text(
            text = texto,
            color = contentColor,
            fontSize = 17.sp,
            fontWeight = FontWeight.Black
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFF9EC)
@Composable
fun BotonDialogoPeligroPreview() {
    MiTallerCarpinteriaTheme {
        Box(modifier = Modifier.padding(16.dp)) {
            BotonDialogo(
                texto = "Sí, eliminar",
                tipo = TipoBotonDialogo.PELIGRO,
                onClick = {}
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFF9EC)
@Composable
fun BotonDialogoSecundarioPreview() {
    MiTallerCarpinteriaTheme {
        Box(modifier = Modifier.padding(16.dp)) {
            BotonDialogo(
                texto = "No, volver atrás",
                tipo = TipoBotonDialogo.SECUNDARIO,
                onClick = {}
            )
        }
    }
}