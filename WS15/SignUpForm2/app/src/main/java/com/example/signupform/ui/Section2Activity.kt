package com.example.signupform.ui

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.signupform.R
import com.example.signupform.db.Database
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class Section2Activity : AppCompatActivity() {
    private lateinit var db: Database

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.section2)

        db = Database(this)

        // Find views by ID
        val dateInput = findViewById<EditText>(R.id.dateInput)
        val nextButton = findViewById<Button>(R.id.nextButton)

        // Date picker setup
        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        dateInput.setOnClickListener {
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
                calendar.set(selectedYear, selectedMonth, selectedDay)
                dateInput.setText(dateFormat.format(calendar.time))
            }, year, month, day).show()
        }

        // Handle Next button
        nextButton.setOnClickListener {
            val dateOfBirth = dateInput.text.toString().trim()

            // Validate date (year > 2003)
            if (dateOfBirth.isEmpty()) {
                dateInput.error = "Date of Birth is required"
                return@setOnClickListener
            }
            val selectedYear = calendar.get(Calendar.YEAR)
            if (selectedYear <= 2003) {
                dateInput.error = "Year must be after 2003"
                return@setOnClickListener
            }

            // Get Section 1 data from SharedPreferences
            val sharedPrefs = getSharedPreferences("SignupData", Context.MODE_PRIVATE)
            val firstName = sharedPrefs.getString("firstName", "") ?: ""
            val email = sharedPrefs.getString("email", "") ?: ""

            // Save to SQLite
            val result = db.addUser(firstName, email, dateOfBirth)
            if (result != -1L) {
                Toast.makeText(this, "Data saved to database!", Toast.LENGTH_SHORT).show()
                with(sharedPrefs.edit()) {
                    clear()
                    putString("userId", result.toString())
                    apply()
                }
                // Navigate to Section 3
                startActivity(Intent(this, Section3Activity::class.java))
            } else {
                Toast.makeText(this, "Failed to save data", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
