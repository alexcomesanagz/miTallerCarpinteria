package com.carpinteria.mitallercarpinteria.ui.componentes

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.carpinteria.mitallercarpinteria.ui.theme.CardBorder
import com.carpinteria.mitallercarpinteria.ui.theme.MiTallerCarpinteriaTheme
import com.carpinteria.mitallercarpinteria.ui.theme.TextDarkBrown

@Composable
fun HerramientaProximamente(
    texto: String = "PRÓXIMAMENTE",
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(Color(0xFFFFFDF7), shape = RoundedCornerShape(6.dp))
            .border(1.5.dp, CardBorder, RoundedCornerShape(6.dp))
            .padding(horizontal = 6.dp, vertical = 2.dp)
    ) {
        Text(
            text = texto,
            color = TextDarkBrown,
            fontSize = 9.sp,
            fontWeight = FontWeight.Black
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFF8E8)
@Composable
fun HerramientaProximamentePreview() {
    MiTallerCarpinteriaTheme {
        Box(modifier = Modifier.padding(16.dp)) {
            HerramientaProximamente()
        }
    }
}