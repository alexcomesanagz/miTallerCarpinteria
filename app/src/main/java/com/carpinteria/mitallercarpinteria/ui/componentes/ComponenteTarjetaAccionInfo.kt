package com.carpinteria.mitallercarpinteria.ui.componentes

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.carpinteria.mitallercarpinteria.ui.theme.MiTallerCarpinteriaTheme
import com.carpinteria.mitallercarpinteria.ui.theme.TextDarkBrown

@Composable
fun TarjetaAccionInfo(
    titulo: String,
    subtitulo: String,
    contentColor: Color = TextDarkBrown,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = titulo,
            fontSize = 20.sp,
            fontWeight = FontWeight.Black,
            color = contentColor,
            lineHeight = 22.sp
        )
        Spacer(modifier = Modifier.height(3.dp))
        Text(
            text = subtitulo,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            color = contentColor.copy(alpha = 0.8f)
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFF8E8)
@Composable
fun TarjetaAccionInfoPreview() {
    MiTallerCarpinteriaTheme {
        Box(modifier = Modifier.padding(16.dp)) {
            TarjetaAccionInfo(
                titulo = "Acceder trabajo",
                subtitulo = "Ver o modificar"
            )
        }
    }
}