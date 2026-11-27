package com.carpinteria.mitallercarpinteria.ui.vista.navigation

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.carpinteria.mitallercarpinteria.data.model.ClienteDTO
import com.carpinteria.mitallercarpinteria.ui.componentes.ModoTarjetaTrabajo
import com.carpinteria.mitallercarpinteria.ui.viewModels.vm.TallerViewModel
import com.carpinteria.mitallercarpinteria.ui.viewModels.vm.TrabajosViewModel
import com.carpinteria.mitallercarpinteria.ui.vista.paginas.PaginaDetalleTrabajo
import com.carpinteria.mitallercarpinteria.ui.vista.paginas.PaginaFormularioTrabajoWizard
import com.carpinteria.mitallercarpinteria.ui.vista.paginas.PaginaListaTrabajos
import com.carpinteria.mitallercarpinteria.ui.vista.paginas.PaginaPrincipal
import com.carpinteria.mitallercarpinteria.ui.vista.paginas.PaginaVisorFoto

object Routes {
    const val principal = "principal"
    const val lista = "lista/{modo}"
    const val detalleProyecto = "detalleProyecto/{idProyecto}"
    const val wizard = "wizard?idProyecto={idProyecto}"
    const val visorFoto = "visorFoto?ruta={ruta}"

    fun irAWizard(idProyecto: Long? = null): String =
        if (idProyecto != null) "wizard?idProyecto=$idProyecto" else "wizard"

    fun irAVisorFoto(rutaFoto: String): String =
        "visorFoto?ruta=${Uri.encode(rutaFoto)}"
}

