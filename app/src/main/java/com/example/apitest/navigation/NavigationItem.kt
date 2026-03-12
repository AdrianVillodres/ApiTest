package com.example.apitest.navigation

import androidx.compose.ui.graphics.vector.ImageVector

data class NavigationItem (
    val label: String,
    val icon: ImageVector,
    val route: String,
    val index: Int
)