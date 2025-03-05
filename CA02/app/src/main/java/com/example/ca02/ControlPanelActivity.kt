package com.example.ca02

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.ca02.db.Database
import com.example.ca02.db.User

class ControlPanelActivity : AppCompatActivity() {

    private lateinit var userListView: ListView
    private lateinit var db: Database
    private lateinit var userList: MutableList<User>
    private lateinit var adapter: ArrayAdapter<User>
    private lateinit var backBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ctrl_panel)

        backBtn = findViewById(R.id.button)
        backBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        userListView = findViewById(R.id.userListView)
        db = Database(this)
        loadUsers()
    }

    private fun loadUsers() {
        userList = db.getAllUsers().toMutableList()

        adapter = object : ArrayAdapter<User>(this, R.layout.list_item_user, userList) {
            override fun getView(position: Int, convertView: android.view.View?, parent: android.view.ViewGroup): android.view.View {
                val view = layoutInflater.inflate(R.layout.list_item_user, parent, false)

                val userInfo = view.findViewById<TextView>(R.id.userInfo)
                val updateBtn = view.findViewById<Button>(R.id.updateBtn)
                val deleteBtn = view.findViewById<Button>(R.id.deleteBtn)

                val user = userList[position]
                userInfo.text = "${user.id}: ${user.name} (${user.email})"

                updateBtn.setOnClickListener {
                    updateUser(user.id)
                }

                deleteBtn.setOnClickListener {
                    deleteUser(user.id)
                }

                return view
            }
        }

        userListView.adapter = adapter
    }

    private fun updateUser(userId: Long) {
        val prefs = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        prefs.edit().putLong("userId", userId).apply()

        val intent = Intent(this, Section3Activity::class.java)
        startActivity(intent)
    }

    private fun deleteUser(userId: Long) {
        val success = db.deleteUser(userId)
        if (success) {
            Toast.makeText(this, "User deleted successfully!", Toast.LENGTH_SHORT).show()
            loadUsers() // Refresh the list
        } else {
            Toast.makeText(this, "Failed to delete user.", Toast.LENGTH_SHORT).show()
        }
    }
}
