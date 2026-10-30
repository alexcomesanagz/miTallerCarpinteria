package com.carpinteria.mitallercarpinteria.ui.vista.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.carpinteria.mitallercarpinteria.ui.viewModels.vm.TallerViewModel
import com.carpinteria.mitallercarpinteria.ui.vista.paginas.PaginaPrincipal

// Importar aquí páginas cuando las vaya creando:
// ej: import com.carpinteria.mitallercarpinteria.ui.vista.paginas.PaginaEliminarEncargp

object TallerRoutes {
    const val home = "home"
    const val allProjects = "all_projects"
    const val projectDetail = "project_detail/{idProyecto}"

    // Función auxiliar para pasar el ID del proyecto de forma limpia
    fun createProjectDetailRoute(idProyecto: String) = "project_detail/$idProyecto"
}

/**
 * Componente de navegación principal para miTallerCarpinteria
 * @param navController Controlador que gestiona la navegación entre pantallas.
 * @param viewModel ViewModel principal del taller para controlar estados globales o de la barra de navegación.
 */
@Composable
fun TallerNavGraph(navController: NavHostController, viewModel: TallerViewModel) {

    NavHost(navController = navController, startDestination = TallerRoutes.home) {

        // Pantalla Principal (Home)
        composable(TallerRoutes.home) {
            PaginaPrincipal(
                viewModel = viewModel,
                onVerTodosClick = {
                    navController.navigate(TallerRoutes.allProjects)
                },
                onClienteClick = { cliente ->
                    // Navega al detalle usando el ID del cliente o proyecto
                    navController.navigate(TallerRoutes.createProjectDetailRoute(cliente.id.toString()))
                }
            )
        }

        // Pantalla "Ver todos los proyectos"
        composable(TallerRoutes.allProjects) {
            // Aquí llamar a pantalla de lista completa
            // ej: PaginaProyectos(onBackClick = { navController.popBackStack() })
        }

        // Pantalla de Detalle de un Proyecto/Trabajo (con argumento ID)
        composable(
            route = TallerRoutes.projectDetail,
            arguments = listOf(navArgument("idProyecto") { type = NavType.StringType })
        ) { backStackEntry ->
            val idProyecto = backStackEntry.arguments?.getString("idProyecto") ?: return@composable

            // Aquí llamar a pantalla de detalle pasando el idProyecto
            // PantallaEncargoDetalles(idProyecto = idProyecto, onBackClick = { navController.popBackStack() })
        }
    }
}