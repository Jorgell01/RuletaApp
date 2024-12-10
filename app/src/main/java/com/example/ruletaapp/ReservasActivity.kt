package com.example.ruletaapp

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ruletaapp.databinding.ActivityReservasBinding
import com.example.ruletaapp.models.Cita
import java.util.*

class ReservasActivity : AppCompatActivity() {
    private lateinit var binding: ActivityReservasBinding
    private val citas = mutableListOf<Cita>()
    private lateinit var adapter: CitasAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReservasBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = CitasAdapter(citas)
        binding.recyclerViewCitas.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewCitas.adapter = adapter

        // Configurar TimePicker para la hora
        binding.edtHora.setOnClickListener {
            showTimePicker()
        }

        // Configurar DatePicker para el día
        binding.edtDia.setOnClickListener {
            showDatePicker()
        }

        // Configurar DatePicker para la fecha de nacimiento
        binding.edtFechaNacimiento.setOnClickListener {
            showDatePicker { date -> binding.edtFechaNacimiento.setText(date) }
        }

        // Agregar una nueva cita a la lista
        binding.btnAgregarCita.setOnClickListener {
            val nuevaCita = Cita(
                nombre = binding.edtNombre.text.toString(),
                dia = binding.edtDia.text.toString(),
                hora = binding.edtHora.text.toString(),
                direccion = binding.edtDireccion.text.toString(),
                fechaNacimiento = binding.edtFechaNacimiento.text.toString()
            )
            citas.add(nuevaCita)
            adapter.notifyDataSetChanged() // Notifica al adaptador que los datos han cambiado
        }
    }

    // Muestra un TimePicker para seleccionar la hora
    private fun showTimePicker() {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        TimePickerDialog(this, { _, selectedHour, selectedMinute ->
            val time = String.format("%02d:%02d", selectedHour, selectedMinute)
            binding.edtHora.setText(time)
        }, hour, minute, true).show()
    }

    // Muestra un DatePicker para seleccionar la fecha
    private fun showDatePicker(onDateSelected: ((String) -> Unit)? = null) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
            val date = String.format("%02d/%02d/%04d", selectedDay, selectedMonth + 1, selectedYear)
            if (onDateSelected != null) {
                onDateSelected(date)
            } else {
                binding.edtDia.setText(date)
            }
        }, year, month, day).show()
    }
}