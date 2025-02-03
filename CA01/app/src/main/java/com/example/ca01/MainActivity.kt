package com.example.ca01

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    // Define SharedPreferences object
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE)

        // Get all inputs
        val name = findViewById<EditText>(R.id.nameText)
        val email = findViewById<EditText>(R.id.emailText)
        val salary = findViewById<EditText>(R.id.salaryText)
        val submitBtn = findViewById<Button>(R.id.button)



        // Spinner setup
        val occupationSpinner = findViewById<Spinner>(R.id.occupationSpinner)
        val occupations = arrayOf("Select one", "A", "B", "C", "D")
        occupationSpinner.onItemSelectedListener = this
        val occupationAdapter = ArrayAdapter<Any?>(this, android.R.layout.simple_spinner_item, occupations)
        occupationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        occupationSpinner.adapter = occupationAdapter

        // Radio setup
        val radioGroup = findViewById<RadioGroup>(R.id.radioGroup)

        submitBtn.setOnClickListener {
            // Get the values from the inputs
            val nameInput = name.text.toString().trim()
            val emailInput = email.text.toString().trim()
            val salaryInput = salary.text.toString().trim()


            if (nameInput.isEmpty()) {
                findViewById<EditText>(R.id.nameText).error = "Name is required"
                return@setOnClickListener
            }

            // Validate Email (non-empty and valid format using regex pattern)
            if (emailInput.isEmpty()) {
                findViewById<EditText>(R.id.emailText).error = "Email is required"
                return@setOnClickListener
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
                findViewById<EditText>(R.id.emailText).error = "Please enter a valid email address"
                return@setOnClickListener
            }

            val salaryPattern = "^[+]?(\\d*\\.?\\d{1,2})$"
            val salaryRegex = Regex(salaryPattern)
            if (salaryInput.isEmpty() || !salaryRegex.matches(salaryInput)) {
                salary.error = "Please enter a valid salary"
                return@setOnClickListener
            }

            val salaryValue = salaryInput.toDouble()
            if (salaryValue <= 0) {
                salary.error = "Salary must be greater than zero"
                return@setOnClickListener
            }


            // Save data in SharedPreferences
            val editor = sharedPreferences.edit()
            editor.putString("name", nameInput)
            editor.putString("email", emailInput)
            editor.putString("salary", salaryInput)
            editor.apply()

            // Get the spinner selection
            val selectedOccupation = occupationSpinner.selectedItem.toString()

            // Get the selected radio button
            val selectedRadioBtnId = radioGroup.checkedRadioButtonId
            val selectedRadioBtn = findViewById<View>(selectedRadioBtnId)

            // Pass the data to the next activity using Intent
            val intent = Intent(this, Form2::class.java)
            intent.putExtra("NAME", nameInput)
            intent.putExtra("EMAIL", emailInput)
            intent.putExtra("SALARY", salaryInput)
            intent.putExtra("OCCUPATION", selectedOccupation)
            intent.putExtra("GENDER", selectedRadioBtn?.tag?.toString())

            startActivity(intent)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onItemSelected(p0: AdapterView<*>?, view: View?, position: Int, id: Long) {
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
    }
}
