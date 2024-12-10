package com.example.ruletaapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ruletaapp.models.Cita

class CitasAdapter(private val citas: List<Cita>) : RecyclerView.Adapter<CitasAdapter.CitaViewHolder>() {

    // Crea nuevos ViewHolder cuando no hay suficientes para mostrar en pantalla
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CitaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cita, parent, false)
        return CitaViewHolder(view)
    }

    // Vincula los datos de una cita a un ViewHolder
    override fun onBindViewHolder(holder: CitaViewHolder, position: Int) {
        holder.bind(citas[position])
    }

    // Devuelve el número total de citas
    override fun getItemCount(): Int = citas.size

    // ViewHolder que contiene las vistas para mostrar una cita
    class CitaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val txtNombre = itemView.findViewById<TextView>(R.id.txt_nombre)
        private val txtDia = itemView.findViewById<TextView>(R.id.txt_dia)
        private val txtHora = itemView.findViewById<TextView>(R.id.txt_hora)
        private val txtDireccion = itemView.findViewById<TextView>(R.id.txt_direccion)
        private val txtFechaNacimiento = itemView.findViewById<TextView>(R.id.txt_fecha_nacimiento)

        // Vincula los datos de una cita a las vistas
        fun bind(cita: Cita) {
            txtNombre.text = cita.nombre
            txtDia.text = cita.dia
            txtHora.text = cita.hora
            txtDireccion.text = cita.direccion
            txtFechaNacimiento.text = cita.fechaNacimiento
        }
    }
}