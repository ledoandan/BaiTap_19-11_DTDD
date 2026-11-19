package com.example.baitap_1911.network

import com.example.baitap_1911.model.Student


data class StudentDto(
    val fullName: String?,
    val studentCode: String?,
    val email: String?,
    val gpa: Double?,
    val major: String?
)

fun StudentDto.toEntity(): Student {
    val name = fullName.orEmpty().trim()
    val code = studentCode.orEmpty().trim().uppercase()
    val grade = requireNotNull(gpa)
    require(name.isNotEmpty() && code.isNotEmpty() && grade in 0.0..4.0)

    return Student(
        fullName = name,
        studentCode = code,
        email = email.orEmpty().trim().ifBlank { "${code.lowercase()}@ute.udn.vn" },
        gpa = grade,
        major = major.orEmpty().trim().ifBlank { "Công nghệ thông tin" }
    )
}