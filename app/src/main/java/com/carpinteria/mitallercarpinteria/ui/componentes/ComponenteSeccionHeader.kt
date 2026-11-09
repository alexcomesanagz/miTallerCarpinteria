package com.carpinteria.mitallercarpinteria.ui.componentes

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import com.carpinteria.mitallercarpinteria.ui.theme.*

@Composable
fun HeaderSeccion(
    titleTop: String = "TALLER DE CARPINTERÍA",
    titleMain: String = "La Buena Madera",
    logoResId: Int = R.drawable.ic_launcher_foreground,
    showBackButton: Boolean = false,
    onBackClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(DarkBrownHeader)
            .padding(vertical = 20.dp, horizontal = 16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            // Si se requiere botón de retroceso, lo muestra en lugar del logo corporativo
            if (showBackButton) {
                IconButton(
                    onClick = onBackClick,
                    modifier = Modifier
                        .size(52.dp)
                        .background(Color(0xFF925A35), RoundedCornerShape(16.dp))
                        .border(3.dp, Color(0xFFF6CE72), RoundedCornerShape(16.dp))
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Volver atrás",
                        tint = Color.White
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
            } else {
                // Logo corporativo por defecto (Pantalla Principal)
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
            }

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = titleTop.uppercase(),
                    color = Color(0xFFF8DFAB),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Black,
                    letterSpacing = 0.06.sp
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = titleMain,
                    color = Color.White,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = (-0.03).sp
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HeaderSeccionPrincipalPreview() {
    MiTallerCarpinteriaTheme {
        HeaderSeccion()
    }
}

@Preview(showBackground = true)
@Composable
fun HeaderSeccionConVolverPreview() {
    MiTallerCarpinteriaTheme {
        HeaderSeccion(
            titleTop = "4 PROYECTOS GUARDADOS",
            titleMain = "Todos los trabajos",
            showBackButton = true,
            onBackClick = {}
        )
    }
}