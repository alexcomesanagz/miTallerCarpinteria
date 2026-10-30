package com.carpinteria.mitallercarpinteria.ui.componentes

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
fun ProjectList(
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
                    titulo = cliente.titulosEncargos ?: "Sin nombre",
                    cliente = cliente.nombre ?: "",
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
fun ProjectListPreview() {
    MiTallerCarpinteriaTheme {
        Column(modifier = Modifier.padding(16.dp)) {
            ProjectList(
                clientes = listOf(
                    ClienteDTO().apply {
                        // Asignamos datos de prueba si tu DTO lo permite, o simulamos con un mock
                    }
                ),
                onClienteClick = {}
            )
        }
    }
}