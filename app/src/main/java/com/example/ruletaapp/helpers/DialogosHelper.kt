package com.example.ruletaapp.helpers

import android.app.AlertDialog
import android.content.Context
import com.example.ruletaapp.R

object DialogosHelper {
    fun mostrarDialogoModo(context: Context) {
        val opciones = arrayOf("Claro", "Oscuro")
        AlertDialog.Builder(context)
            .setTitle("Seleccionar tema")
            .setSingleChoiceItems(opciones, -1) { dialog, which ->
                // Cambiar tema según la selección
                dialog.dismiss()
            }
            .create()
            .show()
    }

    fun mostrarDialogoAyuda(context: Context) {
        AlertDialog.Builder(context)
            .setTitle("Ayuda")
            .setMessage("Esta es una breve explicación de cómo navegar por la app y sus funciones.")
            .setPositiveButton("Cerrar") { dialog, _ -> dialog.dismiss() }
            .create()
            .show()
    }

    fun mostrarDialogoAcercaDe(context: Context) {
        AlertDialog.Builder(context)
            .setTitle("Acerca de")
            .setMessage("Nombre: RuletaApp\nVersión: 1.0\nDesarrollador: Jorge A. Herrero Santana")
            .setPositiveButton("Cerrar") { dialog, _ -> dialog.dismiss() }
            .create()
            .show()
    }
}