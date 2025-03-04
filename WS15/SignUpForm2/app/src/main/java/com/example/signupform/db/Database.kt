package com.example.signupform.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class Database(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    companion object {
        private const val DB_NAME = "SignupForm.db"
        private const val DB_VERSION = 1
        private const val TABLE_USERS = "Users"

        private const val COL_ID = "id"
        private const val COL_FIRST_NAME = "firstName"
        private const val COL_EMAIL = "email"
        private const val COL_DOB = "dateOfBirth"
    }

    override fun onCreate(db: SQLiteDatabase?) {

        val createTable = """
            CREATE TABLE $TABLE_USERS (
                $COL_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COL_FIRST_NAME TEXT NOT NULL,
                $COL_EMAIL TEXT NOT NULL,
                $COL_DOB TEXT NOT NULL
            )
        """.trimIndent()
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_USERS")
        onCreate(db)
    }

    fun addUser(firstName: String, email: String, dateOfBirth: String): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COL_FIRST_NAME, firstName)
            put(COL_EMAIL, email)
            put(COL_DOB, dateOfBirth)
        }
        val id = db.insert(TABLE_USERS, null, values)
        db.close()
        return id // Returns the row ID, -1 if failed
    }

    fun getUserById(userId: String): User? {
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_USERS WHERE $COL_ID = ?", arrayOf(userId))
        var user: User? = null

        if (cursor.moveToFirst()) {
            user = User(
                id = cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID)),
                firstName = cursor.getString(cursor.getColumnIndexOrThrow(COL_FIRST_NAME)),
                email = cursor.getString(cursor.getColumnIndexOrThrow(COL_EMAIL)),
                dateOfBirth = cursor.getString(cursor.getColumnIndexOrThrow(COL_DOB))
            )
        }

        cursor.close()
        db.close()
        return user
    }
}
