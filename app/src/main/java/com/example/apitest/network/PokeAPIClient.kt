package com.example.apitest.network

import com.example.apitest.model.Pokemon
import okhttp3.Response
import retrofit2.http.GET

interface PokeAPIClient {
    @GET ("pokemon/emolga")
    suspend fun getPokemon(): retrofit2.Response<Pokemon>
}