package com.carpinteria.mitallercarpinteria.ui.componentes

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.carpinteria.mitallercarpinteria.data.model.ClienteDTO
import com.carpinteria.mitallercarpinteria.ui.theme.GreenStatusBg
import com.carpinteria.mitallercarpinteria.ui.theme.GreenStatusText
import com.carpinteria.mitallercarpinteria.ui.theme.MiTallerCarpinteriaTheme
import com.carpinteria.mitallercarpinteria.ui.theme.TextDarkBrown

@Composable
fun ListaTarjetasProyecto(
    clientes: List<ClienteDTO>,
    onClienteClick: (ClienteDTO) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(14.dp) // Espacio simétrico entre cada tarjeta
    ) {
        if (clientes.isEmpty()) {
            Text(
                text = "No hay proyectos recientes disponibles.",
                fontSize = 14.sp,
                color = TextDarkBrown.copy(alpha = 0.7f)
            )
        } else {
            clientes.forEach { cliente ->
                TarjetaProyecto(
                    titulo = cliente.titulosEncargos?.firstOrNull() ?: "Sin proyectos",
                    cliente = cliente.nombre ?: "Sin nombre",
                    estado = "Activo",
                    estadoBg = GreenStatusBg,
                    estadoTextColor = GreenStatusText,
                    onClick = {
                        Log.d("ProjectList", "Clic en cliente: ${cliente.nombre}")
                        onClienteClick(cliente)
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFF8E8)
@Composable
fun ListaTarjetasProyectoPreview() {
    MiTallerCarpinteriaTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            ListaTarjetasProyecto(
                clientes = listOf(
                    ClienteDTO(
                        id = 1L,
                        nombre = "Carmen Ruiz",
                        telefono = "600111222",
                        titulosEncargos = listOf("Armario de roble")
                    ),
                    ClienteDTO(
                        id = 2L,
                        nombre = "Manuel Ortega",
                        telefono = "600333444",
                        titulosEncargos = listOf("Mesa de comedor")
                    ),
                    ClienteDTO(
                        id = 3L,
                        nombre = "Pilar Gómez",
                        telefono = "600555666",
                        titulosEncargos = listOf("Puerta de entrada")
                    )
                ),
                onClienteClick = {}
            )
        }
    }
}