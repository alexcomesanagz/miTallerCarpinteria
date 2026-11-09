package com.carpinteria.mitallercarpinteria.ui.componentes

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.carpinteria.mitallercarpinteria.ui.theme.*

// Tipos de avisos predefinidos y extensibles para el futuro
enum class TipoAviso(
    val icono: ImageVector,
    val textoPorDefecto: String,
    val backgroundColor: Color,
    val borderColor: Color,
    val textColor: Color
) {
    ACCEDER(
        icono = Icons.Default.Visibility,
        textoPorDefecto = "Pulse una tarjeta para ver todos sus datos.",
        backgroundColor = Color(0xFFFFF0AE),
        borderColor = YellowButtonDark,
        textColor = TextDarkBrown
    ),
    EDITAR(
        icono = Icons.Default.Edit,
        textoPorDefecto = "Pulse el lápiz del trabajo que desea modificar.",
        backgroundColor = Color(0xFFFFF0AE),
        borderColor = YellowButtonDark,
        textColor = TextDarkBrown
    ),
    ELIMINAR(
        icono = Icons.Default.Delete,
        textoPorDefecto = "Pulse la papelera del trabajo que desea borrar.",
        backgroundColor = RedAlertBg,
        borderColor = RedAlertText,
        textColor = RedAlertText
    )
}

@Composable
fun TarjetaAvisoInstruccion(
    tipo: TipoAviso = TipoAviso.ACCEDER,
    textoPersonalizado: String? = null,
    modifier: Modifier = Modifier
) {
    val textoAMostrar = textoPersonalizado ?: tipo.textoPorDefecto

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = tipo.backgroundColor),
        border = BorderStroke(2.dp, tipo.borderColor)
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Icon(
                imageVector = tipo.icono,
                contentDescription = null,
                tint = tipo.textColor,
                modifier = Modifier.size(20.dp)
            )
            Text(
                text = textoAMostrar,
                fontSize = 14.sp,
                fontWeight = FontWeight.Black,
                color = tipo.textColor
            )
        }
    }
}

// PREVIEWS
@Preview(showBackground = true, backgroundColor = 0xFFFFF8E8)
@Composable
fun TarjetaAvisoAccederPreview() {
    MiTallerCarpinteriaTheme {
        Box(modifier = Modifier.padding(16.dp)) {
            TarjetaAvisoInstruccion(tipo = TipoAviso.ACCEDER)
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFF8E8)
@Composable
fun TarjetaAvisoEditarPreview() {
    MiTallerCarpinteriaTheme {
        Box(modifier = Modifier.padding(16.dp)) {
            TarjetaAvisoInstruccion(tipo = TipoAviso.EDITAR)
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFF8E8)
@Composable
fun TarjetaAvisoEliminarPreview() {
    MiTallerCarpinteriaTheme {
        Box(modifier = Modifier.padding(16.dp)) {
            TarjetaAvisoInstruccion(tipo = TipoAviso.ELIMINAR)
        }
    }
}