package com.example.signupform.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.signupform.R

class Section1Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.section1)

        // Find views by ID
        val firstNameInput = findViewById<EditText>(R.id.firstNameInput)
        val emailInput = findViewById<EditText>(R.id.emailInput)
        val nextButton = findViewById<Button>(R.id.nextButton)

        // Handle button click
        nextButton.setOnClickListener {
            val firstName = firstNameInput.text.toString().trim()
            val email = emailInput.text.toString().trim()

            // Simple validation
            if (firstName.isEmpty()) {
                firstNameInput.error = "Name is required"
                return@setOnClickListener
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                emailInput.error = "Valid email is required"
                return@setOnClickListener
            }

            // Store in SharedPreferences
            val sharedPrefs = getSharedPreferences("SignupData", Context.MODE_PRIVATE)
            with(sharedPrefs.edit()) {
                putString("firstName", firstName)
                putString("email", email)
                apply()
            }

            // Navigate to Section 2
            val intent = Intent(this, Section2Activity::class.java)
            startActivity(intent)

            Toast.makeText(this, "Section 1 saved!", Toast.LENGTH_SHORT).show()
        }
    }
}
