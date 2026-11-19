package com.example.baitap_1911.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.baitap_1911.databinding.ItemStudentRoomBinding
import com.example.baitap_1911.model.Student

import java.util.Locale

class StudentAdapter(
    private val onStudentClick: (Student) -> Unit,
    private val onDeleteClick: (Student) -> Unit
) : ListAdapter<Student, StudentAdapter.StudentViewHolder>(StudentDiff) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val binding = ItemStudentRoomBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return StudentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class StudentViewHolder(
        private val binding: ItemStudentRoomBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(student: Student) {
            binding.tvFullName.text = student.fullName
            binding.tvStudentCode.text = "MSSV: ${student.studentCode}"
            binding.tvEmail.text = student.email
            binding.tvGpa.text = String.format(Locale.getDefault(), "GPA: %.2f", student.gpa)
            binding.tvAvatar.text = student.fullName.trim().take(1).uppercase()
            binding.btnDelete.contentDescription = "Xóa ${student.fullName}"

            binding.root.setOnClickListener { onStudentClick(student) }
            binding.btnDelete.setOnClickListener { onDeleteClick(student) }
        }
    }

    private object StudentDiff : DiffUtil.ItemCallback<Student>() {
        override fun areItemsTheSame(oldItem: Student, newItem: Student) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Student, newItem: Student) =
            oldItem == newItem
    }
}