package com.example.ca02

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ca02.db.Database
import com.example.ca02.db.User
import java.util.Calendar

class Section3Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_section3)

        // Retrieve stored user ID from SharedPreferences
        val prefs = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        val userId = prefs.getLong("userId", -1)

        // Initialize database instance and fetch user details
        val db = Database(this)
        val user = db.getUserById(userId)

        // UI Elements
        val userIdEditText = findViewById<TextView>(R.id.userIdTextView)
        val nameEditText = findViewById<EditText>(R.id.nameEditText)
        val emailEditText = findViewById<EditText>(R.id.editTextTextEmailAddress)
        val dobEditText = findViewById<EditText>(R.id.dateEditText)
        val progressBar = findViewById<ProgressBar>(R.id.progressBar3)
        val nextBtn = findViewById<Button>(R.id.nextBtn3)

        // Set progress bar to 75% (indicating step 3 of 4)
        progressBar.progress = 75

        // Display the user ID on the screen
        userIdEditText.text = "User Id: $userId"

        // Set up a date picker dialog for DOB input
        val calendar = Calendar.getInstance()
        dobEditText.setOnClickListener {
            DatePickerDialog(this, { _, y, m, d ->
                val date = "$y-${m + 1}-$d"
                if (y in 2000..2020) dobEditText.setText(date)
                else Toast.makeText(this, "Select a date between 2000-2020", Toast.LENGTH_SHORT).show()
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show()
        }

        // Populate fields with existing user data (if found)
        user?.let {
            nameEditText.setText(it.name)
            emailEditText.setText(it.email)
            dobEditText.setText(it.dob)
        }

        // Handle click event for the "Next" button
        nextBtn.setOnClickListener {
            val updatedName = nameEditText.text.toString().trim()
            val updatedEmail = emailEditText.text.toString().trim()
            val updatedDob = dobEditText.text.toString().trim()

            // Validate that no field is left empty
            if (updatedName.isEmpty() || updatedEmail.isEmpty() || updatedDob.isEmpty()) {
                Toast.makeText(this, "All fields must be filled", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Create an updated user object
            val updatedUser = User(userId, updatedName, updatedEmail, updatedDob)

            // Attempt to update the user in the database
            val success = db.updateUser(updatedUser)
            if (success) {
                Toast.makeText(this, "Updated successfully!", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, Section4Activity::class.java)) // Navigate to next section
            } else {
                Toast.makeText(this, "Update failed!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
