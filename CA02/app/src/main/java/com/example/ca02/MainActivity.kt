package com.example.ca02

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.ProgressBar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    // Boolean flag to prevent multiple clicks on "Start" button
    private var isLoading = false

    // UI elements
    private lateinit var startBtn: Button  // Button to start the form
    private lateinit var ctrlPanelBtn: Button  // Button to open Control Panel
    private lateinit var loader: ProgressBar  // Progress bar to show loading state

    // Handler to introduce delay before starting the next activity
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Enables edge-to-edge display for modern UI appearance
        enableEdgeToEdge()

        // Connects this activity with its XML layout file
        setContentView(R.layout.activity_main)

        // Initialize UI elements
        startBtn = findViewById(R.id.startBtn)
        ctrlPanelBtn = findViewById(R.id.ctrlPanelBtn)
        loader = findViewById(R.id.loader)

        // When the "Control Panel" button is clicked, navigate to ControlPanelActivity
        ctrlPanelBtn.setOnClickListener {
            startActivity(Intent(this, ControlPanelActivity::class.java))
        }

        // When the "Start" button is clicked
        startBtn.setOnClickListener {
            // Ensure that the action is not triggered multiple times
            if (!isLoading) {
                isLoading = true // Mark that loading is in progress
                startBtn.text = "Loading" // Change button text to indicate loading
                loader.visibility = ProgressBar.VISIBLE // Show the progress bar

                // Delay execution for 2 seconds before moving to the next activity
                handler.postDelayed({
                    startActivity(Intent(this, Section1Activity::class.java)) // Navigate to Section1Activity
                    isLoading = false // Reset loading state
                    startBtn.text = "Start" // Reset button text
                    loader.visibility = ProgressBar.INVISIBLE // Hide the progress bar
                }, 2000) // 2000ms (2 seconds) delay
            }
        }
    }
}
