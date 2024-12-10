package com.example.ruletaapp

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

class JuegoActivity : AppCompatActivity() {

    private lateinit var diceImage: ImageView
    private lateinit var rollButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_juego)

        diceImage = findViewById(R.id.diceImage)
        rollButton = findViewById(R.id.rollButton)

        rollButton.setOnClickListener {
            rollDice()
        }
    }

    private fun rollDice() {
        val result = Random.nextInt(1, 7)
        val drawableResource = when (result) {
            1 -> R.drawable.dice_1
            2 -> R.drawable.dice_2
            3 -> R.drawable.dice_3
            4 -> R.drawable.dice_4
            5 -> R.drawable.dice_5
            else -> R.drawable.dice_6
        }
        diceImage.setImageResource(drawableResource)
        showResultDialog(result)
    }

    private fun showResultDialog(result: Int) {
        val message = when (result) {
            1 -> "¿Un uno? Colega, eres un pringado en todos los aspectos de tu vida."
            2 -> "¿Un dos? Eso es lo que sacas en los exámenes, ¿verdad?."
            3 -> "¿Un tres? Bueno, al menos no es un uno."
            4 -> "¿Un cuatro? Sigues siendo un maula."
            5 -> "¿Un cinco? ¡Casi tienes suerte en tu triste vida! Pero no."
            else -> "Por fin colega, un seis. ¿Tanto te costaba sacarlo?."
        }

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Resultado")
        builder.setMessage(message)
        builder.setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
        builder.show()
    }
}