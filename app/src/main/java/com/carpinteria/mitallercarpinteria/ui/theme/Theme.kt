package com.carpinteria.mitallercarpinteria.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = DarkBrownHeader,
    secondary = YellowButtonBg,
    tertiary = CardBorder
)

private val LightColorScheme = lightColorScheme(
    primary = DarkBrownHeader,
    secondary = YellowButtonBg,
    tertiary = CardBorder,
    background = FondoCrema,
    surface = CardBackground,
    onPrimary = androidx.compose.ui.graphics.Color.White,
    onSecondary = TextDarkBrown,
    onBackground = TextDarkBrown,
    onSurface = TextDarkBrown
)

@Composable
fun MiTallerCarpinteriaTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false, // Desactivado por defecto para mantener los colores de la carpintería
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}