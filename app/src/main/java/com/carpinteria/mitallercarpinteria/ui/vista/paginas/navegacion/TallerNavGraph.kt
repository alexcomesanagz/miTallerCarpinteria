package com.carpinteria.mitallercarpinteria.ui.vista.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.carpinteria.mitallercarpinteria.data.model.ClienteDTO
import com.carpinteria.mitallercarpinteria.ui.componentes.ModoTarjetaTrabajo
import com.carpinteria.mitallercarpinteria.ui.viewModels.vm.TallerViewModel
import com.carpinteria.mitallercarpinteria.ui.vista.paginas.PaginaListaTrabajos
import com.carpinteria.mitallercarpinteria.ui.vista.paginas.PaginaPrincipal

object Routes {
    const val principal = "principal"
    const val lista = "lista/{modo}" // Ruta parametrizada para el modo (COMPLETA, SOLO_ELIMINAR, SOLO_EDITAR)
    const val detalleProyecto = "detalleProyecto/{idProyecto}"
}

/**
 * Componente de navegación principal para miTallerCarpinteria
 * @param navController Controlador que gestiona la navegación entre pantallas.
 * @param viewModel ViewModel principal del taller para controlar estados globales o de la barra de navegación.
 */
@Composable
fun TallerNavGraph(navController: NavHostController, viewModel: TallerViewModel) {

    NavHost(navController = navController, startDestination = Routes.principal) {

        // Pantalla Principal
        composable(Routes.principal) {
            viewModel.cargarDatosTaller()
            PaginaPrincipal(
                viewModel = viewModel,
                onVerTodosClick = {
                    // Navega a la lista en modo completo (Ver/Acceder)
                    navController.navigate("lista/${ModoTarjetaTrabajo.COMPLETA.name}")
                },
                onEditarTrabajosClick = {
                    navController.navigate("lista/${ModoTarjetaTrabajo.SOLO_EDITAR.name}")
                },
                onEliminarTrabajosClick = {
                    // Navega a la lista en modo eliminar (Papelera roja)
                    navController.navigate("lista/${ModoTarjetaTrabajo.SOLO_ELIMINAR.name}")
                },
                onClienteClick = { clienteObj ->
                    // Hace el cast seguro a ClienteDTO para extraer su id real
                    val cliente = clienteObj as? ClienteDTO
                    if (cliente != null) {
                        navController.navigate("detalleProyecto/${cliente.id}")
                    }
                }
            )
        }

        //Página lista trabajos
        composable(
            route = Routes.lista,
            arguments = listOf(navArgument("modo") { type = NavType.StringType })
        ) { backStackEntry ->
            val modoString = backStackEntry.arguments?.getString("modo") ?: ModoTarjetaTrabajo.COMPLETA.name
            val modoActual = try {
                ModoTarjetaTrabajo.valueOf(modoString)
            } catch (e: IllegalArgumentException) {
                ModoTarjetaTrabajo.COMPLETA
            }

            PaginaListaTrabajos(
                modoInicial = modoActual,
                onVolverClick = { navController.popBackStack() },
                onClienteSeleccionado = { cliente ->
                    navController.navigate("detalleProyecto/${cliente.id}")
                }
            )
        }

        // Pantalla de Detalle de un Proyecto/Trabajo (con argumento ID)
        composable(
            route = Routes.detalleProyecto,
            arguments = listOf(navArgument("idProyecto") { type = NavType.StringType })
        ) { backStackEntry ->
            val idProyecto = backStackEntry.arguments?.getString("idProyecto") ?: return@composable

            // Aquí llamar a pantalla de detalle pasando el idProyecto
            // PantallaEncargoDetalles(idProyecto = idProyecto, onBackClick = { navController.popBackStack() })
        }
    }
}