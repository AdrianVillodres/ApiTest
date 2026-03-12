package com.example.apitest.navigation

import com.example.apitest.model.Pokemon
import kotlinx.serialization.Serializable

@Serializable
sealed class Destinations {
    @Serializable
    object Pantalla1 : Destinations()

    @Serializable
    object Pantalla2: Destinations()

    @Serializable
    object Pantalla3 : Destinations()

    @Serializable
    data class PantallaDetall(val nomItem: Pokemon) : Destinations()
}