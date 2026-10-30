package com.carpinteria.mitallercarpinteria.ui.componentes

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.carpinteria.mitallercarpinteria.ui.theme.MiTallerCarpinteriaTheme
import com.carpinteria.mitallercarpinteria.ui.theme.YellowButtonBg
import com.carpinteria.mitallercarpinteria.ui.theme.YellowButtonDark

@Composable
fun TarjetaAccionContenedor(
    bgColor: Color,
    borderColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 7.dp)
            .border(4.dp, borderColor, RoundedCornerShape(18.dp)),
        colors = CardDefaults.cardColors(containerColor = bgColor),
        shape = RoundedCornerShape(18.dp),
        onClick = {
            Log.d("TarjetaAccionContenedor", "Tarjeta pulsada")
            onClick()
        }
    ) {
        content()
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFF8E8)
@Composable
fun TarjetaAccionContenedorPreview() {
    MiTallerCarpinteriaTheme {
        Box(modifier = Modifier.padding(16.dp)) {
            TarjetaAccionContenedor(
                bgColor = YellowButtonBg,
                borderColor = YellowButtonDark,
                onClick = {}
            ) {
                // Aquí dentro va el contenido ensamblado
            }
        }
    }
}