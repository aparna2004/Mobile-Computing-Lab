package com.example.ca02.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.content.ContentValues

// SQLiteOpenHelper class to manage database creation and version management
class Database(context: Context) : SQLiteOpenHelper(context, "CA02DB", null, 1) {

    // Called when the database is first created
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            "CREATE TABLE Users (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " + // Auto-incrementing ID
                    "name TEXT, " + // User's name
                    "email TEXT, " + // User's email
                    "dob TEXT)" // User's date of birth
        )
    }

    // Called when the database version is upgraded
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS Users") // Delete existing table
        onCreate(db) // Recreate the table
    }

    // Function to add a new user to the database
    fun addUser(name: String, email: String, dob: String): Long {
        return writableDatabase.use { db ->
            val values = ContentValues().apply {
                put("name", name)
                put("email", email)
                put("dob", dob)
            }
            db.insert("Users", null, values) // Insert new user and return the row ID
        }
    }

    // Function to retrieve a user by their ID
    fun getUserById(userId: Long): User? {
        return readableDatabase.use { db ->
            db.rawQuery("SELECT * FROM Users WHERE id = ?", arrayOf(userId.toString())).use { cursor ->
                if (cursor.moveToFirst()) {
                    User(
                        cursor.getLong(0), // ID
                        cursor.getString(1), // Name
                        cursor.getString(2), // Email
                        cursor.getString(3)  // Date of Birth
                    )
                } else null
            }
        }
    }

    // Function to update user details in the database
    fun updateUser(user: User): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("name", user.name)
            put("email", user.email)
            put("dob", user.dob)
        }

        val rowsUpdated = db.update("Users", values, "id=?", arrayOf(user.id.toString()))
        db.close() // Close the database connection
        return rowsUpdated > 0 // Return true if update was successful
    }

    // Function to delete a user from the database
    fun deleteUser(userId: Long): Boolean {
        val db = writableDatabase
        val rowsDeleted = db.delete("Users", "id=?", arrayOf(userId.toString()))
        db.close() // Close the database connection
        return rowsDeleted > 0 // Return true if deletion was successful
    }

    // Function to retrieve all users in the database
    fun getAllUsers(): List<User> {
        val userList = mutableListOf<User>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM Users", null)

        while (cursor.moveToNext()) {
            val id = cursor.getLong(cursor.getColumnIndexOrThrow("id"))
            val name = cursor.getString(cursor.getColumnIndexOrThrow("name"))
            val email = cursor.getString(cursor.getColumnIndexOrThrow("email"))
            val dob = cursor.getString(cursor.getColumnIndexOrThrow("dob"))

            userList.add(User(id, name, email, dob))
        }
        cursor.close() // Close cursor to prevent memory leaks
        return userList // Return the list of users
    }
}

// Data class representing a User object
data class User(
    val id: Long,   // User ID
    val name: String,  // User Name
    val email: String, // User Email
    val dob: String    // User Date of Birth
)
