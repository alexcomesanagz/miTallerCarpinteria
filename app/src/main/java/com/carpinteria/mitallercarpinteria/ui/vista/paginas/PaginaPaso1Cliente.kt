package com.carpinteria.mitallercarpinteria.ui.vista.paginas

import com.carpinteria.mitallercarpinteria.ui.viewModels.Uis.EstadoFormularioTrabajo
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.carpinteria.mitallercarpinteria.ui.componentes.*
import com.carpinteria.mitallercarpinteria.ui.theme.*

@Composable
fun SeccionDatosPrincipalesPaso1(
    estado: EstadoFormularioTrabajo,
    errorTitulo: String? = null,
    errorCliente: String? = null,
    errorTelefono: String? = null,
    onTituloChange: (String) -> Unit,
    onClienteChange: (String) -> Unit,
    onTelefonoChange: (String) -> Unit,
    onEstadoChange: (String) -> Unit,
    onFechaEntregaChange: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(
                text = "Datos principales",
                fontSize = 22.sp,
                fontWeight = FontWeight.Black,
                color = WoodPrimary
            )
            Text(
                text = "Complete los datos básicos del trabajo.",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = MutedBrown
            )
        }

        TarjetaContenedorDetalle {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                CampoTextoTaller(
                    etiqueta = "Título del trabajo",
                    valor = estado.tituloProyecto,
                    onValorChange = onTituloChange,
                    placeholder = "Ej.: Mesa de comedor"
                )
                errorTitulo?.let {
                    Text(
                        text = it,
                        color = MaterialTheme.colorScheme.error,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                CampoTextoTaller(
                    etiqueta = "Nombre del cliente",
                    valor = estado.nombreCliente,
                    onValorChange = onClienteChange,
                    placeholder = "Nombre y apellidos"
                )
                errorCliente?.let {
                    Text(
                        text = it,
                        color = MaterialTheme.colorScheme.error,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                CampoTextoTaller(
                    etiqueta = "Teléfono",
                    valor = estado.telefonoCliente,
                    onValorChange = onTelefonoChange,
                    placeholder = "Ej.: 600 000 000",
                    keyboardType = KeyboardType.Phone
                )
                errorTelefono?.let {
                    Text(
                        text = it,
                        color = MaterialTheme.colorScheme.error,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        SelectorFechaEntrega(
            fechaEntrega = estado.fechaEntregaEstimada,
            fechaCreacion = estado.fechaCreacion,
            onFechaSeleccionada = onFechaEntregaChange
        )

        SeccionEstadoTrabajo(
            estadoActual = estado.estadoTrabajo,
            onEstadoSeleccionado = onEstadoChange
        )

    }
}

@Composable
fun PaginaPaso1Cliente(
    estado: EstadoFormularioTrabajo,
    errorTitulo: String? = null,
    errorCliente: String? = null,
    errorTelefono: String? = null,
    onTituloChange: (String) -> Unit,
    onClienteChange: (String) -> Unit,
    onTelefonoChange: (String) -> Unit,
    onEstadoChange: (String) -> Unit,
    onFechaEntregaChange: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    SeccionDatosPrincipalesPaso1(
        estado = estado,
        errorTitulo = errorTitulo,
        errorCliente = errorCliente,
        errorTelefono = errorTelefono,
        onTituloChange = onTituloChange,
        onClienteChange = onClienteChange,
        onTelefonoChange = onTelefonoChange,
        onEstadoChange = onEstadoChange,
        onFechaEntregaChange = onFechaEntregaChange,
        modifier = modifier
    )
}

@Preview(showBackground = true, backgroundColor = 0xFFFFF8E8)
@Composable
fun PaginaPaso1ClientePreview() {
    MiTallerCarpinteriaTheme {
        Box(modifier = Modifier.padding(16.dp)) {
            PaginaPaso1Cliente(
                estado = EstadoFormularioTrabajo(),
                onTituloChange = {},
                onClienteChange = {},
                onTelefonoChange = {},
                onEstadoChange = {},
                onFechaEntregaChange = {}
            )
        }
    }
}