package com.carpinteria.mitallercarpinteria.ui.componentes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.carpinteria.mitallercarpinteria.ui.theme.*

@Composable
fun CampoTextoTaller(
    etiqueta: String,
    valor: String,
    onValorChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    error: String? = null,
    singleLine: Boolean = true,
    minLines: Int = 1,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    val tieneError = !error.isNullOrBlank()
    val bordeColor = if (tieneError) Color(0xFFB7322C) else CardBorder
    val fondoColor = if (tieneError) Color(0xFFFFF0ED) else CardBackground

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Text(
            text = etiqueta,
            fontSize = 14.sp,
            fontWeight = FontWeight.Black,
            color = if (tieneError) Color(0xFF771915) else TextDarkBrown
        )

        OutlinedTextField(
            value = valor,
            onValueChange = onValorChange,
            placeholder = {
                if (placeholder.isNotBlank()) {
                    Text(
                        text = placeholder,
                        color = MutedBrown.copy(alpha = 0.75f),
                        fontSize = 14.sp
                    )
                }
            },
            isError = tieneError,
            singleLine = singleLine,
            minLines = minLines,
            maxLines = maxLines,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = fondoColor,
                unfocusedContainerColor = fondoColor,
                errorContainerColor = fondoColor,
                focusedBorderColor = bordeColor,
                unfocusedBorderColor = bordeColor,
                errorBorderColor = bordeColor,
                focusedTextColor = TextDarkBrown,
                unfocusedTextColor = TextDarkBrown,
                errorTextColor = TextDarkBrown,
                cursorColor = WoodPrimary
            )
        )

        if (tieneError) {
            Text(
                text = "Atención: $error",
                color = MaterialTheme.colorScheme.error,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFF8E8, name = "Campo Texto Normal y Error")
@Composable
fun CampoTextoTallerPreview() {
    MiTallerCarpinteriaTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            CampoTextoTaller(
                etiqueta = "Título del trabajo",
                valor = "Armario de roble",
                onValorChange = {},
                placeholder = "Ej.: Mesa de comedor"
            )
            CampoTextoTaller(
                etiqueta = "Teléfono",
                valor = "",
                onValorChange = {},
                placeholder = "Ej.: 600 000 000",
                error = "Escriba un teléfono de contacto",
                keyboardType = KeyboardType.Phone
            )
        }
    }
}