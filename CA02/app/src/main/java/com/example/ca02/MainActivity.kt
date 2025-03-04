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

    private var isLoading = false
    private lateinit var startBtn: Button
    private lateinit var loader: ProgressBar
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        startBtn = findViewById(R.id.startBtn)
        loader = findViewById(R.id.loader)

        startBtn.setOnClickListener {
            if (!isLoading) {
                isLoading = true
                startBtn.text = "Loading"
                loader.visibility = ProgressBar.VISIBLE

                handler.postDelayed({
                    startActivity(Intent(this, Section1Activity::class.java))
                    isLoading = false
                    startBtn.text = "Start"
                    loader.visibility = ProgressBar.INVISIBLE
                }, 5000)
            }
        }
    }
}
