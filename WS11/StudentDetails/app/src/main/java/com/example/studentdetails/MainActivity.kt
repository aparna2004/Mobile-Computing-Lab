package com.example.studentdetails

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.PopupWindow
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var rollNoText: EditText
    private lateinit var nameText: EditText
    private lateinit var marksText: EditText
    private lateinit var insertBtn: Button
    private lateinit var deleteBtn: Button
    private lateinit var updateBtn: Button
    private lateinit var viewBtn: Button
    private lateinit var viewAllBtn: Button
    private lateinit var dbHelper: StudentDatabaseHelper
    private lateinit var dimBackground: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Initialize views
        rollNoText = findViewById(R.id.rollNoEditText)
        nameText = findViewById(R.id.nameEditText)
        marksText = findViewById(R.id.marksEditText)
        insertBtn = findViewById(R.id.insertBtn)
        deleteBtn = findViewById(R.id.deleteBtn)
        updateBtn = findViewById(R.id.updateBtn)
        viewBtn = findViewById(R.id.viewBtn)
        viewAllBtn = findViewById(R.id.viewAllBtn)

        // Initialize dimBackground
        dimBackground = findViewById(R.id.dimBackground)

        // Initialize the database helper
        dbHelper = StudentDatabaseHelper(this)

        // Set click listeners
        insertBtn.setOnClickListener { insertStudent() }
        deleteBtn.setOnClickListener { deleteStudent() }
        updateBtn.setOnClickListener { updateStudent() }
        viewBtn.setOnClickListener { viewStudent() }
        viewAllBtn.setOnClickListener { viewAllStudents() }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onDestroy() {
        dbHelper.close()
        super.onDestroy()
    }

    private fun insertStudent() {
        val rollNo = rollNoText.text.toString().toIntOrNull()
        val name = nameText.text.toString()
        val marks = marksText.text.toString().toDoubleOrNull()

        if (rollNo == null || name.isEmpty() || marks == null) {
            showPopupMessage(message = "Please enter valid data") // Pass null for non-student details
            return
        }

        val result = dbHelper.insertStudent(rollNo, name, marks)
        if (result > 0) {
            showPopupMessage(message = "Student inserted successfully") // Pass null for non-student details
            clearFields()
        } else {
            showPopupMessage(message = "Failed to insert student") // Pass null for non-student details
        }
    }

    private fun viewStudent() {
        val rollNo = rollNoText.text.toString().toIntOrNull()
        if (rollNo == null) {
            showPopupMessage(message = "Please enter a valid roll number") // Pass null for non-student details
            return
        }

        val student = dbHelper.getStudent(rollNo)
        if (student != null) {
            nameText.setText(student.name)
            marksText.setText(student.marks.toString())
            // showPopupMessage(null, student) //Pass student Details, will show only one student so commenting it out.
            val studentList = listOf(student)
            showPopupMessage(message = "Student Details", studentList = studentList)
        } else {
            showPopupMessage(message = "Student not found") // Pass null for non-student details
            clearFields()
        }
    }

    private fun updateStudent() {
        val rollNo = rollNoText.text.toString().toIntOrNull()
        val name = nameText.text.toString()
        val marks = marksText.text.toString().toDoubleOrNull()

        if (rollNo == null || name.isEmpty() || marks == null) {
            showPopupMessage(message = "Please enter valid data") // Pass null for non-student details
            return
        }

        val rowsAffected = dbHelper.updateStudent(rollNo, name, marks)
        if (rowsAffected > 0) {
            showPopupMessage(message = "Student updated successfully") // Pass null for non-student details
            clearFields()
        } else {
            showPopupMessage(message = "Failed to update student") // Pass null for non-student details
        }
    }

    private fun deleteStudent() {
        val rollNo = rollNoText.text.toString().toIntOrNull()
        if (rollNo == null) {
            showPopupMessage(message = "Please enter a valid roll number") // Pass null for non-student details
            return
        }

        val rowsAffected = dbHelper.deleteStudent(rollNo)
        if (rowsAffected > 0) {
            showPopupMessage(message = "Student deleted successfully") // Pass null for non-student details
            clearFields()
        } else {
            showPopupMessage(message = "Failed to delete student") // Pass null for non-student details
        }
    }

    private fun viewAllStudents() {
        val students = dbHelper.getAllStudents()
        if (students.isEmpty()) {
            showPopupMessage(message = "No students found") // Pass null for non-student details
        } else {
            showPopupMessage(message = "All Student Details", studentList = students)
        }
    }

    private fun clearFields() {
        rollNoText.text.clear()
        nameText.text.clear()
        marksText.text.clear()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun showPopupMessage(message: String?, studentList: List<Student>?=null) {
        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val popupView = inflater.inflate(R.layout.popup_layout, null)

        val popupTitleTextView = popupView.findViewById<TextView>(R.id.popupTitleTextView)
        val studentRecyclerView = popupView.findViewById<RecyclerView>(R.id.studentRecyclerView)

        popupTitleTextView.text = message ?: "Message" // Set the title

        if (studentList != null && studentList.isNotEmpty()) {
            // Initialize RecyclerView
            studentRecyclerView.layoutManager = LinearLayoutManager(this)
            val adapter = StudentAdapter(studentList)
            studentRecyclerView.adapter = adapter
            studentRecyclerView.visibility = View.VISIBLE
        } else {
            studentRecyclerView.visibility = View.GONE
            // If the message needs to be shown
            popupTitleTextView.text = message ?: ""
        }

        val popupWindow = PopupWindow(
            popupView,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            true
        )

        popupWindow.setBackgroundDrawable(ColorDrawable(Color.WHITE))
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            popupWindow.elevation = 10f
        }

        dimBackground.visibility = View.VISIBLE

        popupView.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                popupWindow.dismiss()
                dimBackground.visibility = View.GONE
                true
            } else {
                false
            }
        }

        popupWindow.setOnDismissListener {
            dimBackground.visibility = View.GONE
        }

        popupWindow.showAtLocation(
            findViewById(R.id.main),
            Gravity.CENTER,
            0,
            0
        )
    }

    private fun showPopupMessage(message: String?, student: Student?) {
        val studentList = if(student != null) listOf(student) else null
        showPopupMessage(message = message, studentList = studentList)
    }


}
