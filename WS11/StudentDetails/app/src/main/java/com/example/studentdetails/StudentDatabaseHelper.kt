package com.example.studentdetails

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class StudentDatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "StudentDatabase"
        private const val DATABASE_VERSION = 1
        const val TABLE_NAME = "Students"
        const val COLUMN_ROLL_NO = "roll_no"
        const val COLUMN_NAME = "name"
        const val COLUMN_MARKS = "marks"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTableQuery = """
            CREATE TABLE $TABLE_NAME (
                $COLUMN_ROLL_NO INTEGER PRIMARY KEY,
                $COLUMN_NAME TEXT,
                $COLUMN_MARKS REAL
            )
        """
        db.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Handle database schema upgrades here if needed.
        // For simplicity, we'll just drop the table and recreate it.
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    // --- CRUD Operations ---

    fun insertStudent(rollNo: Int, name: String, marks: Double): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_ROLL_NO, rollNo)
            put(COLUMN_NAME, name)
            put(COLUMN_MARKS, marks)
        }
        return db.insert(TABLE_NAME, null, values) // Returns the row ID of the newly inserted row, or -1 if an error occurred
    }

    fun getStudent(rollNo: Int): Student? {
        val db = this.readableDatabase
        val cursor = db.query(
            TABLE_NAME,
            arrayOf(COLUMN_ROLL_NO, COLUMN_NAME, COLUMN_MARKS),
            "$COLUMN_ROLL_NO = ?",
            arrayOf(rollNo.toString()),
            null,
            null,
            null
        )

        var student: Student? = null
        cursor.use {
            if (it.moveToFirst()) {
                val name = it.getString(it.getColumnIndexOrThrow(COLUMN_NAME))
                val marks = it.getDouble(it.getColumnIndexOrThrow(COLUMN_MARKS))
                student = Student(rollNo, name, marks)
            }
        }
        return student
    }

    fun updateStudent(rollNo: Int, name: String, marks: Double): Int {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, name)
            put(COLUMN_MARKS, marks)
        }

        return db.update(
            TABLE_NAME,
            values,
            "$COLUMN_ROLL_NO = ?",
            arrayOf(rollNo.toString())
        ) // Returns the number of rows affected
    }

    fun deleteStudent(rollNo: Int): Int {
        val db = this.writableDatabase
        return db.delete(
            TABLE_NAME,
            "$COLUMN_ROLL_NO = ?",
            arrayOf(rollNo.toString())
        ) // Returns the number of rows affected
    }

    fun getAllStudents(): MutableList<Student> {
        val studentList = mutableListOf<Student>()
        val db = this.readableDatabase
        val cursor = db.query(
            TABLE_NAME,
            arrayOf(COLUMN_ROLL_NO, COLUMN_NAME, COLUMN_MARKS),
            null,
            null,
            null,
            null,
            null
        )

        cursor.use {
            if (it.moveToFirst()) {
                do {
                    val rollNo = it.getInt(it.getColumnIndexOrThrow(COLUMN_ROLL_NO))
                    val name = it.getString(it.getColumnIndexOrThrow(COLUMN_NAME))
                    val marks = it.getDouble(it.getColumnIndexOrThrow(COLUMN_MARKS))
                    val student = Student(rollNo, name, marks)
                    studentList.add(student)
                } while (it.moveToNext())
            }
        }
        return studentList
    }
}
