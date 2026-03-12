package com.example.apitest.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.apitest.data.SettingsRepository

class SettingsViewModel(private val repository: SettingsRepository) : ViewModel() {
    var darkMode by mutableStateOf(repository.getDarkMode())
        private set

    var isGrid by mutableStateOf(repository.getLayoutMode())
        private set

    fun toggleDarkMode(enabled: Boolean) {
        darkMode = enabled
        repository.saveDarkMode(enabled)
    }

    fun setLayoutMode(grid: Boolean) {
        isGrid = grid
        repository.saveLayoutMode(grid)
    }
}
// Aquesta classe és el que guarda la peça que necessitem (el repository)
class SettingsViewModelFactory(private val repository: SettingsRepository) : ViewModelProvider.Factory {

    // Aquesta funció la crida Android per crear el ViewModel
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        // 1. Comprovem que ens demanen el ViewModel correcte
        if (modelClass.isAssignableFrom(SettingsViewModel::class.java)) {

            // 2. Creem el ViewModel manualment i li "injectem" el repository
            @Suppress("UNCHECKED_CAST")
            return SettingsViewModel(repository) as T
        }

        // Si ens demanen un altre tipus de viewmodel, donem error
        throw IllegalArgumentException("Classe ViewModel desconeguda")
    }
}