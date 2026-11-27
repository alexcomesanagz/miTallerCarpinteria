package com.carpinteria.mitallercarpinteria.ui.componentes

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.carpinteria.mitallercarpinteria.ui.theme.*
import kotlin.math.roundToInt

@Composable
fun ControlesZoomFoto(
    zoom: Float,
    minZoom: Float = 1f,
    maxZoom: Float = 3f,
    onAlejarClick: () -> Unit = {},
    onAcercarClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Surface(
        color = DarkBrownHeader,
        modifier = modifier.fillMaxWidth()
    ) {
        Column {
            HorizontalDivider(thickness = 4.dp, color = CardBorder)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding()
                    .padding(horizontal = 18.dp, vertical = 14.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Button(
                    onClick = onAlejarClick,
                    enabled = zoom > minZoom,
                    modifier = Modifier.size(width = 72.dp, height = 62.dp),
                    shape = RoundedCornerShape(14.dp),
                    border = BorderStroke(3.dp, Color.White),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = YellowButtonBg,
                        contentColor = TextDarkBrown,
                        disabledContainerColor = Color(0xFFC7BBA8),
                        disabledContentColor = MutedBrown
                    ),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Remove,
                        contentDescription = "Alejar imagen",
                        modifier = Modifier.size(32.dp)
                    )
                }

                Text(
                    text = "${(zoom * 100).roundToInt()}%",
                    color = Color.White,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Black,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f)
                )

                Button(
                    onClick = onAcercarClick,
                    enabled = zoom < maxZoom,
                    modifier = Modifier.size(width = 72.dp, height = 62.dp),
                    shape = RoundedCornerShape(14.dp),
                    border = BorderStroke(3.dp, Color.White),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = YellowButtonBg,
                        contentColor = TextDarkBrown,
                        disabledContainerColor = Color(0xFFC7BBA8),
                        disabledContentColor = MutedBrown
                    ),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Acercar imagen",
                        modifier = Modifier.size(32.dp)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ControlesZoomFotoPreview() {
    MiTallerCarpinteriaTheme {
        ControlesZoomFoto(zoom = 1.5f)
    }
}