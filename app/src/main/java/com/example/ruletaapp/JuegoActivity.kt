package com.example.ruletaapp

import android.animation.ObjectAnimator
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.floor
import kotlin.random.Random

class JuegoActivity : AppCompatActivity() {
    private val wheelSections = (0..36).map { it.toString() }
    private var totalChips = 100
    private var currentBet = 0
    private var selectedOption: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_juego)

        val rouletteWheel = findViewById<ImageView>(R.id.rouletteWheel)
        val btnSpin = findViewById<Button>(R.id.btnSpin)
        val txtResult = findViewById<TextView>(R.id.txtResult)
        val chipCount = findViewById<TextView>(R.id.chipCount)

        chipCount.text = totalChips.toString()

        btnSpin.setOnClickListener {
            if (currentBet == 0 || selectedOption == null) {
                Toast.makeText(this, "Por favor, realiza una apuesta.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val randomAngle = Random.nextInt(360, 1440)
            val sectionAngle = 360f / wheelSections.size

            // Animación de la ruleta
            val animator = ObjectAnimator.ofFloat(rouletteWheel, "rotation", 0f, randomAngle.toFloat())
            animator.duration = 3000
            animator.start()

            animator.addListener(object : android.animation.Animator.AnimatorListener {
                override fun onAnimationStart(animation: android.animation.Animator) {}

                override fun onAnimationEnd(animation: android.animation.Animator) {
                    // Calcular el ángulo final relativo
                    val finalAngle = randomAngle % 360
                    val sectionIndex = floor(finalAngle / sectionAngle).toInt()

                    // El número ganador
                    val result = wheelSections[(36 - sectionIndex) % 37] // Invertimos la dirección del giro

                    txtResult.text = "¡El número ganador es: $result!"

                    // Comprobar si la apuesta es ganadora
                    if (selectedOption == result) {
                        totalChips += currentBet * 2
                        Toast.makeText(this@JuegoActivity, "¡Ganaste!", Toast.LENGTH_SHORT).show()
                    } else {
                        totalChips -= currentBet
                        Toast.makeText(this@JuegoActivity, "¡Perdiste!", Toast.LENGTH_SHORT).show()
                    }
                    chipCount.text = totalChips.toString()
                }

                override fun onAnimationCancel(animation: android.animation.Animator) {}
                override fun onAnimationRepeat(animation: android.animation.Animator) {}
            })
        }

        configureBettingButtons()
    }

    private fun configureBettingButtons() {
        val bettingButtons = listOf(
            findViewById<Button>(R.id.btn_1),
            findViewById<Button>(R.id.btn_2),
            findViewById<Button>(R.id.btn_3),
            findViewById<Button>(R.id.btn_4),
            findViewById<Button>(R.id.btn_5),
            findViewById<Button>(R.id.btn_6),
            findViewById<Button>(R.id.btn_7),
            findViewById<Button>(R.id.btn_8),
            findViewById<Button>(R.id.btn_9),
            findViewById<Button>(R.id.btn_10),
            findViewById<Button>(R.id.btn_11),
            findViewById<Button>(R.id.btn_12),
            findViewById<Button>(R.id.btn_13),
            findViewById<Button>(R.id.btn_14),
            findViewById<Button>(R.id.btn_15),
            findViewById<Button>(R.id.btn_16),
            findViewById<Button>(R.id.btn_17),
            findViewById<Button>(R.id.btn_18),
            findViewById<Button>(R.id.btn_19),
            findViewById<Button>(R.id.btn_20),
            findViewById<Button>(R.id.btn_21),
            findViewById<Button>(R.id.btn_22),
            findViewById<Button>(R.id.btn_23),
            findViewById<Button>(R.id.btn_24),
            findViewById<Button>(R.id.btn_25),
            findViewById<Button>(R.id.btn_26),
            findViewById<Button>(R.id.btn_27),
            findViewById<Button>(R.id.btn_28),
            findViewById<Button>(R.id.btn_29),
            findViewById<Button>(R.id.btn_30),
            findViewById<Button>(R.id.btn_31),
            findViewById<Button>(R.id.btn_32),
            findViewById<Button>(R.id.btn_33),
            findViewById<Button>(R.id.btn_34),
            findViewById<Button>(R.id.btn_35),
            findViewById<Button>(R.id.btn_36),
            findViewById<Button>(R.id.btn_even),
            findViewById<Button>(R.id.btn_odd)
        )

        for (button in bettingButtons) {
            button.setOnClickListener {
                selectedOption = button.text.toString()
                currentBet = 10
                Toast.makeText(this, "Apostaste por $selectedOption", Toast.LENGTH_SHORT).show()
            }
        }
    }
}