package com.example.apitest.data

import android.content.Context

class SettingsRepository(context: Context) {
    private val sharedPreferences =
        context.getSharedPreferences("AppSettings", Context.MODE_PRIVATE)

    companion object{
        private const val DARK_MODE_KEY = "dark_mode"
        private const val LAYOUT_KEY = "layout_mode"
    }

    fun saveDarkMode(enabled: Boolean) {
        sharedPreferences.edit().putBoolean(DARK_MODE_KEY, enabled).apply()
    }

    fun getDarkMode(): Boolean {
        return sharedPreferences.getBoolean(DARK_MODE_KEY, false)
    }

    fun saveLayoutMode(isGrid: Boolean) {
        sharedPreferences.edit().putBoolean(LAYOUT_KEY, isGrid).apply()
    }

    fun getLayoutMode(): Boolean {
        return sharedPreferences.getBoolean(LAYOUT_KEY, false)
    }
}