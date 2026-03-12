package com.example.apitest.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "items")
data class PokemonEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val weight: Int,
    val height: Int
)