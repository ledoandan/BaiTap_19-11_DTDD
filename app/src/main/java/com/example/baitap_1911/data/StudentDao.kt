package com.example.baitap_1911.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.baitap_1911.model.Student
import kotlinx.coroutines.flow.Flow

@Dao
interface StudentDao {
    @Insert
    suspend fun insertStudent(student: Student): Long

    @Update
    suspend fun updateStudent(student: Student): Int

    @Delete
    suspend fun deleteStudent(student: Student): Int

    @Query("SELECT * FROM students_table ORDER BY id DESC")
    fun getAllStudents(): Flow<List<Student>>

    @Query(
        "SELECT * FROM students_table " +
                "WHERE student_code = :code COLLATE NOCASE ORDER BY id LIMIT 1"
    )
    suspend fun getStudentByCode(code: String): Student?
}