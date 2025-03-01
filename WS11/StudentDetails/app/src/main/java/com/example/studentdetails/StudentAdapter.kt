package com.example.studentdetails

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class StudentAdapter(private val studentList: List<Student>) :
    RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {

    class StudentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val rollNoTextView: TextView = itemView.findViewById(R.id.rollNoTextView)
        val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        val marksTextView: TextView = itemView.findViewById(R.id.marksTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.student_item_layout, parent, false)
        return StudentViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val currentItem = studentList[position]
        holder.rollNoTextView.text = "Roll No: ${currentItem.rollNo}"
        holder.nameTextView.text = "Name: ${currentItem.name}"
        holder.marksTextView.text = "Marks: ${currentItem.marks}"
    }

    override fun getItemCount() = studentList.size
}
