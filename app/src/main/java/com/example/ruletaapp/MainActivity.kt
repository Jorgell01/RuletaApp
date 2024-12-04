package com.example.ruletaapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.GravityCompat
import com.example.ruletaapp.databinding.ActivityMainBinding
import com.example.ruletaapp.helpers.DialogosHelper

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var drawerToggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        applySavedTheme()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configurar Toolbar
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_menu)

        // Configurar DrawerToggle
        drawerToggle = ActionBarDrawerToggle(
            this, binding.drawerLayout, binding.toolbar,
            R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        binding.drawerLayout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()

        // Configurar botones
        binding.btnJuego.setOnClickListener {
            startActivity(Intent(this, JuegoActivity::class.java))
        }

        binding.btnReservas.setOnClickListener {
            startActivity(Intent(this, ReservasActivity::class.java))
        }

        // Configurar Navigation Drawer
        binding.navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_modo -> {
                    DialogosHelper.mostrarDialogoModo(this)
                }
                R.id.menu_acerca_de -> {
                    DialogosHelper.mostrarDialogoAcercaDe(this)
                }
                R.id.menu_salir -> {
                    finishAffinity() // Cierra la aplicación
                }
            }
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            true
        }
    }

    private fun applySavedTheme() {
        val sharedPreferences = getSharedPreferences(DialogosHelper.PREFS_NAME, MODE_PRIVATE)
        val theme = sharedPreferences.getInt(DialogosHelper.PREF_THEME_KEY, AppCompatDelegate.MODE_NIGHT_NO)
        AppCompatDelegate.setDefaultNightMode(theme)
    }
}