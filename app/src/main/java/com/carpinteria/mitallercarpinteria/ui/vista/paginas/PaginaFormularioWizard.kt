package com.carpinteria.mitallercarpinteria.ui.vista.paginas

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.carpinteria.mitallercarpinteria.data.model.ClienteDTO
import com.carpinteria.mitallercarpinteria.ui.theme.MiTallerCarpinteriaTheme
import com.carpinteria.mitallercarpinteria.ui.viewModels.vm.WizardTrabajoViewModel
import com.carpinteria.mitallercarpinteria.ui.vista.navigation.ScaffoldPasoWizard
import java.io.File
import java.io.FileOutputStream
import java.util.UUID

private fun copiarUriAAlmacenamientoInterno(context: Context, uri: Uri): String? {
    return try {
        val dirFotos = File(context.filesDir, "fotos_encargos").apply { mkdirs() }
        val archivoDestino = File(dirFotos, "foto_${UUID.randomUUID()}.jpg")
        context.contentResolver.openInputStream(uri)?.use { input ->
            FileOutputStream(archivoDestino).use { output ->
                input.copyTo(output)
            }
        }
        archivoDestino.absolutePath
    } catch (_: Exception) {
        null
    }
}

@Composable
fun PaginaFormularioTrabajoWizard(
    clienteInicial: ClienteDTO? = null,
    viewModel: WizardTrabajoViewModel = viewModel(),
    onCancelarClick: () -> Unit = {},
    onGuardadoExitoso: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    val selectorFotosLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia()
    ) { uris: List<Uri> ->
        if (uris.isNotEmpty()) {
            val rutasCopiadas = uris.mapNotNull { uri ->
                copiarUriAAlmacenamientoInterno(context, uri)
            }
            viewModel.agregarRutasFotos(rutasCopiadas)
        }
    }

    LaunchedEffect(clienteInicial) {
        viewModel.inicializar(clienteInicial)
    }

    ScaffoldPasoWizard(
        paso = uiState.pasoActual,
        esEdicion = uiState.esEdicion,
        onAtrasClick = { viewModel.retrocederPaso(onSalirWizard = onCancelarClick) },
        onSiguienteOGuardarClick = { viewModel.avanzarPasoOGuardar(onGuardadoExitoso = onGuardadoExitoso) }
    ) {
        when (uiState.pasoActual) {
            1 -> PaginaPaso1Cliente(
                estado = uiState.formulario,
                errorTitulo = uiState.errorTitulo,
                errorCliente = uiState.errorCliente,
                errorTelefono = uiState.errorTelefono,
                onTituloChange = viewModel::onTituloChange,
                onClienteChange = viewModel::onClienteChange,
                onTelefonoChange = viewModel::onTelefonoChange,
                onEstadoChange = viewModel::onEstadoChange
            )

            2 -> PaginaPaso2Detalles(
                mediciones = uiState.formulario.mediciones,
                materialesTexto = uiState.formulario.materialesTexto,
                onMedicionesChange = viewModel::onMedicionesChange,
                onMaterialesChange = viewModel::onMaterialesChange
            )

            3 -> PaginaPaso3Fotos(
                estado = uiState.formulario,
                onAdjuntarFotoClick = {
                    selectorFotosLauncher.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                    )
                },
                onEliminarFotoClick = viewModel::eliminarFotoEnIndice
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFF8E8)
@Composable
fun PaginaFormularioTrabajoWizardPreview() {
    MiTallerCarpinteriaTheme {
        PaginaFormularioTrabajoWizard()
    }
}