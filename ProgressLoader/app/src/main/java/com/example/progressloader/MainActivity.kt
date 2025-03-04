package com.example.progressloader

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    private lateinit var button1: Button
    private lateinit var progressBar1: ProgressBar
    private var isLoading1 = false
    private var job1: Job? = null

    private lateinit var button2: Button
    private lateinit var progressBar2: ProgressBar
    private var isLoading2 = false
    private var job2: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        button1 = findViewById(R.id.button)
        progressBar1 = findViewById(R.id.progressBar)
        progressBar1.visibility = View.INVISIBLE

        button2 = findViewById(R.id.button2)
        progressBar2 = findViewById(R.id.progressBar2)

        // Set onClickListener for button1 (Intermediate ProgressBar)
        button1.setOnClickListener {
            if (isLoading1) {
                resetLoading1()
            } else {
                startLoading1()
            }
        }

        // Set onClickListener for button2 (Result-Based ProgressBar)
        button2.setOnClickListener {
            if (isLoading2) {
                resetLoading2()
            } else {
                startLoading2()
            }
        }
    }

    private fun startLoading1() {
        isLoading1 = true
        button1.text = "Reset"
        progressBar1.visibility = View.VISIBLE // Make visible
        progressBar1.isIndeterminate = true // Start indeterminate loading
    }

    private fun resetLoading1() {
        isLoading1 = false
        button1.text = "Click to Load"
        progressBar1.visibility = View.INVISIBLE // Make invisible
        progressBar1.isIndeterminate = false // Stop indeterminate loading
    }

    private fun startLoading2() {
        isLoading2 = true
        button2.text = "Reset"
        progressBar2.max = 100
        job2 = CoroutineScope(Dispatchers.Main).launch {
            for (progress in 1..100) {
                progressBar2.progress = progress
                delay(10)
            }
            resetLoading2()
        }
    }

    private fun resetLoading2() {
        isLoading2 = false
        button2.text = "Click to Load"
        progressBar2.progress = 0
        job2?.cancel()
    }
}
