package com.example.cookiemonster

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
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

        var state = "hungry"
        val btn = findViewById<Button>(R.id.button)
        val desc = findViewById<TextView>(R.id.textView2)
        val image = findViewById<ImageView>(R.id.imageView)

        btn.setOnClickListener {
            if (state == "hungry") {
                desc.text = getString(R.string.im_so_full)
                image.setImageResource(R.drawable.full)
                btn.text = getString(R.string.done)
                state = "full"
            }
            else {
                desc.text = getString(R.string.im_so_hungry)
                image.setImageResource(R.drawable.hungry)
                btn.text = getString(R.string.eat_cookie)
                state = "hungry"
            }
        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}