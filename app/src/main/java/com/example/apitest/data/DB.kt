package com.example.apitest.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [PokemonEntity::class], version = 1)
abstract class BaseDeDades: RoomDatabase(){

    abstract fun funDAO(): IDao

    companion object {
        @Volatile
        private var Instance: BaseDeDades? = null

        fun getDatabase(context: Context): BaseDeDades {

            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, BaseDeDades::class.java, "todo_db")
                    .build()
                    .also { Instance = it }
            }
        }
    }
}