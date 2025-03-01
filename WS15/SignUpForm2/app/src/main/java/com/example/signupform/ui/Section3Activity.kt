package com.example.signupform.ui

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.signupform.R
import com.example.signupform.db.Database

class Section3Activity : AppCompatActivity() {
    private lateinit var db: Database

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.section3)

        db = Database(this)

        // Find TextViews by ID
        val userNameText = findViewById<TextView>(R.id.user_name)
        val userEmailText = findViewById<TextView>(R.id.user_email)
        val userDobText = findViewById<TextView>(R.id.user_dob)

        // Fetch and display the latest user data
        val user = db.getLatestUser()
        if (user != null) {
            userNameText.text = "Name: ${user.firstName}"
            userEmailText.text = "Email: ${user.email}"
            userDobText.text = "Date of Birth: ${user.dateOfBirth}"
        } else {
            userNameText.text = "Name: Not found"
            userEmailText.text = "Email: Not found"
            userDobText.text = "Date of Birth: Not found"
        }
    }
}