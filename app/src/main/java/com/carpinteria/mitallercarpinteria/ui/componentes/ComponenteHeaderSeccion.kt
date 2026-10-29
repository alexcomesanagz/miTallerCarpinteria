package com.carpinteria.mitallercarpinteria.ui.componentes

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.carpinteria.mitallercarpinteria.R
import com.carpinteria.mitallercarpinteria.ui.theme.DarkBrownHeader
import com.carpinteria.mitallercarpinteria.ui.theme.MiTallerCarpinteriaTheme

@Composable
fun HeaderSection(
    titleTop: String = "TALLER DE CARPINTERÍA",
    titleMain: String = "La Buena Madera",
    logoResId: Int = R.drawable.ic_launcher_foreground
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(DarkBrownHeader)
            .padding(vertical = 20.dp, horizontal = 16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(DarkBrownHeader),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = logoResId),
                    contentDescription = "Logo de la empresa",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = titleTop,
                    color = Color.White.copy(alpha = 0.8f),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = titleMain,
                    color = Color.White,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HeaderSectionPreview() {
    MiTallerCarpinteriaTheme {
        HeaderSection()
    }
}