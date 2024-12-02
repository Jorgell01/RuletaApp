package com.example.ruletaapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.ruletaapp.databinding.ActivityMainBinding
import com.example.ruletaapp.helpers.DialogosHelper
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        drawerLayout = binding.drawerLayout

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
            }
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }
    }
}