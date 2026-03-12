package com.example.apitest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.apitest.data.BaseDeDades
import com.example.apitest.ui.theme.ApiTestTheme
import com.example.apitest.viewModel.BDViewModel
import com.example.apitest.viewModel.BDViewModelFactory
import com.example.apitest.viewModel.PokemonViewModel2
import com.example.apitest.viewModel.PokemonViewModel3
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.apitest.data.SettingsRepository
import com.example.apitest.navigation.NavigationItem
import com.example.apitest.navigation.NavigationWrapper
import com.example.apitest.viewModel.SearchBarViewModel
import com.example.apitest.viewModel.SettingsViewModel
import com.example.apitest.viewModel.SettingsViewModelFactory


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*val viewModel = PokemonViewModel() // API
        viewModel.getEmolga()*/
        val database = BaseDeDades.getDatabase(this)
        val viewModel2 = PokemonViewModel2() // JSON
        viewModel2.getEmolga(this) // single item
        viewModel2.getPokemons(this) // multiple items

        enableEdgeToEdge()
        setContent {
            val bdViewModel: BDViewModel = viewModel(
                factory = BDViewModelFactory(database.funDAO())
            )
            val viewModel3 = remember {PokemonViewModel3(bdViewModel)} // Form
            val settingsRepository = remember { SettingsRepository(this) }
            val searchBarViewModel = remember { SearchBarViewModel(bdViewModel) }

            val settingsViewModel: SettingsViewModel =
                viewModel(
                    factory = SettingsViewModelFactory(settingsRepository)
                )
            val darkTheme = settingsViewModel.darkMode

            MaterialTheme(
                colorScheme = if (darkTheme)
                    darkColorScheme()
                else
                    lightColorScheme()
            ) {
                MyApp(searchBarViewModel, bdViewModel, viewModel3, settingsViewModel)
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ApiTestTheme {
        Greeting("Android")
    }
}

@Composable
fun MyApp(searchBarViewModel: SearchBarViewModel, bdViewModel: BDViewModel, viewModel3: PokemonViewModel3, settingsViewModel: SettingsViewModel){

    // Estat per saber quin ítem està seleccionat (0, 1 o 2)
    var selectedItem by remember { mutableIntStateOf(0) }

    // Creem el navController aquí, al nivell superior
    val navController = rememberNavController()

    // Llista d'opcions del menú
    val items = listOf(
        NavigationItem("Inici", Icons.Default.Home, "screen", 0),
        NavigationItem("Perfil", Icons.Default.Person, "screen2", 1),
        NavigationItem("Ajustos", Icons.Default.Settings, "screen3", 2)
    )

    // Scaffold que conté la BottomBar
    Scaffold(
        bottomBar = {
            NavigationBar {
                items.forEachIndexed { index, item -> // Bucle per crear cada ítem
                    NavigationBarItem(
                        selected = item.index == selectedItem, // Marquem si està seleccionat
                        label = { Text(item.label) },
                        icon = {
                            Icon(imageVector = item.icon, contentDescription = item.label)
                        },
                        onClick = {
                            selectedItem = index // Actualitzem l'estat visual
                            navController.navigate(item.route) { // Naveguem a la ruta
                                // Opcional: Evitar múltiples còpies de la mateixa pantalla a la pila
                                launchSingleTop = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        // Important: Passem el padding al contingut perquè la barra no tapi la pantalla
        Box(modifier = Modifier.padding(innerPadding)) {
            // Carreguem el NavigationWrapper passant-li el controlador
            NavigationWrapper(navController, searchBarViewModel, bdViewModel, viewModel3, settingsViewModel)
        }
    }
}