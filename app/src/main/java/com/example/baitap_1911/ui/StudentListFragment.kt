package com.example.baitap_1911.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.baitap_1911.MainActivity
import com.example.baitap_1911.R
import com.example.baitap_1911.databinding.FragmentStudentListBinding
import com.example.baitap_1911.model.Student
import com.example.baitap_1911.ui.adapter.StudentAdapter
import com.example.baitap_1911.viewmodel.StudentViewModel

class StudentListFragment : Fragment(R.layout.fragment_student_list) {
    private var _binding: FragmentStudentListBinding? = null

    private val viewModel: StudentViewModel by activityViewModels {
        (requireActivity() as MainActivity).studentViewModelFactory
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentStudentListBinding.bind(view)
        _binding = binding

        val adapter = StudentAdapter(::showDetail, ::confirmDelete)
        adapter.stateRestorationPolicy =
            RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY

        binding.rvStudents.layoutManager = LinearLayoutManager(requireContext())
        binding.rvStudents.adapter = adapter

        viewModel.allStudents.observe(viewLifecycleOwner) { students ->
            adapter.submitList(students)
            binding.layoutEmpty.isVisible = students.isEmpty()
            binding.tvStudentCountBadge.text = "${students.size} sinh viên"
        }

        viewModel.statusMessage.observe(viewLifecycleOwner) { message ->
            if (message != null) {
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                viewModel.clearStatusMessage()
            }
        }

        binding.fabAdd.setOnClickListener {
            AddStudentDialogFragment().show(childFragmentManager, "ADD_STUDENT_DIALOG")
        }

        binding.toolbar.setOnMenuItemClickListener { item ->
            if (item.itemId == R.id.action_sync) {
                viewModel.syncFromCloud()
                true
            } else {
                false
            }
        }
    }

    private fun confirmDelete(student: Student) {
        AlertDialog.Builder(requireContext())
            .setTitle("Xác nhận xóa")
            .setMessage("Xóa sinh viên ${student.fullName}?")
            .setPositiveButton("Xóa") { _, _ -> viewModel.delete(student) }
            .setNegativeButton("Hủy", null)
            .show()
    }

    private fun showDetail(student: Student) {
        val details = """
            ID: ${student.id}
            Họ tên: ${student.fullName}
            MSSV: ${student.studentCode}
            Email: ${student.email}
            GPA: ${student.gpa}
            Ngành: ${student.major}
        """.trimIndent()

        AlertDialog.Builder(requireContext())
            .setTitle("Thông tin sinh viên")
            .setMessage(details)
            .setPositiveButton("Đóng", null)
            .show()
    }

    override fun onDestroyView() {
        _binding?.rvStudents?.adapter = null
        _binding = null
        super.onDestroyView()
    }
}