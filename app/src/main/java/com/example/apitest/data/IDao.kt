package com.example.apitest.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface IDao{
    @Query("SELECT * FROM items")
    fun getAllItems(): Flow<List<PokemonEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertItem(item: PokemonEntity)

    @Delete
    suspend fun deleteItem(item: PokemonEntity)

    @Update
    suspend fun updateItem(item: PokemonEntity)

    @Query("DELETE FROM items WHERE name = 'bulbasaur'")
    suspend fun deleteCompleted()

    @Query("DELETE FROM items")
    suspend fun deleteAllItems()

}