package com.carpinteria.mitallercarpinteria.ui.componentes

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.carpinteria.mitallercarpinteria.ui.theme.MiTallerCarpinteriaTheme
import com.carpinteria.mitallercarpinteria.ui.theme.MutedBrown
import com.carpinteria.mitallercarpinteria.ui.theme.TextDarkBrown

@Composable
fun TarjetaInfoProyecto(
    titulo: String,
    cliente: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = titulo,
            fontWeight = FontWeight.Black,
            fontSize = 24.sp,
            color = TextDarkBrown,
            lineHeight = 26.sp
        )
        Spacer(modifier = Modifier.height(7.dp))
        Text(
            text = cliente,
            color = MutedBrown,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TarjetaInfoProyectoPreview() {
    MiTallerCarpinteriaTheme {
        Box(modifier = Modifier.padding(16.dp)) {
            TarjetaInfoProyecto(
                titulo = "Armario de roble",
                cliente = "Carmen Ruiz"
            )
        }
    }
}