// DialogosHelper.kt
package com.example.ruletaapp.helpers

import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import com.example.ruletaapp.R

object DialogosHelper {
    const val PREFS_NAME = "theme_prefs"
    const val PREF_THEME_KEY = "theme"

    fun mostrarDialogoModo(context: Context) {
        val opciones = arrayOf("Claro", "Oscuro")
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val currentTheme = sharedPreferences.getInt(PREF_THEME_KEY, AppCompatDelegate.MODE_NIGHT_NO)

        AlertDialog.Builder(context)
            .setTitle("Seleccionar tema")
            .setSingleChoiceItems(opciones, if (currentTheme == AppCompatDelegate.MODE_NIGHT_NO) 0 else 1) { dialog, which ->
                val newTheme = if (which == 0) AppCompatDelegate.MODE_NIGHT_NO else AppCompatDelegate.MODE_NIGHT_YES
                AppCompatDelegate.setDefaultNightMode(newTheme)
                saveThemePreference(sharedPreferences, newTheme)
                dialog.dismiss()
            }
            .create()
            .show()
    }

    fun mostrarDialogoAcercaDe(context: Context) {
        AlertDialog.Builder(context)
            .setTitle(context.getString(R.string.dialog_acerca_de))
            .setMessage("Roll or Die\nVersión 1.0\nDesarrollado por Jorge A. Herrero Santana")
            .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
            .create()
            .show()
    }

    private fun saveThemePreference(sharedPreferences: SharedPreferences, theme: Int) {
        with(sharedPreferences.edit()) {
            putInt(PREF_THEME_KEY, theme)
            apply()
        }
    }
}