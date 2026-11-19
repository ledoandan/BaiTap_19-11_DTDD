package com.example.baitap_1911.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.baitap_1911.model.Student
import com.example.baitap_1911.repository.StudentRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class StudentViewModel(private val repository: StudentRepository) : ViewModel() {
    val allStudents = repository.allStudents.asLiveData(Dispatchers.IO)

    private val _statusMessage = MutableLiveData<String?>()
    val statusMessage: LiveData<String?> = _statusMessage
    private var syncJob: Job? = null

    fun insert(student: Student) = runOperation("Lỗi khi thêm sinh viên.") {
        if (repository.insert(student)) {
            "Đã thêm ${student.fullName}"
        } else {
            "Mã sinh viên đã tồn tại."
        }
    }

    fun delete(student: Student) = runOperation("Lỗi khi xóa sinh viên.") {
        repository.delete(student)
        "Đã xóa ${student.fullName}"
    }

    fun syncFromCloud() {
        if (syncJob?.isActive == true) return
        syncJob = runOperation("Đồng bộ thất bại. Hãy kiểm tra API.") {
            "Đã đồng bộ ${repository.syncFromCloud()} sinh viên từ API."
        }
    }

    fun clearStatusMessage() {
        _statusMessage.value = null
    }

    private fun runOperation(errorMessage: String, action: suspend () -> String): Job =
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _statusMessage.postValue(action())
            } catch (exception: CancellationException) {
                throw exception
            } catch (exception: Exception) {
                _statusMessage.postValue(errorMessage)
            }
        }
}