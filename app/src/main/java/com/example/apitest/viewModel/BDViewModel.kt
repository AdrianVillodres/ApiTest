package com.example.apitest.viewModel

import android.content.ClipData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apitest.data.IDao
import com.example.apitest.data.PokemonEntity
import com.example.apitest.model.Pokemon
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class BDViewModel(private val dao: IDao) : ViewModel() {

    //estats
    val items: StateFlow<List<PokemonEntity>> = dao.getAllItems()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    //Accions
    fun addItem(item: Pokemon) = viewModelScope.launch {
        dao.insertItem(PokemonEntity(name = item.name, weight = item.weight, height = item.height))
    }

    fun toggleItem(item: PokemonEntity) = viewModelScope.launch {
        dao.updateItem(item.copy(name = "AAA"))
    }

    fun deleteItem(item: PokemonEntity) = viewModelScope.launch {
        dao.deleteItem(item)
    }
}