package com.example.ruletaapp

import com.example.ruletaapp.JuegoActivity
import com.example.ruletaapp.ReservasActivity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.ruletaapp.databinding.ActivityMainBinding
import com.example.ruletaapp.helpers.DialogosHelper

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configurar botones
        binding.btnJuego.setOnClickListener {
            startActivity(Intent(this, JuegoActivity::class.java))
        }

        binding.btnReservas.setOnClickListener {
            startActivity(Intent(this, ReservasActivity::class.java))
        }

        // Configurar Navigator Drawer
        binding.navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_modo -> {
                    DialogosHelper.mostrarDialogoModo(this)
                }
                R.id.menu_acerca_de -> {
                    DialogosHelper.mostrarDialogoAcercaDe(this)
                }
            }
            true
        }
    }
}
