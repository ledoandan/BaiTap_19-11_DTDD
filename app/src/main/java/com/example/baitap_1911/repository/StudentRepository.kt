package com.ute.studentmvvm.repository

import StudentApiService
import androidx.room.withTransaction
import com.example.baitap_1911.data.AppDatabase
import com.example.baitap_1911.model.Student
import com.example.baitap_1911.network.RetrofitClient
import com.example.baitap_1911.network.toEntity

class StudentRepository(
    private val database: AppDatabase,
    private val apiService: StudentApiService = RetrofitClient.apiService
) {
    private val dao = database.studentDao()
    val allStudents = dao.getAllStudents()

    suspend fun insert(student: Student): Boolean = database.withTransaction {
        if (dao.getStudentByCode(student.studentCode) != null) {
            false
        } else {
            dao.insertStudent(student)
            true
        }
    }

    suspend fun delete(student: Student): Int = dao.deleteStudent(student)

    suspend fun syncFromCloud(): Int {
        val students = apiService.getStudents()
            .map { it.toEntity() }
            .distinctBy { it.studentCode }

        database.withTransaction {
            for (student in students) {
                val current = dao.getStudentByCode(student.studentCode)
                if (current == null) {
                    dao.insertStudent(student)
                } else {
                    dao.updateStudent(student.copy(id = current.id))
                }
            }
        }
        return students.size
    }
}