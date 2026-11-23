package com.carpinteria.mitallercarpinteria.ui.vista.paginas

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.carpinteria.mitallercarpinteria.ui.componentes.CampoTextoTaller
import com.carpinteria.mitallercarpinteria.ui.componentes.TarjetaContenedorDetalle
import com.carpinteria.mitallercarpinteria.ui.theme.*

@Composable
fun SeccionMedidasMaterialesPaso2(
    mediciones: String,
    materialesTexto: String,
    onMedicionesChange: (String) -> Unit,
    onMaterialesChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(
                text = "Medidas y materiales",
                fontSize = 22.sp,
                fontWeight = FontWeight.Black,
                color = WoodPrimary
            )
            Text(
                text = "Puede escribir una medida o material en cada línea.",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = MutedBrown
            )
        }

        TarjetaContenedorDetalle {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                CampoTextoTaller(
                    etiqueta = "Mediciones",
                    valor = mediciones,
                    onValorChange = onMedicionesChange,
                    placeholder = "Ej.: Alto: 200 cm, Ancho: 90 cm, Fondo: 50 cm",
                    singleLine = false,
                    minLines = 3
                )

                CampoTextoTaller(
                    etiqueta = "Materiales",
                    valor = materialesTexto,
                    onValorChange = onMaterialesChange,
                    placeholder = "Tipo de madera\nHerrajes\nAcabado o barniz",
                    singleLine = false,
                    minLines = 4
                )
            }
        }
    }
}

@Composable
fun PaginaPaso2Detalles(
    mediciones: String,
    materialesTexto: String,
    onMedicionesChange: (String) -> Unit,
    onMaterialesChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    SeccionMedidasMaterialesPaso2(
        mediciones = mediciones,
        materialesTexto = materialesTexto,
        onMedicionesChange = onMedicionesChange,
        onMaterialesChange = onMaterialesChange,
        modifier = modifier
    )
}

@Preview(showBackground = true, backgroundColor = 0xFFFFF8E8)
@Composable
fun PaginaPaso2DetallesPreview() {
    MiTallerCarpinteriaTheme {
        Box(modifier = Modifier.padding(16.dp)) {
            PaginaPaso2Detalles(
                mediciones = "",
                materialesTexto = "",
                onMedicionesChange = {},
                onMaterialesChange = {}
            )
        }
    }
}