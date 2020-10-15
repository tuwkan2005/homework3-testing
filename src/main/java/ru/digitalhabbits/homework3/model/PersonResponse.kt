package ru.digitalhabbits.homework3.model

data class PersonResponse(
    var id: Int,
    val fullName: String,
    val age: Int?,
    val department: DepartmentInfo?
)