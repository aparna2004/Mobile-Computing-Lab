package com.example.ca01

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ViewForm : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_form)

        // Get values passed through Intent
        val name = intent.getStringExtra("NAME")
        val email = intent.getStringExtra("EMAIL")
        val salary = intent.getStringExtra("SALARY")
        val occupation = intent.getStringExtra("OCCUPATION")
        val gender = intent.getStringExtra("GENDER")

        // Get shared preferences for name and salary (if required)
        val sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE)
        val savedName = sharedPreferences.getString("name", "Default Name")
        val savedSalary = sharedPreferences.getString("salary", "Default Salary")

        // Find TextViews
        val nameTextView = findViewById<TextView>(R.id.nameTextView)
        val emailTextView = findViewById<TextView>(R.id.emailTextView)
        val salaryTextView = findViewById<TextView>(R.id.salaryTextView)
        val occupationTextView = findViewById<TextView>(R.id.occupationTextView)
        val genderTextView = findViewById<TextView>(R.id.genderTextView)
        val ratingTextView = findViewById<TextView>(R.id.ratingTextView)
        val checkBoxSelectionTextView = findViewById<TextView>(R.id.checkBoxSelectionTextView)

        // Display values on TextViews
        nameTextView.text = "Name: $name"
        emailTextView.text = "Email: $email"
        salaryTextView.text = "Salary: $salary"
        occupationTextView.text = "Occupation: $occupation"
        genderTextView.text = "Gender: $gender"

        // Assuming rating value is passed as float value in intent
        val ratingValue = intent.getFloatExtra("RATING", 0f)
        ratingTextView.text = "Rating: $ratingValue stars"

        // Assuming checkbox value is passed
        val checkBoxSelection = intent.getStringExtra("CHECKBOX_SELECTION")
        checkBoxSelectionTextView.text = "Checkbox Selection: $checkBoxSelection"
    }
}
