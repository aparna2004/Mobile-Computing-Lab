package com.example.ca02

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.ca02.db.Database
import java.util.*

class Section1Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_section1)

        // Initialize database and shared preferences
        val db = Database(this)
        val prefs = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)

        // UI Elements
        val nameInput = findViewById<EditText>(R.id.nameEditText)
        val emailInput = findViewById<EditText>(R.id.editTextTextEmailAddress)
        val dobInput = findViewById<EditText>(R.id.dateEditText)
        val nextBtn = findViewById<Button>(R.id.nextBtn1)

        // Set initial progress to 25% (assuming a multi-step process)
        findViewById<ProgressBar>(R.id.progressBar1).progress = 25

        // Date Picker for selecting DOB
        val calendar = Calendar.getInstance()
        dobInput.setOnClickListener {
            DatePickerDialog(
                this, { _, y, m, d ->
                    val date = "$y-${m + 1}-$d"
                    // Restrict date selection between the years 2000 and 2020
                    if (y in 2000..2020) {
                        dobInput.setText(date)
                    } else {
                        showToast("Select a date between 2000-2020")
                    }
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        // Handling the Next Button click event
        nextBtn.setOnClickListener {
            val name = nameInput.text.toString().trim()
            val email = emailInput.text.toString().trim()
            val dob = dobInput.text.toString().trim()

            when {
                // Validation: Check if all fields are filled
                name.isEmpty() || email.isEmpty() || dob.isEmpty() -> showToast("Please fill all fields")

                // Validation: Check for valid email format
                !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> showToast("Enter a valid email")

                else -> {
                    // Store user details in the database
                    val userId = db.addUser(name, email, dob)

                    // Save user ID in SharedPreferences
                    prefs.edit().putLong("userId", userId).apply()

                    // Move to the next section
                    startActivity(Intent(this, Section2Activity::class.java))
                }
            }
        }
    }

    // Utility function to show Toast messages
    private fun showToast(msg: String) = Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}
