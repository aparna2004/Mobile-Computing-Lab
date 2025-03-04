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

        val prefs = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        val userId = prefs.getLong("userId", -1)

        val userIdTextView = findViewById<TextView>(R.id.userIdTextView)
        val progressBar = findViewById<ProgressBar>(R.id.progressBar4)
        val submissionText = findViewById<TextView>(R.id.submittedTextView)
        val deleteButton = findViewById<Button>(R.id.deleteBtn)

        userIdTextView.text = "User Id: $userId"
        progressBar.progress = 100
        submissionText.text = "Submitted!"


        deleteButton.setOnClickListener {
            if (userId == -1L) {
                Toast.makeText(this, "User not found!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            AlertDialog.Builder(this)
                .setTitle("Delete User")
                .setMessage("Are you sure you want to delete your account? This action cannot be undone.")
                .setPositiveButton("Delete") { _, _ ->
                    val db = Database(this)
                    val success = db.deleteUser(userId)
                    if (success) {
                        prefs.edit().remove("userId").apply()
                        Toast.makeText(this, "User deleted successfully!", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(this, "Failed to delete user!", Toast.LENGTH_SHORT).show()
                    }
                }
                .setNegativeButton("Cancel", null)
                .show()
        }
    }
}