@Composable
fun TallerNavGraph(navController: NavHostController, viewModel: TallerViewModel) {

    val tallerUiState by viewModel.uiState.collectAsStateWithLifecycle()

    NavHost(navController = navController, startDestination = Routes.principal) {

        // 1. Pantalla Principal
        composable(Routes.principal) {
            val lifecycleOwner = LocalLifecycleOwner.current

            LaunchedEffect(Unit) {
                viewModel.cargarDatosTaller()
            }

            DisposableEffect(lifecycleOwner) {
                val observer = LifecycleEventObserver { _, event ->
                    if (event == Lifecycle.Event.ON_STOP) {
                        viewModel.limpiarAvisoEncargo()
                    }
                }
                lifecycleOwner.lifecycle.addObserver(observer)
                onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
            }

            PaginaPrincipal(
                viewModel = viewModel,
                onVerTodosClick = {
                    viewModel.limpiarAvisoEncargo()
                    navController.navigate("lista/${ModoTarjetaTrabajo.COMPLETA.name}")
                },
                onEditarTrabajosClick = {
                    viewModel.limpiarAvisoEncargo()
                    navController.navigate("lista/${ModoTarjetaTrabajo.SOLO_EDITAR.name}")
                },
                onEliminarTrabajosClick = {
                    viewModel.limpiarAvisoEncargo()
                    navController.navigate("lista/${ModoTarjetaTrabajo.SOLO_ELIMINAR.name}")
                },
                onClienteClick = { clienteObj ->
                    val cliente = clienteObj as? ClienteDTO
                    if (cliente != null) {
                        viewModel.limpiarAvisoEncargo()
                        viewModel.registrarActividadCliente(cliente.id)
                        navController.navigate("detalleProyecto/${cliente.id}")
                    }
                },
                onCreateProjectClick = {
                    viewModel.limpiarAvisoEncargo()
                    navController.navigate(Routes.irAWizard())
                }
            )
        }

        // 2. Pantalla Lista de Trabajos
        composable(
            route = Routes.lista,
            arguments = listOf(navArgument("modo") { type = NavType.StringType })
        ) { backStackEntry ->
            val modoString =
                backStackEntry.arguments?.getString("modo") ?: ModoTarjetaTrabajo.COMPLETA.name
            val modoActual = try {
                ModoTarjetaTrabajo.valueOf(modoString)
            } catch (_: IllegalArgumentException) {
                ModoTarjetaTrabajo.COMPLETA
            }

            val trabajosViewModel: TrabajosViewModel = viewModel()
            val lifecycleOwner = LocalLifecycleOwner.current

            DisposableEffect(lifecycleOwner) {
                val observer = LifecycleEventObserver { _, event ->
                    when (event) {
                        Lifecycle.Event.ON_RESUME -> trabajosViewModel.cargarClientes()
                        Lifecycle.Event.ON_STOP -> {
                            viewModel.limpiarAvisoEncargo()
                            trabajosViewModel.limpiarAvisoEliminacion()
                        }
                        else -> Unit
                    }
                }
                lifecycleOwner.lifecycle.addObserver(observer)
                onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
            }

            PaginaListaTrabajos(
                modoInicial = modoActual,
                viewModel = trabajosViewModel,
                avisoEncargo = tallerUiState.avisoEncargo,
                onLimpiarAvisoEncargo = viewModel::limpiarAvisoEncargo,
                onVolverClick = {
                    viewModel.limpiarAvisoEncargo()
                    trabajosViewModel.limpiarAvisoEliminacion()
                    if (navController.currentDestination?.route == Routes.lista) {
                        navController.popBackStack()
                    }
                },
                onClienteSeleccionado = { cliente ->
                    viewModel.limpiarAvisoEncargo()
                    trabajosViewModel.limpiarAvisoEliminacion()
                    viewModel.registrarActividadCliente(cliente.id)
                    trabajosViewModel.registrarActividadCliente(cliente.id)
                    navController.navigate("detalleProyecto/${cliente.id}")
                },
                onEditarCliente = { cliente ->
                    viewModel.limpiarAvisoEncargo()
                    trabajosViewModel.limpiarAvisoEliminacion()
                    viewModel.registrarActividadCliente(cliente.id)
                    trabajosViewModel.registrarActividadCliente(cliente.id)
                    navController.navigate(Routes.irAWizard(cliente.id))
                }
            )
        }

        // 3. Pantalla Detalle de Trabajo
        composable(
            route = Routes.detalleProyecto,
            arguments = listOf(navArgument("idProyecto") { type = NavType.LongType })
        ) { backStackEntry ->
            val idProyecto = backStackEntry.arguments?.getLong("idProyecto") ?: return@composable
            val clienteSeleccionado = tallerUiState.clientes.find { it.id == idProyecto }
            val lifecycleOwner = LocalLifecycleOwner.current

            LaunchedEffect(idProyecto) {
                viewModel.registrarActividadCliente(idProyecto)
            }

            DisposableEffect(lifecycleOwner) {
                val observer = LifecycleEventObserver { _, event ->
                    if (event == Lifecycle.Event.ON_STOP) {
                        viewModel.limpiarAvisoEncargo()
                    }
                }
                lifecycleOwner.lifecycle.addObserver(observer)
                onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
            }

            PaginaDetalleTrabajo(
                cliente = clienteSeleccionado,
                avisoEncargo = tallerUiState.avisoEncargo,
                onLimpiarAvisoEncargo = viewModel::limpiarAvisoEncargo,
                onVolverClick = {
                    viewModel.limpiarAvisoEncargo()
                    if (navController.currentDestination?.route == Routes.detalleProyecto) {
                        navController.popBackStack()
                    }
                },
                onEditarClick = { cliente ->
                    viewModel.limpiarAvisoEncargo()
                    viewModel.registrarActividadCliente(cliente.id)
                    navController.navigate(Routes.irAWizard(cliente.id))
                },
                onAmpliarFotoClick = { rutaFoto ->
                    viewModel.limpiarAvisoEncargo()
                    navController.navigate(Routes.irAVisorFoto(rutaFoto))
                }
            )
        }

        // 4. Wizard en 3 pasos (Crear y Editar)
        composable(
            route = Routes.wizard,
            arguments = listOf(
                navArgument("idProyecto") {
                    type = NavType.LongType
                    defaultValue = -1L
                }
            )
        ) { backStackEntry ->
            val idProyecto = backStackEntry.arguments?.getLong("idProyecto") ?: -1L
            val esEdicion = idProyecto != -1L
            val clienteInicial = if (esEdicion) {
                tallerUiState.clientes.find { it.id == idProyecto }
            } else {
                null
            }

            PaginaFormularioTrabajoWizard(
                clienteInicial = clienteInicial,
                onCancelarClick = {
                    if (navController.currentDestination?.route == Routes.wizard) {
                        navController.popBackStack()
                    }
                },
                onGuardadoExitoso = {
                    if (navController.currentDestination?.route == Routes.wizard) {
                        val tituloAviso = clienteInicial?.titulosEncargos?.firstOrNull()
                            ?: clienteInicial?.nombre
                        viewModel.notificarGuardadoEncargo(
                            esEdicion = esEdicion,
                            detalle = tituloAviso,
                            idClienteAfectado = clienteInicial?.id
                        )
                        navController.popBackStack()
                    }
                },
                onAmpliarFotoClick = { rutaFoto ->
                    viewModel.limpiarAvisoEncargo()
                    navController.navigate(Routes.irAVisorFoto(rutaFoto))
                }
            )
        }

        // 5. Pantalla Visor de Fotografía Ampliada
        composable(
            route = Routes.visorFoto,
            arguments = listOf(
                navArgument("ruta") {
                    type = NavType.StringType
                    defaultValue = ""
                }
            )
        ) { backStackEntry ->
            val rutaCodificada = backStackEntry.arguments?.getString("ruta").orEmpty()
            val rutaDecodificada = Uri.decode(rutaCodificada)

            PaginaVisorFoto(
                rutaFoto = rutaDecodificada,
                onCerrarClick = {
                    if (navController.currentDestination?.route == Routes.visorFoto) {
                        navController.popBackStack()
                    }
                }
            )
        }
    }
}