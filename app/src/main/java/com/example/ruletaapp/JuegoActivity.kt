package com.example.ruletaapp

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.animation.DecelerateInterpolator
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.roundToInt

class JuegoActivity : AppCompatActivity() {

    private lateinit var triangleIndicator: ImageView
    private lateinit var rouletteWheel: ImageView
    private lateinit var btnSpin: Button
    private lateinit var spinnerNumbers: Spinner
    private lateinit var spinnerColors: Spinner
    private lateinit var editTextBetAmount: EditText
    private lateinit var labelInitialChips: TextView
    private lateinit var labelBetChips: TextView

    private var initialChips = 1000
    private var betChips = 0
    private val numbers = (0..36).toList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_juego)

        // Enlazar vistas
        triangleIndicator = findViewById(R.id.triangleIndicator)
        rouletteWheel = findViewById(R.id.rouletteWheel)
        btnSpin = findViewById(R.id.btnSpin)
        spinnerNumbers = findViewById(R.id.spinnerNumbers)
        spinnerColors = findViewById(R.id.spinnerColors)
        editTextBetAmount = findViewById(R.id.editTextBetAmount)
        labelInitialChips = findViewById(R.id.labelInitialChips)
        labelBetChips = findViewById(R.id.labelBetChips)

        setupSpinners()

        btnSpin.setOnClickListener {
            if (validateBet()) {
                calculateMoneyStake()
                spinRoulette()
            } else {
                Toast.makeText(this, "Por favor, completa tu apuesta correctamente", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupSpinners() {
        val numbers = (0..36).map { it.toString() }
        val colors = listOf("Rojo", "Negro", "Verde")

        spinnerNumbers.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, numbers)
        spinnerColors.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, colors)
    }

    private fun validateBet(): Boolean {
        val betAmount = editTextBetAmount.text.toString().toIntOrNull() ?: 0
        return betAmount > 0 && spinnerNumbers.selectedItem != null && spinnerColors.selectedItem != null
    }

    private fun calculateMoneyStake() {
        val betAmount = editTextBetAmount.text.toString().toIntOrNull() ?: 0
        if (betAmount > initialChips) {
            Toast.makeText(this, "No tienes suficientes fichas para esta apuesta", Toast.LENGTH_SHORT).show()
            return
        }

        initialChips -= betAmount
        betChips += betAmount
        updateLabels()
    }

    private fun spinRoulette() {
        val rotation = (720..1440).random().toFloat() // Rotación aleatoria con al menos dos giros completos
        val animator = ObjectAnimator.ofFloat(rouletteWheel, "rotation", rouletteWheel.rotation, rouletteWheel.rotation + rotation)
        animator.duration = 3000
        animator.interpolator = DecelerateInterpolator()
        animator.start()

        animator.addListener(object : android.animation.Animator.AnimatorListener {
            override fun onAnimationStart(animation: android.animation.Animator) {}
            override fun onAnimationEnd(animation: android.animation.Animator) {
                determineResult(rouletteWheel.rotation)
            }
            override fun onAnimationCancel(animation: android.animation.Animator) {}
            override fun onAnimationRepeat(animation: android.animation.Animator) {}
        })
    }

    private fun determineResult(rotation: Float) {
        val normalizedRotation = (rotation % 360 + 360) % 360 // Normaliza el ángulo a [0, 360)
        val anglePerNumber = 360f / numbers.size

        // Determina el índice del número ganador basado en la posición final de la ruleta
        val winningIndex = ((normalizedRotation + anglePerNumber / 2) / anglePerNumber).toInt() % numbers.size
        val winningNumber = numbers[winningIndex]

        val winningColor = when {
            winningNumber == 0 -> "Verde"
            winningNumber % 2 == 0 -> "Negro"
            else -> "Rojo"
        }

        AlertDialog.Builder(this)
            .setTitle("Número Ganador")
            .setMessage("El número ganador es: $winningNumber")
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
                calculateWinnings(winningNumber, winningColor)
            }
            .setCancelable(false)
            .show()
    }

    private fun calculateWinnings(winningNumber: Int, winningColor: String) {
        val betAmount = editTextBetAmount.text.toString().toIntOrNull() ?: 0
        var winnings = 0

        // Calcular ganancias por número
        if (spinnerNumbers.selectedItem.toString() == winningNumber.toString()) {
            winnings += betAmount * 5
        }

        // Calcular ganancias por color
        if (spinnerColors.selectedItem.toString() == winningColor) {
            winnings += when (winningColor) {
                "Verde" -> betAmount * 14
                else -> betAmount * 2
            }
        }

        // Actualizar fichas
        initialChips += winnings
        betChips = 0
        updateLabels()

        if (initialChips == 0) {
            showGameOverDialog()
        }
    }

    private fun updateLabels() {
        labelInitialChips.text = "Fichas iniciales: $initialChips"
        labelBetChips.text = "Fichas apostadas: $betChips"
    }

    private fun showGameOverDialog() {
        AlertDialog.Builder(this)
            .setTitle("Juego Terminado")
            .setMessage("Te has quedado sin fichas. ¿Quieres reiniciar el juego o volver al menú principal?")
            .setPositiveButton("Reiniciar") { _, _ -> resetGame() }
            .setNegativeButton("Menú Principal") { _, _ -> finish() }
            .setCancelable(false)
            .show()
    }

    private fun resetGame() {
        initialChips = 1000
        betChips = 0
        updateLabels()
    }
}