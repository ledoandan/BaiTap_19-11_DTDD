package com.example.baitap_1911.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.baitap_1911.repository.StudentRepository

class StudentViewModelFactory(
    private val repository: StudentRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StudentViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return StudentViewModel(repository) as T
        }
        throw IllegalArgumentException("ViewModel chưa được hỗ trợ: ${modelClass.name}")
    }
}