package ru.digitalhabbits.homework3.model

data class DepartmentResponse(
    var id: Int,
    val name: String,
    val closed: Boolean,
    val persons: List<PersonInfo>?
)