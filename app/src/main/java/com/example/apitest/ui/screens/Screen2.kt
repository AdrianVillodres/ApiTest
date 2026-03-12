package com.example.apitest.ui.screens

import android.content.ClipData
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.apitest.data.PokemonEntity
import com.example.apitest.model.Pokemon
import com.example.apitest.model.PokemonItem
import com.example.apitest.viewModel.BDViewModel
import com.example.apitest.viewModel.SearchBarViewModel
import com.example.apitest.viewModel.SettingsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Screen2(viewModel: SearchBarViewModel, bdViewModel: BDViewModel, settingsViewModel: SettingsViewModel, onNavegarAlDetall: (PokemonEntity) -> Unit) {
    val items by bdViewModel.items.collectAsState()
    var isGrid = settingsViewModel.isGrid

    Column(modifier = Modifier.fillMaxSize().padding(10.dp)){
        SearchBar(
            query = viewModel.searchedText,
            onQueryChange = { viewModel.onSearchTextChange(it) },
            onSearch = { viewModel.onSearch(it) },
            active = viewModel.active,
            onActiveChange = { viewModel.onActiveChange(it) },
            placeholder = { Text("Busca un nom...") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            trailingIcon = {
                if (viewModel.active) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Tancar",
                        modifier = Modifier.clickable {
                            if (viewModel.searchedText.isNotEmpty()) {
                                viewModel.onSearchTextChange("")
                            } else {
                                viewModel.onActiveChange(false)
                            }
                        }
                    )
                }
            }
        ) {

            if (viewModel.searchedText.isNotEmpty()) {
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(viewModel.filteredNames) { nomTrobat ->
                        ResultatCard(nom = nomTrobat)
                    }
                }
            }
            else {
                if (viewModel.searchHistory.isNotEmpty()) {
                    Text(
                        text = "Cerques recents",
                        style = MaterialTheme.typography.titleSmall,
                        modifier = Modifier.padding(16.dp),
                        color = MaterialTheme.colorScheme.primary
                    )

                    LazyColumn {
                        items(viewModel.searchHistory) { itemHistorial ->
                            ListItem(
                                headlineContent = { Text(text = itemHistorial) },
                                leadingContent = { Icon(Icons.Default.History, contentDescription = null) },
                                modifier = Modifier.clickable {
                                    viewModel.onSearchTextChange(itemHistorial)
                                }
                            )
                        }

                        // Botó per esborrar historial al final de la llista
                        item {
                            TextButton(onClick = { viewModel.onClearHistory() }) {
                                Text("Esborrar tot l'historial")
                            }
                        }
                    }
                } else {
                    // Missatge si no hi ha historial ni text
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("Comença a escriure per buscar...", color = MaterialTheme.colorScheme.secondary)
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        if (isGrid) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(8.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(items) { item ->
                    PokemonItem(
                        item = item, onItemClick = onNavegarAlDetall
                    )
                }
            }
        }else {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(items) { item ->
                    PokemonItem(item = item, onItemClick = onNavegarAlDetall)
                }
            }
        }
    }
    }
@Composable
fun ResultatCard(nom: String) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icona d'usuari a l'esquerra
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(
                    text = nom,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "Usuari registrat",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        }
    }
}