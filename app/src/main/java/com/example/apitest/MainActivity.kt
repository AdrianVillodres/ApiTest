package com.example.apitest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.apitest.model.Pokemon
import com.example.apitest.ui.theme.ApiTestTheme
import com.example.apitest.viewModel.PokemonViewModel
import com.example.apitest.viewModel.PokemonViewModel2
import com.example.apitest.viewModel.PokemonViewModel3

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*val viewModel = PokemonViewModel() // API
        viewModel.getEmolga()*/

        val viewModel2 = PokemonViewModel2() // JSON
        viewModel2.getEmolga(this) // single item
        viewModel2.getPokemons(this) // multiple items

        val viewModel3 = PokemonViewModel3() // Form
        enableEdgeToEdge()
        setContent {
            ApiTestTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                    PokemonForm(viewModel3)
                }
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

//For formulario
@Composable
fun PokemonForm(viewModel: PokemonViewModel3) {

    var name by remember { mutableStateOf("") }
    var id by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }
    var height by remember { mutableStateOf("") }

    Column {

        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Nombre") }
        )

        TextField(
            value = id,
            onValueChange = { id = it },
            label = { Text("ID") }
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