package com.carpinteria.mitallercarpinteria.ui.vista.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.carpinteria.mitallercarpinteria.data.model.ClienteDTO
import com.carpinteria.mitallercarpinteria.ui.viewModels.vm.TallerViewModel
import com.carpinteria.mitallercarpinteria.ui.vista.paginas.PaginaPrincipal

// Importar aquí páginas cuando las vaya creando:
// ej: import com.carpinteria.mitallercarpinteria.ui.vista.paginas.PaginaEliminarEncargp

object Routes {
    const val principal = "principal"
    const val lista = "lista"
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
                    navController.navigate(Routes.lista)
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

        // Pantalla de Lista de Proyectos
        composable(Routes.lista) {
            // Aquí llamar a pantalla de lista completa
            // ej: PaginaProyectos(onBackClick = { navController.popBackStack() })
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