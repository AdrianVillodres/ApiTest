package com.example.apitest.navigation

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.apitest.model.Pokemon
import com.example.apitest.ui.screens.DetailScreen
import com.example.apitest.ui.screens.Screen
import com.example.apitest.ui.screens.Screen2
import com.example.apitest.ui.screens.Screen3
import com.example.apitest.viewModel.BDViewModel
import com.example.apitest.viewModel.PokemonViewModel3
import com.example.apitest.viewModel.SearchBarViewModel
import com.example.apitest.viewModel.SettingsViewModel

@Composable
fun NavigationWrapper(
    navController: NavHostController,
    searchBarViewModel: SearchBarViewModel,
    bdViewModel: BDViewModel,
    viewModel3: PokemonViewModel3,
    settingsViewModel: SettingsViewModel
) {

    NavHost(
        navController = navController,
        startDestination = "screen"
    ) {

        // Pantalla1
        composable("screen") {
            Screen(
                viewModel = viewModel3
            )
        }

        // Pantalla2
        composable("screen2") {
            Screen2(
                viewModel = searchBarViewModel,
                bdViewModel = bdViewModel,
                settingsViewModel = settingsViewModel,
                onNavegarAlDetall = { pokemon ->
                    navController.navigate(
                        "detail/${pokemon.name}/${pokemon.height}/${pokemon.weight}"
                    )
                }
            )
        }

        // Pantalla3
        composable("screen3") {
            Screen3(settingsViewModel, bdViewModel)
        }

        // Detalle
        composable(
            "detail/{name}/{height}/{weight}"
        ) { backStackEntry ->

            val name = backStackEntry.arguments?.getString("name") ?: ""
            val height = backStackEntry.arguments?.getString("height")?.toInt() ?: 0
            val weight = backStackEntry.arguments?.getString("weight")?.toInt() ?: 0

            val pokemon = Pokemon(
                id = 0,
                name = name,
                height = height,
                weight = weight
            )

            DetailScreen(
                item = pokemon,
                onBack = { navController.popBackStack() }
            )
        }
    }
}