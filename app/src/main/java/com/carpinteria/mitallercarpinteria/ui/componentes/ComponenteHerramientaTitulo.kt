package com.carpinteria.mitallercarpinteria.ui.componentes

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.carpinteria.mitallercarpinteria.ui.theme.MiTallerCarpinteriaTheme
import com.carpinteria.mitallercarpinteria.ui.theme.TextDarkBrown

@Composable
fun HerramientaTitulo(
    titulo: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = titulo,
        fontSize = 16.sp,
        fontWeight = FontWeight.Black,
        color = TextDarkBrown,
        maxLines = 2,
        overflow = TextOverflow.Ellipsis,
        lineHeight = 18.sp,
        modifier = modifier
    )
}

@Preview(showBackground = true, backgroundColor = 0xFFFFF8E8)
@Composable
fun HerramientaTituloPreview() {
    MiTallerCarpinteriaTheme {
        Box(modifier = Modifier.padding(16.dp)) {
            HerramientaTitulo(titulo = "Crear presupuesto")
        }
    }
}