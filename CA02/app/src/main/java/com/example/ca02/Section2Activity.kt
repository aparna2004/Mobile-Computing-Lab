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

        val prefs = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        val userId = prefs.getLong("userId", -1)

        val user = Database(this).getUserById(userId)

        val greetingTextView = findViewById<TextView>(R.id.greetingTextView)
        val progressBar = findViewById<ProgressBar>(R.id.progressBar2)
        val nextBtn = findViewById<Button>(R.id.nextBtn2)

        greetingTextView.text = user?.let { "Welcome, ${it.name}!\nEmail: ${it.email}\nDOB: ${it.dob}" }
            ?: "User not found"

        progressBar.progress = 50

        nextBtn.setOnClickListener {
            startActivity(Intent(this, Section3Activity::class.java))
        }
    }
}
