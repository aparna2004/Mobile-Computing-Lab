package com.example.ca01

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.RatingBar
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Form2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_form2)


        // Using Intent
        val name = intent.getStringExtra("NAME")
        val email = intent.getStringExtra("EMAIL")
        val salary = intent.getStringExtra("SALARY")
        val occupation = intent.getStringExtra("OCCUPATION")
        val gender = intent.getStringExtra("GENDER")

        // Using SharedPreferences data
        val sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE)
        val savedName = sharedPreferences.getString("name", "Default Name")
        val savedEmail = sharedPreferences.getString("email", "Default Email")
        val savedSalary = sharedPreferences.getString("salary", "Default Salary")

        // Delete a stored key
        val editor = sharedPreferences.edit()
        editor.remove("salary")
        editor.apply()

        // Get references to the CheckBoxes and RatingBar
        val checkBox1 = findViewById<CheckBox>(R.id.checkBox)
        val checkBox2 = findViewById<CheckBox>(R.id.checkBox2)

        checkBox1.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                checkBox2.isChecked = false // Uncheck the other checkbox
            }
        }

        checkBox2.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                checkBox1.isChecked = false // Uncheck the other checkbox
            }
        }

        val ratingBar = findViewById<RatingBar>(R.id.ratingBar)
        ratingBar.setOnRatingBarChangeListener { _, rating, _ ->
            Toast.makeText(this, "You rated: $rating stars", Toast.LENGTH_SHORT).show()
        }

        val submitBtn = findViewById<Button>(R.id.button)
        submitBtn.setOnClickListener {
            val intent = Intent(this, ViewForm::class.java)

            // Pass data as Intent extras
            intent.putExtra("NAME", name)
            intent.putExtra("EMAIL", email)
            intent.putExtra("SALARY", salary)
            intent.putExtra("OCCUPATION", occupation)
            intent.putExtra("GENDER", gender)

            // Rating
            val ratingValue = ratingBar.rating
            intent.putExtra("RATING", ratingValue)

            // Checkbox selection
            val selectedCheckbox = when {
                checkBox1.isChecked -> "Yes"
                checkBox2.isChecked -> "No"
                else -> "None"
            }
            intent.putExtra("CHECKBOX_SELECTION", selectedCheckbox)

            startActivity(intent)
        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}