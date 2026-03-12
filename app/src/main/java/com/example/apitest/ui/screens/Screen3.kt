package com.example.apitest.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.apitest.viewModel.BDViewModel
import com.example.apitest.viewModel.SettingsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Screen3(settingsViewModel: SettingsViewModel, bdViewModel: BDViewModel) {
    val darkMode = settingsViewModel.darkMode
    val isGrid = settingsViewModel.isGrid

    var expanded by remember { mutableStateOf(false) }

    val layoutOptions = listOf("List", "Grid")
    val selectedText = if (isGrid) "Grid" else "List"

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {


        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Dark mode")
            Spacer(modifier = Modifier.width(12.dp))
            Switch(
                checked = darkMode,
                onCheckedChange = {
                    settingsViewModel.toggleDarkMode(it)
                }
            )
        }

        Spacer(modifier = Modifier.height(80.dp))


        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {

            OutlinedTextField(
                value = selectedText,
                onValueChange = {},
                readOnly = true,
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth(0.6f),
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                }
            )

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                layoutOptions.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option) },
                        onClick = {
                            settingsViewModel.setLayoutMode(option == "Grid")
                            expanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(80.dp))

        Button(onClick = { bdViewModel.clearDatabase() }) {
            Text("Delete favs")
        }
    }
}