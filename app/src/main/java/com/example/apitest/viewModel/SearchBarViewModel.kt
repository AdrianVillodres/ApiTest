package com.example.apitest.viewModel

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch


class SearchBarViewModel(bdViewModel: BDViewModel) : ViewModel() {

    var searchedText by mutableStateOf("")
        private set

    var active by mutableStateOf(false)

    var searchHistory = mutableStateListOf<String>()
        private set

    // filteredNames será la lista de resultados como Strings
    var filteredNames = mutableStateListOf<String>()
        private set

    // allNames contendrá todos los nombres de Pokémon
    private var allNames = mutableListOf<String>()

    init {
        viewModelScope.launch {
            bdViewModel.items.collect { list ->
                val names = list.map { it.name }
                allNames.clear()
                allNames.addAll(names)

                if (searchedText.isNotEmpty()) {
                    onSearchTextChange(searchedText)
                }
            }
        }
    }

    fun onSearchTextChange(text: String) {
        searchedText = text
        filteredNames.clear()
        if (text.isNotEmpty()) {
            val results = allNames.filter { it.contains(text, ignoreCase = true) }
            filteredNames.addAll(results)
        }
    }

    fun onActiveChange(isActive: Boolean) {
        active = isActive
    }

    fun onSearch(text: String) {
        if (text.isNotEmpty()) {
            searchHistory.add(text)
            onActiveChange(false)
        }
    }

    fun onClearHistory() {
        searchHistory.clear()
    }
}