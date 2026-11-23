package com.carpinteria.mitallercarpinteria.ui.componentes

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.carpinteria.mitallercarpinteria.ui.theme.*

@Composable
fun BarraNavegacionInferior(
    textoBotonPrincipal: String,
    onPrincipalClick: () -> Unit,
    iconoPrincipal: ImageVector? = null,
    colorFondoPrincipal: Color = YellowButtonBg,
    colorBordePrincipal: Color = YellowButtonDark,
    colorTextoPrincipal: Color = TextDarkBrown,
    // Parámetros opcionales: si textoBotonSecundario es null, el botón principal ocupa todo el ancho
    textoBotonSecundario: String? = null,
    onSecundarioClick: () -> Unit = {},
    iconoSecundario: ImageVector? = Icons.AutoMirrored.Filled.ArrowBack,
    mostrarDivisorSuperior: Boolean = true,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(FondoCrema)
    ) {
        if (mostrarDivisorSuperior) {
            HorizontalDivider(thickness = 2.dp, color = LineColor)
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Botón Secundario Izquierdo (ej. "Volver" o "Anterior") - Solo se pinta si se le pasa texto
            if (!textoBotonSecundario.isNullOrBlank()) {
                Button(
                    onClick = onSecundarioClick,
                    modifier = Modifier
                        .weight(1f)
                        .height(52.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = CardBackground),
                    border = BorderStroke(2.5.dp, CardBorder),
                    contentPadding = PaddingValues(horizontal = 12.dp)
                ) {
                    if (iconoSecundario != null) {
                        Icon(
                            imageVector = iconoSecundario,
                            contentDescription = null,
                            tint = TextDarkBrown,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                    }
                    Text(
                        text = textoBotonSecundario,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Black,
                        color = TextDarkBrown,
                        maxLines = 1
                    )
                }
            }

            // Botón Principal Derecho (o único botón ancho si no hay secundario)
            Button(
                onClick = onPrincipalClick,
                modifier = Modifier
                    .weight(1f)
                    .height(52.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(containerColor = colorFondoPrincipal),
                border = BorderStroke(2.5.dp, colorBordePrincipal),
                contentPadding = PaddingValues(horizontal = 12.dp)
            ) {
                if (iconoPrincipal != null) {
                    Icon(
                        imageVector = iconoPrincipal,
                        contentDescription = null,
                        tint = colorTextoPrincipal,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                }
                Text(
                    text = textoBotonPrincipal,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Black,
                    color = colorTextoPrincipal,
                    maxLines = 1
                )
            }
        }
    }
}

// Previews de todos los modos de uso

@Preview(showBackground = true, backgroundColor = 0xFFFFF8E8, name = "1. Ficha: Volver y Editar")
@Composable
fun BarraNavegacionFichaPreview() {
    MiTallerCarpinteriaTheme {
        BarraNavegacionInferior(
            textoBotonSecundario = "Volver",
            onSecundarioClick = {},
            textoBotonPrincipal = "Editar",
            iconoPrincipal = Icons.Default.Edit,
            onPrincipalClick = {}
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFF8E8, name = "2. Formulario: Anterior y Siguiente")
@Composable
fun BarraNavegacionPasosPreview() {
    MiTallerCarpinteriaTheme {
        BarraNavegacionInferior(
            textoBotonSecundario = "Anterior",
            onSecundarioClick = {},
            textoBotonPrincipal = "Siguiente",
            iconoPrincipal = Icons.AutoMirrored.Filled.ArrowForward,
            onPrincipalClick = {}
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFF8E8, name = "3. Finalizar: Anterior y Guardar")
@Composable
fun BarraNavegacionGuardarPreview() {
    MiTallerCarpinteriaTheme {
        BarraNavegacionInferior(
            textoBotonSecundario = "Anterior",
            onSecundarioClick = {},
            textoBotonPrincipal = "Guardar trabajo",
            iconoPrincipal = Icons.Default.Check,
            colorFondoPrincipal = GreenPrimary,
            colorBordePrincipal = GreenStatusText,
            colorTextoPrincipal = Color.White,
            onPrincipalClick = {}
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFF8E8, name = "4. Paso 1: Único botón Siguiente")
@Composable
fun BarraNavegacionUnicoBotonPreview() {
    MiTallerCarpinteriaTheme {
        BarraNavegacionInferior(
            textoBotonSecundario = null, // Al ser null, el principal ocupa todo el ancho
            textoBotonPrincipal = "Siguiente",
            iconoPrincipal = Icons.AutoMirrored.Filled.ArrowForward,
            onPrincipalClick = {}
        )
    }
}