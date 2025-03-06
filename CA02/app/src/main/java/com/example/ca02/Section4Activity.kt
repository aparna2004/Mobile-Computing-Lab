package com.example.ca02

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.ca02.db.Database

class Section4Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_section4)

        // Retrieve stored user ID from SharedPreferences
        val prefs = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        val userId = prefs.getLong("userId", -1)

        // UI Elements
        val userIdTextView = findViewById<TextView>(R.id.userIdTextView)
        val progressBar = findViewById<ProgressBar>(R.id.progressBar4)
        val submissionText = findViewById<TextView>(R.id.submittedTextView)
        val deleteButton = findViewById<Button>(R.id.deleteBtn)
        val backBtn = findViewById<Button>(R.id.backBtn)

        // Set the user ID in the text view
        userIdTextView.text = "User Id: $userId"

        // Progress bar at 100% (final step)
        progressBar.progress = 100

        // Display submission confirmation message
        submissionText.text = "Submitted!"

        // Navigate back to MainActivity when "Back" button is clicked
        backBtn.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish() // Close this activity to prevent stacking multiple instances
        }

        // Handle user deletion when "Delete Account" button is clicked
        deleteButton.setOnClickListener {
            if (userId == -1L) { // Check if the user exists
                Toast.makeText(this, "User not found!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Show confirmation dialog before deleting the user
            AlertDialog.Builder(this)
                .setTitle("Delete User")
                .setMessage("Are you sure you want to delete your account? This action cannot be undone.")
                .setPositiveButton("Delete") { _, _ ->  // If user confirms deletion
                    val db = Database(this)
                    val success = db.deleteUser(userId) // Delete user from database

                    if (success) {
                        // Remove user ID from SharedPreferences after deletion
                        prefs.edit().remove("userId").apply()

                        Toast.makeText(this, "User deleted successfully!", Toast.LENGTH_SHORT).show()

                        // Redirect to the input screen (InputsActivity)
                        startActivity(Intent(this, InputsActivity::class.java))
                        finish() // Close this activity
                    } else {
                        Toast.makeText(this, "Failed to delete user!", Toast.LENGTH_SHORT).show()
                    }
                }
                .setNegativeButton("Cancel", null) // If user cancels deletion
                .show()
        }
    }
}
