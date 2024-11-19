package com.example.ruletaapp

import android.animation.ObjectAnimator
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ruletaapp.R
import kotlin.math.floor
import kotlin.random.Random

class JuegoActivity : AppCompatActivity() {
    private val wheelSections = listOf(
        "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15",
        "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29",
        "30", "31", "32", "33", "34", "35", "36"
    )

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

            val animator = ObjectAnimator.ofFloat(rouletteWheel, "rotation", 0f, randomAngle.toFloat())
            animator.duration = 3000
            animator.start()

            animator.addListener(object : android.animation.Animator.AnimatorListener {
                override fun onAnimationStart(animation: android.animation.Animator) {}
                override fun onAnimationEnd(animation: android.animation.Animator) {
                    val finalAngle = randomAngle % 360
                    val sectionIndex = floor(finalAngle / sectionAngle).toInt()
                    val result = wheelSections[sectionIndex]

                    txtResult.text = "¡El número ganador es: $result!"

                    // Verificar si ganó la apuesta
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

        // Configurar botones de apuesta
        findViewById<Button>(R.id.btnEven).setOnClickListener {
            selectedOption = "EVEN"
            currentBet = 10
        }

        findViewById<Button>(R.id.btnOdd).setOnClickListener {
            selectedOption = "ODD"
            currentBet = 10
        }
    }
}
