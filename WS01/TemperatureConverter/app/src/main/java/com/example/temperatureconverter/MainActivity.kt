package com.example.temperatureconverter

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.Locale

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val convertBtn = findViewById<Button>(R.id.convertButton)
        val fahrenheitInput = findViewById<EditText>(R.id.fahrenheitInput)
        val celsiusInput = findViewById<EditText>(R.id.celsiusInput)

        convertBtn.setOnClickListener {

            val fahrenheit = fahrenheitInput.text.toString().trim().toDoubleOrNull()
            val celsius = celsiusInput.text.toString().trim().toDoubleOrNull()

            if (fahrenheit != null || celsius != null) {

                if (fahrenheit != null) {
                    val convertedCelsius = (fahrenheit - 32) * 5 / 9
                    celsiusInput.setText(String.format(Locale.ROOT, "%.2f", convertedCelsius))
                    Toast.makeText(this, "Converted to Celsius: ${String.format(Locale.ROOT, "%.2f", convertedCelsius)}", Toast.LENGTH_SHORT).show()
                }
                else if (celsius != null) {
                    val convertedFahrenheit = (celsius * 9 / 5) + 32
                    fahrenheitInput.setText(String.format(Locale.ROOT, "%.2f", convertedFahrenheit))
                    Toast.makeText(this, "Converted to Fahrenheit: ${String.format(Locale.ROOT, "%.2f", convertedFahrenheit)}", Toast.LENGTH_SHORT).show()
                }
            }
            else {
                fahrenheitInput.error = "Please enter a valid Fahrenheit temperature"
                celsiusInput.error = "Please enter a valid Celsius temperature"
                Toast.makeText(this, "Please enter valid temperatures", Toast.LENGTH_SHORT).show()
            }

        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}