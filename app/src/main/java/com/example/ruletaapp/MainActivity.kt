package com.example.ruletaapp

import android.content.Intent
import android.os.Bundle
import android.widget.RatingBar
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.GravityCompat
import com.example.ruletaapp.databinding.ActivityMainBinding
import com.example.ruletaapp.helpers.DialogosHelper

class MainActivity : AppCompatActivity() {
    // Declaración de variables para View Binding, DrawerToggle y RatingBar
    private lateinit var binding: ActivityMainBinding
    private lateinit var drawerToggle: ActionBarDrawerToggle
    private lateinit var ratingBar: RatingBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        applySavedTheme() // Aplica el tema guardado en las preferencias del usuario
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root) // Establece el contenido de la vista usando View Binding

        // Configurar Toolbar
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true) // Habilita el botón de navegación en la barra de herramientas
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_menu) // Establece el icono del botón de navegación

        // Configurar DrawerToggle para el Navigation Drawer
        drawerToggle = ActionBarDrawerToggle(
            this, binding.drawerLayout, binding.toolbar,
            R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        binding.drawerLayout.addDrawerListener(drawerToggle) // Añade el DrawerToggle como listener del DrawerLayout
        drawerToggle.syncState() // Sincroniza el estado del DrawerToggle

        // Configurar botones para iniciar otras actividades
        binding.btnJuego.setOnClickListener {
            startActivity(Intent(this, JuegoActivity::class.java)) // Inicia JuegoActivity
        }

        binding.btnReservas.setOnClickListener {
            startActivity(Intent(this, ReservasActivity::class.java)) // Inicia ReservasActivity
        }

        binding.btnTroyano.setOnClickListener {
            val intent = Intent(this, VideoActivity::class.java)
            startActivity(intent) // Inicia VideoActivity
        }

        // Configurar RatingBar
        val ratingBar = findViewById<RatingBar>(R.id.ratingBar)
        ratingBar.setOnRatingBarChangeListener { _, rating, _ ->
            // Muestra un Toast con la puntuación seleccionada
            Toast.makeText(this, "Puntuación: $rating", Toast.LENGTH_SHORT).show()
        }

        // Configurar Navigation Drawer
        binding.navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_modo -> {
                    DialogosHelper.mostrarDialogoModo(this) // Muestra el diálogo para seleccionar el modo (tema)
                }
                R.id.menu_acerca_de -> {
                    DialogosHelper.mostrarDialogoAcercaDe(this) // Muestra el diálogo "Acerca de"
                }
                R.id.menu_salir -> {
                    finishAffinity() // Cierra la aplicación
                }
            }
            binding.drawerLayout.closeDrawer(GravityCompat.START) // Cierra el Navigation Drawer
            true
        }
    }

    // Aplica el tema guardado en las preferencias del usuario
    private fun applySavedTheme() {
        val sharedPreferences = getSharedPreferences(DialogosHelper.PREFS_NAME, MODE_PRIVATE)
        val theme = sharedPreferences.getInt(DialogosHelper.PREF_THEME_KEY, AppCompatDelegate.MODE_NIGHT_NO)
        AppCompatDelegate.setDefaultNightMode(theme) // Establece el modo de noche según el tema guardado
    }
}