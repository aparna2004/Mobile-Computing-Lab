package com.example.ws02

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val imageView: ImageView = findViewById(R.id.imageView)
        val statusTextView: TextView = findViewById(R.id.statusTextView)
        val actionButton: Button = findViewById(R.id.actionButton)

        actionButton.setOnClickListener {
            imageView.setImageResource(R.drawable.full)
            statusTextView.text = "I'm so full"
            actionButton.text = "DONE"
            actionButton.isEnabled = false
        }
    }
}
