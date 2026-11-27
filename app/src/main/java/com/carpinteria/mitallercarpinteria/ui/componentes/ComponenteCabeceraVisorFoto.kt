package com.carpinteria.mitallercarpinteria.ui.componentes

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.carpinteria.mitallercarpinteria.ui.theme.DarkBrownHeader
import com.carpinteria.mitallercarpinteria.ui.theme.MiTallerCarpinteriaTheme
import com.carpinteria.mitallercarpinteria.ui.theme.TextDarkBrown
import com.carpinteria.mitallercarpinteria.ui.theme.YellowButtonBg

@Composable
fun CabeceraVisorFoto(
    titulo: String = "Fotografía ampliada",
    onCerrarClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Surface(
        color = DarkBrownHeader,
        modifier = modifier.fillMaxWidth()
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .padding(horizontal = 18.dp, vertical = 14.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = titulo,
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Black
                )

                Button(
                    onClick = onCerrarClick,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = YellowButtonBg,
                        contentColor = TextDarkBrown
                    ),
                    border = BorderStroke(3.dp, Color.White),
                    shape = RoundedCornerShape(12.dp),
                    contentPadding = PaddingValues(horizontal = 14.dp, vertical = 10.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Cerrar fotografía",
                        modifier = Modifier.size(22.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Cerrar",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Black
                    )
                }
            }
            HorizontalDivider(thickness = 4.dp, color = YellowButtonBg)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CabeceraVisorFotoPreview() {
    MiTallerCarpinteriaTheme {
        CabeceraVisorFoto()
    }
}