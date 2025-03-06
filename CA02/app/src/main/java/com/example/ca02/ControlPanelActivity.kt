package com.example.ca02

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.ca02.db.Database
import com.example.ca02.db.User

class ControlPanelActivity : AppCompatActivity() {

    // UI Elements
    private lateinit var userListView: ListView // ListView to display users
    private lateinit var db: Database // Database instance
    private lateinit var userList: MutableList<User> // List to store users
    private lateinit var adapter: ArrayAdapter<User> // Adapter for ListView
    private lateinit var backBtn: Button // Button to go back to MainActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ctrl_panel)

        // Initialize Back Button
        backBtn = findViewById(R.id.button)
        backBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        // Initialize ListView and Database
        userListView = findViewById(R.id.userListView)
        db = Database(this)

        // Load users into the ListView
        loadUsers()
    }

    // Function to load all users from the database and display them in the ListView
    private fun loadUsers() {
        userList = db.getAllUsers().toMutableList() // Fetch all users from the database

        // Custom adapter to populate the ListView with user details
        adapter = object : ArrayAdapter<User>(this, R.layout.list_item_user, userList) {
            override fun getView(position: Int, convertView: android.view.View?, parent: android.view.ViewGroup): android.view.View {
                val view = layoutInflater.inflate(R.layout.list_item_user, parent, false)

                // UI elements inside each list item
                val userInfo = view.findViewById<TextView>(R.id.userInfo)
                val updateBtn = view.findViewById<Button>(R.id.updateBtn)
                val deleteBtn = view.findViewById<Button>(R.id.deleteBtn)

                // Get user details for the current position
                val user = userList[position]
                userInfo.text = "${user.id}: ${user.name} (${user.email})" // Display user ID, name, and email

                // Handle Update button click
                updateBtn.setOnClickListener {
                    updateUser(user.id)
                }

                // Handle Delete button click
                deleteBtn.setOnClickListener {
                    deleteUser(user.id)
                }

                return view
            }
        }

        // Set the adapter to the ListView
        userListView.adapter = adapter
    }

    // Function to update a user by navigating to Section3Activity
    private fun updateUser(userId: Long) {
        val prefs = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        prefs.edit().putLong("userId", userId).apply() // Store user ID in SharedPreferences

        val intent = Intent(this, Section3Activity::class.java) // Navigate to user update page
        startActivity(intent)
    }

    // Function to delete a user from the database
    private fun deleteUser(userId: Long) {
        val success = db.deleteUser(userId) // Delete user from the database
        if (success) {
            Toast.makeText(this, "User deleted successfully!", Toast.LENGTH_SHORT).show()
            loadUsers() // Refresh the user list after deletion
        } else {
            Toast.makeText(this, "Failed to delete user.", Toast.LENGTH_SHORT).show()
        }
    }
}
