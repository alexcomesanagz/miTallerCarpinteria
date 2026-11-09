package com.carpinteria.mitallercarpinteria.ui.componentes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.carpinteria.mitallercarpinteria.ui.theme.MiTallerCarpinteriaTheme
import com.carpinteria.mitallercarpinteria.ui.theme.TextDarkBrown

@Composable
fun SeccionBusquedaYAviso(
    modifier: Modifier = Modifier,
    tituloBusqueda: String = "Buscar un trabajo",
    onBuscarChange: (String) -> Unit = {},
    // Permite pasar cualquier componente opcional abajo (por defecto usa el ToolTip de aviso)
    Tooltip: @Composable () -> Unit = { TarjetaAvisoInstruccion() }
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Text(
            text = tituloBusqueda,
            fontSize = 18.sp,
            fontWeight = FontWeight.Black,
            color = TextDarkBrown
        )

        // Subcomponente búsqueda
        BarraBusquedaProyecto(
            // onQueryChange = onBuscarChange
        )

        // Contenido inferior (ToolTip de aviso)
        Tooltip()
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFF8E8)
@Composable
fun SeccionBusquedaYAvisoPreview() {
    MiTallerCarpinteriaTheme {
        SeccionBusquedaYAviso()
    }
}