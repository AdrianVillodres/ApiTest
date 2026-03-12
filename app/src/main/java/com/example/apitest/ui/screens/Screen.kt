package com.example.apitest.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.apitest.data.PokemonEntity
import com.example.apitest.model.Pokemon
import com.example.apitest.model.PokemonItem
import com.example.apitest.viewModel.BDViewModel
import com.example.apitest.viewModel.PokemonViewModel3
import kotlinx.coroutines.flow.StateFlow
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


@Composable
fun Screen(viewModel: PokemonViewModel3, ){
    var name by remember { mutableStateOf("") }
    var id by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }
    var height by remember { mutableStateOf("") }


    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {

        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Nombre") }
        )

        TextField(
            value = weight,
            onValueChange = { weight = it },
            label = { Text("Peso") }
        )

        TextField(
            value = height,
            onValueChange = { height = it },
            label = { Text("Altura") }
        )

        Button(
            onClick = {

                val pokemon = Pokemon(
                    id = id.toIntOrNull() ?: 0,
                    name = name,
                    weight = weight.toIntOrNull() ?: 0,
                    height = height.toIntOrNull() ?: 0
                )

                viewModel.savePokemon(pokemon)

            }
        ) {
            Text("Guardar Pokemon")
        }





    }
}