package com.example.apitest.viewModel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apitest.model.Pokemon
import com.example.apitest.network.JsonReader
import com.example.apitest.network.PokeAPIClient
import com.example.apitest.network.retrofitBuilder
import kotlinx.coroutines.launch

class PokemonViewModel: ViewModel() {
    private val retrofit = retrofitBuilder().build()
    private val pokeApi = retrofit.create(PokeAPIClient::class.java)


    fun getEmolga(){
        viewModelScope.launch {
            try{
                val response = pokeApi.getPokemon()

                if(response.isSuccessful){
                    val emolga = response.body()
                    Log.d("PokemonViewModel", "Pokemon name: ${emolga?.name}")
                    Log.d("PokemonViewModel", "Pokemon weight: ${emolga?.id}")
                    Log.d("PokemonViewModel", "Pokemon height: ${emolga?.height}")
                }else{
                    Log.e("PokemonViewModel", "Error: ${response.code()}")
                }
            }catch (e: Exception){
                Log.e("PokemonViewModel", "Error: ${e.message}")
            }
        }

    }


}

//For json
class PokemonViewModel2: ViewModel() {

    //single item
    fun getEmolga(context: Context) {

        try {
            val pokemon = JsonReader().getPokemon(context)

            Log.d("PokemonViewModel", "Pokemon name: ${pokemon.name}")
            Log.d("PokemonViewModel", "Pokemon id: ${pokemon.id}")
            Log.d("PokemonViewModel", "Pokemon height: ${pokemon.height}")

        } catch (e: Exception) {
            Log.e("PokemonViewModel", "Error: ${e.message}")
        }
    }

    //varios items
    fun getPokemons(context: Context){

        try{

            val pokemonList = JsonReader().getPokemonList(context)

            for(pokemon in pokemonList){
                Log.d("PokemonViewModel", "Name: ${pokemon.name}")
                Log.d("PokemonViewModel", "Id: ${pokemon.id}")
            }

        }catch(e: Exception){
            Log.e("PokemonViewModel", "Error: ${e.message}")
        }

    }
}

//For formulario
class PokemonViewModel3(bdviewmodel : BDViewModel): ViewModel() {
    private var lista = mutableListOf<Pokemon>()

    private val bdviewmodel = bdviewmodel
    fun savePokemon(pokemon: Pokemon){
        Log.d("PokemonViewModel", "Pokemon name: ${pokemon.name}")
        Log.d("PokemonViewModel", "Pokemon id: ${pokemon.id}")
        Log.d("PokemonViewModel", "Pokemon weight: ${pokemon.weight}")
        Log.d("PokemonViewModel", "Pokemon height: ${pokemon.height}")
        bdviewmodel.addItem(pokemon)
    }
}
