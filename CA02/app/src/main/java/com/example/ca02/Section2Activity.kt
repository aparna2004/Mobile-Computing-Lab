package com.example.ca02

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.ca02.db.Database

class Section2Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_section2)

        // Retrieve the stored user ID from SharedPreferences
        val prefs = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        val userId = prefs.getLong("userId", -1)

        // Fetch user details from the database using userId
        val user = Database(this).getUserById(userId)

        // UI Elements
        val greetingTextView = findViewById<TextView>(R.id.greetingTextView)
        val progressBar = findViewById<ProgressBar>(R.id.progressBar2)
        val nextBtn = findViewById<Button>(R.id.nextBtn2)

        // Display user information if found, else show "User not found"
        greetingTextView.text = user?.let {
            "Welcome, ${it.name}!\nEmail: ${it.email}\nDOB: ${it.dob}"
        } ?: "User not found"

        // Set progress bar to 50% (assuming 4 steps in the process)
        progressBar.progress = 50

        // Move to the next section when the button is clicked
        nextBtn.setOnClickListener {
            startActivity(Intent(this, Section3Activity::class.java))
        }
    }
}
