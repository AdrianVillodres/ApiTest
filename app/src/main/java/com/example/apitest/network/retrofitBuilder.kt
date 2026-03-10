package com.example.apitest.network

import android.content.Context
import com.example.apitest.model.Pokemon
import com.google.gson.Gson
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class retrofitBuilder {
    fun build() : Retrofit =
        Retrofit.Builder()
            .baseUrl("https://pokeapi.co/api/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
}


//para json(el json debe ir en app/src/main, buscar en el file explorer)
class JsonReader {

    fun getPokemon(context: Context): Pokemon {
        val jsonString = context.assets
            .open("Pokemon.json")
            .bufferedReader()
            .use { it.readText() }

        return Gson().fromJson(jsonString, Pokemon::class.java)
    }
}