package com.carpinteria.mitallercarpinteria.ui.componentes

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.carpinteria.mitallercarpinteria.ui.theme.MiTallerCarpinteriaTheme
import com.carpinteria.mitallercarpinteria.ui.theme.TextDarkBrown

@Composable
fun Flecha(
    contentColor: Color = TextDarkBrown,
    modifier: Modifier = Modifier
) {
    Icon(
        imageVector = Icons.Default.ChevronRight,
        contentDescription = null,
        tint = contentColor,
        modifier = modifier.size(31.dp)
    )
}

@Preview(showBackground = true, backgroundColor = 0xFFFFF8E8)
@Composable
fun FlechaPreview() {
    MiTallerCarpinteriaTheme {
        Box(modifier = Modifier.padding(16.dp)) {
            Flecha()
        }
    }
}