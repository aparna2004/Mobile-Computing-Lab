package com.example.feedbackapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RatingBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)


        val ratingBar = findViewById<RatingBar>(R.id.ratingBar)
        val ratingTextView = findViewById<TextView>(R.id.textView2)

        ratingBar.setOnRatingBarChangeListener { _, selectedRating, _ ->
            val rating = selectedRating.toInt()
            val ratingReview = when (rating) {
                1 -> "Disappointed. Very poor"
                2 -> "Not good. Need improvement"
                3 -> "Satisfied"
                4 -> "Good. Enjoyed it"
                5 -> "Awesome. I love it"
                else -> "Satisfied"
            }
            ratingTextView.text = ratingReview
        }

        val submitBtn = findViewById<Button>(R.id.button)

        submitBtn.setOnClickListener {
            val userReview = findViewById<EditText>(R.id.editTextText).text.toString().trim()
            val intent = Intent(this, FeedbackScreen::class.java)
            intent.putExtra("USER_REVIEW", userReview)
            startActivity(intent)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}