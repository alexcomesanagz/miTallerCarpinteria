package com.carpinteria.mitallercarpinteria.ui.vista.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
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

object Routes {
    const val principal = "principal"
    const val lista = "lista/{modo}"
    const val detalleProyecto = "detalleProyecto/{idProyecto}"
    const val wizard = "wizard?idProyecto={idProyecto}"

    fun irAWizard(idProyecto: Long? = null): String =
        if (idProyecto != null) "wizard?idProyecto=$idProyecto" else "wizard"
}

@Composable
fun TallerNavGraph(navController: NavHostController, viewModel: TallerViewModel) {

    val tallerUiState by viewModel.uiState.collectAsStateWithLifecycle()

    NavHost(navController = navController, startDestination = Routes.principal) {

        // 1. Pantalla Principal
        composable(Routes.principal) {
            LaunchedEffect(Unit) {
                viewModel.cargarDatosTaller()
            }

            PaginaPrincipal(
                viewModel = viewModel,
                onVerTodosClick = {
                    navController.navigate("lista/${ModoTarjetaTrabajo.COMPLETA.name}")
                },
                onEditarTrabajosClick = {
                    navController.navigate("lista/${ModoTarjetaTrabajo.SOLO_EDITAR.name}")
                },
                onEliminarTrabajosClick = {
                    navController.navigate("lista/${ModoTarjetaTrabajo.SOLO_ELIMINAR.name}")
                },
                onClienteClick = { clienteObj ->
                    val cliente = clienteObj as? ClienteDTO
                    if (cliente != null) {
                        navController.navigate("detalleProyecto/${cliente.id}")
                    }
                },
                onCreateProjectClick = {
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
            LaunchedEffect(Unit) {
                trabajosViewModel.cargarClientes()
            }

            PaginaListaTrabajos(
                modoInicial = modoActual,
                viewModel = trabajosViewModel,
                onVolverClick = { navController.popBackStack() },
                onClienteSeleccionado = { cliente ->
                    navController.navigate("detalleProyecto/${cliente.id}")
                },
                onEditarCliente = { cliente ->
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

            PaginaDetalleTrabajo(
                cliente = clienteSeleccionado,
                onVolverClick = { navController.popBackStack() },
                onEditarClick = { cliente ->
                    navController.navigate(Routes.irAWizard(cliente.id))
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
            val clienteInicial = if (idProyecto != -1L) {
                tallerUiState.clientes.find { it.id == idProyecto }
            } else {
                null
            }

            PaginaFormularioTrabajoWizard(
                clienteInicial = clienteInicial,
                onCancelarClick = { navController.popBackStack() },
                onGuardadoExitoso = {
                    viewModel.cargarDatosTaller()
                    navController.popBackStack()
                }
            )
        }
    }
}