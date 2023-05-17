package com.example.bmicalculator

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.graphics.toColor

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialization
        val weightText = findViewById<EditText>(R.id.etWeight)
        val heightText = findViewById<EditText>(R.id.etHeight)
        val calcButton = findViewById<Button>(R.id.btnCalculate)
        val ResultCard = findViewById<CardView>(R.id.cvResults)

        // Set OnClick Listener to the button
        calcButton.setOnClickListener {
            // Converting weight and height into a string format
            val weight = weightText.text.toString()
            val height = heightText.text.toString()
            // Input Validation
            if(validateInput(weight, height)) {
                ResultCard.visibility = View.VISIBLE
                // Compute BMI
                val bmi = computeBMI(weight, height)
                displayResults(bmi)
            }
        }
    }
    private fun computeBMI(weight:String, height:String):Float {
        // Calculate the BMI for the corresponding height and weight values
        var bmi = weight.toFloat()/((height.toFloat()/100)*(height.toFloat()/100))
        // Get result in two decimal places
        bmi = String.format("%.2f",bmi).toFloat()
        return bmi
    }
    private fun displayResults(bmi:Float) {
        // Initialization of the index, result and information
        val resultIndex = findViewById<TextView>(R.id.tvIndex)
        val resultDescription = findViewById<TextView>(R.id.tvResult)
        val info = findViewById<TextView>(R.id.tvInfo)
        resultIndex.text = bmi.toString()
        info.text = "(Normal range is 18.5 - 24.9)"
        var resultText = ""
        var color = 0
        when {
            bmi<18.50 -> {
                resultText = "Underweight"
                color = R.color.underweight
            }
            bmi in 18.50..24.99 -> {
                resultText = "Normal Weight"
                color = R.color.normal
            }
            bmi in 25.00..29.99 -> {
                resultText = "Overweight"
                color = R.color.over_weight
            }
            bmi in 30.00..34.99 -> {
                resultText = "Obesity Class I"
                color = R.color.obese
            }
            bmi in 35.00..39.99 -> {
                resultText = "Obesity Class II"
                color = R.color.obese
            }
            bmi>=40 -> {
                resultText = "Obesity Class III"
                color = R.color.obese
            }
        }
        resultDescription.setTextColor(ContextCompat.getColor(this,color))
        resultDescription.text = resultText
    }
    private fun validateInput(weight: String?, height: String?):Boolean {
        return when {
            weight.isNullOrEmpty() -> {
                Toast.makeText(this, "Weight is empty", Toast.LENGTH_LONG).show()
                false
            }

            height.isNullOrEmpty() -> {
                Toast.makeText(this, "Height is empty", Toast.LENGTH_LONG).show()
                false
            }

            else -> {
                true
            }
        }
    }
}