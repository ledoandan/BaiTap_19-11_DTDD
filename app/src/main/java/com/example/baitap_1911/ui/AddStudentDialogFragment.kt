package com.example.baitap_1911.ui

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.baitap_1911.MainActivity
import com.example.baitap_1911.databinding.DialogAddStudentBinding
import com.example.baitap_1911.model.Student
import com.example.baitap_1911.viewmodel.StudentViewModel

class AddStudentDialogFragment : DialogFragment() {

    private val viewModel: StudentViewModel by activityViewModels {
        (requireActivity() as MainActivity).studentViewModelFactory
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())
        val binding = DialogAddStudentBinding.inflate(
            LayoutInflater.from(builder.context)
        )

        val dialog = builder
            .setTitle("Thêm sinh viên mới")
            .setView(binding.root)
            .setPositiveButton("Lưu vào Room", null)
            .setNegativeButton("Hủy", null)
            .create()

        dialog.setOnShowListener {
            dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener {
                val student = readStudent(binding) ?: return@setOnClickListener
                viewModel.insert(student)
                dismiss()
            }
        }

        return dialog
    }

    private fun readStudent(binding: DialogAddStudentBinding): Student? {
        val name = binding.etFullName.text.toString().trim()
        val code = binding.etStudentCode.text.toString().trim().uppercase()
        val email = binding.etEmail.text.toString().trim()
            .ifBlank { "${code.lowercase()}@ute.udn.vn" }
        val gpa = binding.etGpa.text.toString().trim()
            .replace(',', '.')
            .toDoubleOrNull()

        if (name.isEmpty()) {
            binding.etFullName.error = "Nhập họ và tên."
            return null
        }
        if (code.isEmpty()) {
            binding.etStudentCode.error = "Nhập mã số sinh viên."
            return null
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.etEmail.error = "Nhập email hợp lệ."
            return null
        }
        if (gpa == null || gpa !in 0.0..4.0) {
            binding.etGpa.error = "Nhập GPA từ 0 đến 4."
            return null
        }

        return Student(
            fullName = name,
            studentCode = code,
            email = email,
            gpa = gpa
        )
    }
}