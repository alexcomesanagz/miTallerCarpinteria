package com.carpinteria.mitallercarpinteria

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.carpinteria.mitallercarpinteria.ui.theme.MiTallerCarpinteriaTheme
import com.carpinteria.mitallercarpinteria.ui.viewModels.vm.TallerViewModel
import com.carpinteria.mitallercarpinteria.ui.vista.navigation.TallerNavGraph
import com.carpinteria.mitallercarpinteria.ui.vista.paginas.PaginaPrincipal

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MiTallerCarpinteriaTheme {
                val navController = rememberNavController()
                val tallerViewModel: TallerViewModel = viewModel()

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        TallerNavGraph(navController = navController, viewModel = tallerViewModel)
                    }
                }
            }
        }
    }
}

/*
/
/ LA PREVIEW NO FUNCIONA Y NO SE EL PQ
/
*/

/*
@Preview(showBackground = true, backgroundColor = 0xFFFFF8E8)
@Composable
fun MainActivityPreview() {
    MiTallerCarpinteriaTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize()
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                // En vez de arrancar el NavHost en la preview, renderizamos
                // directamente la PaginaPrincipal para ver el diseño final al instante.
                PaginaPrincipal()
            }
        }
    }
}
*/
