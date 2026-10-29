package com.carpinteria.mitallercarpinteria.ui.componentes

import android.util.Log
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.carpinteria.mitallercarpinteria.ui.theme.MiTallerCarpinteriaTheme
import com.carpinteria.mitallercarpinteria.ui.theme.WoodPrimary

@Composable
fun LinkAccion(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    TextButton(
        onClick = {
            Log.d("LinkAccionPreview", "Acción pulsada: $text")
            onClick()
        },
        contentPadding = PaddingValues(0.dp),
        modifier = modifier
    ) {
        Text(
            text = text,
            fontSize = 15.sp,
            fontWeight = FontWeight.Black,
            color = WoodPrimary,
            textDecoration = TextDecoration.Underline
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFF8E8)
@Composable
fun LinkAccionPreview() {
    MiTallerCarpinteriaTheme {
        LinkAccion(
            text = "Ver todos",
            onClick = {
                Log.d("LinkAccionPreview", "Clic en vista previa")
            }
        )
    }
}