package com.carpinteria.mitallercarpinteria.ui.componentes

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.carpinteria.mitallercarpinteria.ui.theme.CardBorder
import com.carpinteria.mitallercarpinteria.ui.theme.MiTallerCarpinteriaTheme
import com.carpinteria.mitallercarpinteria.ui.theme.MutedBrown
import com.carpinteria.mitallercarpinteria.ui.theme.RedAlertText
import com.carpinteria.mitallercarpinteria.ui.theme.TextDarkBrown

@Composable
fun BarraBusquedaProyecto(
    query: String = "",
    onQueryChange: (String) -> Unit = {},
    onClearQuery: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        singleLine = true,
        placeholder = {
            Text(
                text = "Nombre del trabajo o cliente",
                color = MutedBrown,
                fontWeight = FontWeight.Bold
            )
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Buscar",
                tint = TextDarkBrown
            )
        },
        trailingIcon = {
            if (query.isNotEmpty()) {
                IconButton(onClick = onClearQuery) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Borrar búsqueda",
                        tint = RedAlertText
                    )
                }
            }
        },
        modifier = modifier
            .fillMaxWidth()
            .height(60.dp),
        shape = RoundedCornerShape(16.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            focusedBorderColor = TextDarkBrown,
            unfocusedBorderColor = CardBorder,
            focusedTextColor = TextDarkBrown,
            unfocusedTextColor = TextDarkBrown,
            cursorColor = TextDarkBrown
        )
    )
}

@Preview(showBackground = true, backgroundColor = 0xFFFFF8E8)
@Composable
fun BarraBusquedaProyectoPreview() {
    MiTallerCarpinteriaTheme {
        BarraBusquedaProyecto()
    }
}