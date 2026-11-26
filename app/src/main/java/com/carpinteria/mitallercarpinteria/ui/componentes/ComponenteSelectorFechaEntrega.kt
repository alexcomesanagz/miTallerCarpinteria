package com.carpinteria.mitallercarpinteria.ui.componentes

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.carpinteria.mitallercarpinteria.ui.theme.*
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectorFechaEntrega(
    fechaEntrega: String,
    onFechaSeleccionada: (String) -> Unit,
    modifier: Modifier = Modifier,
    etiqueta: String = "FECHA DE ENTREGA ESTIMADA",
    fechaCreacion: String = ""
) {
    var mostrarDatePicker by remember { mutableStateOf(false) }
    val formatter = remember { DateTimeFormatter.ofPattern("yyyy-MM-dd") }

    val fechaMinima = remember(fechaCreacion) {
        try {
            if (fechaCreacion.isNotBlank()) LocalDate.parse(fechaCreacion, formatter)
            else LocalDate.now()
        } catch (_: Exception) {
            LocalDate.now()
        }
    }

    val millisMinimos = remember(fechaMinima) {
        fechaMinima.atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli()
    }

    val millisIniciales = remember(fechaEntrega, millisMinimos) {
        try {
            if (fechaEntrega.isNotBlank()) {
                val parsed = LocalDate.parse(fechaEntrega, formatter)
                val millis = parsed.atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli()
                maxOf(millis, millisMinimos)
            } else {
                millisMinimos
            }
        } catch (_: Exception) {
            millisMinimos
        }
    }

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .clickable { mostrarDatePicker = true },
        shape = RoundedCornerShape(14.dp),
        color = FondoCrema,
        border = BorderStroke(2.dp, CardBorder)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Text(
                    text = etiqueta,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Black,
                    color = WoodPrimary
                )
                Text(
                    text = fechaEntrega.ifBlank { "Tocar para seleccionar fecha" },
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (fechaEntrega.isBlank()) MutedBrown else TextDarkBrown
                )
            }

            Surface(
                shape = RoundedCornerShape(10.dp),
                color = YellowButtonBg,
                border = BorderStroke(1.5.dp, YellowButtonDark)
            ) {
                Icon(
                    imageVector = Icons.Outlined.CalendarMonth,
                    contentDescription = "Seleccionar fecha de entrega",
                    tint = TextDarkBrown,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }

    if (mostrarDatePicker) {
        val datePickerState = rememberDatePickerState(
            initialSelectedDateMillis = millisIniciales,
            selectableDates = object : SelectableDates {
                override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                    return utcTimeMillis >= millisMinimos
                }

                override fun isSelectableYear(year: Int): Boolean {
                    return year >= fechaMinima.year
                }
            }
        )

        DatePickerDialog(
            onDismissRequest = { mostrarDatePicker = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        datePickerState.selectedDateMillis?.let { millis ->
                            if (millis >= millisMinimos) {
                                val fechaElegida = Instant.ofEpochMilli(millis)
                                    .atZone(ZoneOffset.UTC)
                                    .toLocalDate()
                                    .format(formatter)
                                onFechaSeleccionada(fechaElegida)
                            }
                        }
                        mostrarDatePicker = false
                    }
                ) {
                    Text(
                        text = "Aceptar",
                        fontWeight = FontWeight.Black,
                        color = WoodPrimary
                    )
                }
            },
            dismissButton = {
                TextButton(onClick = { mostrarDatePicker = false }) {
                    Text(
                        text = "Cancelar",
                        fontWeight = FontWeight.Bold,
                        color = MutedBrown
                    )
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFF8E8, name = "Selector fecha - Vacío")
@Composable
fun SelectorFechaEntregaVacioPreview() {
    MiTallerCarpinteriaTheme {
        Box(modifier = Modifier.padding(16.dp)) {
            SelectorFechaEntrega(
                fechaEntrega = "",
                fechaCreacion = "2026-11-25",
                onFechaSeleccionada = {}
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFF8E8, name = "Selector fecha - Con fecha")
@Composable
fun SelectorFechaEntregaConFechaPreview() {
    MiTallerCarpinteriaTheme {
        Box(modifier = Modifier.padding(16.dp)) {
            SelectorFechaEntrega(
                fechaEntrega = "2026-12-15",
                fechaCreacion = "2026-11-25",
                onFechaSeleccionada = {}
            )
        }
    }
}

