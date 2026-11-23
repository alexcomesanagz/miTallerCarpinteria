package com.carpinteria.mitallercarpinteria.ui.vista.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.carpinteria.mitallercarpinteria.data.model.ClienteDTO
import com.carpinteria.mitallercarpinteria.ui.componentes.ModoTarjetaTrabajo
import com.carpinteria.mitallercarpinteria.ui.viewModels.vm.TallerViewModel
import com.carpinteria.mitallercarpinteria.ui.vista.paginas.PaginaDetalleTrabajo
import com.carpinteria.mitallercarpinteria.ui.vista.paginas.PaginaListaTrabajos
import com.carpinteria.mitallercarpinteria.ui.vista.paginas.PaginaPrincipal
import java.lang.Long.parseLong

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

    // Observam el estado global del taller para poder buscar cualquier cliente por su ID en el detalle
    val tallerUiState by viewModel.uiState.collectAsStateWithLifecycle()

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
                    if(cliente.id != null){
                        navController.navigate("detalleProyecto/${cliente.id}")
                    }
                },
                // Si pulsa el botón de editar desde la lista en modo SOLO_EDITAR
                onEditarCliente = {cliente ->
                    if(cliente.id != null){
                        navController.navigate("detalleProyecto/${cliente.id}")
                    }
                }
            )
        }

        // Pantalla de Detalle de un Trabajo (con argumento ID)
        composable(
            route = Routes.detalleProyecto,
            arguments = listOf(navArgument("idProyecto") { type = NavType.StringType })
        ) { backStackEntry ->
            val idProyecto = backStackEntry.arguments?.getString("idProyecto") ?: return@composable

            // Recupera el cliente seleccionado por su ID desde el estado del ViewModel
            val clienteSeleccionado = tallerUiState.clientes.find { it.id == parseLong(idProyecto) }

            PaginaDetalleTrabajo(
                cliente = clienteSeleccionado,
                onVolverClick = {
                    // Retrocede automáticamente a la pantalla anterior en la pila
                    navController.popBackStack()
                })
            /*,
                onEditarClick = { cliente ->
                    // Aquí conectarás la ruta de edición cuando la tengas lista
                    })*/
            // Aquí llamar a pantalla de detalle pasando el idProyecto
            // PantallaEncargoDetalles(idProyecto = idProyecto, onBackClick = { navController.popBackStack() })
        }
    }
}