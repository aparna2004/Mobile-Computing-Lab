package com.example.signupform.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.signupform.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Find views
        val signUpButton = findViewById<Button>(R.id.signUpButton)

        // Navigate to Section1Activity on button click
        signUpButton.setOnClickListener {
            startActivity(Intent(this, Section1Activity::class.java))
        }
    }
}