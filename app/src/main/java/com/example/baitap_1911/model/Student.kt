package com.example.baitap_1911.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "students_table")
data class Student(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "full_name")
    val fullName: String,

    @ColumnInfo(name = "student_code")
    val studentCode: String,

    val email: String,
    val gpa: Double,
    val major: String = "Công nghệ thông tin"
)