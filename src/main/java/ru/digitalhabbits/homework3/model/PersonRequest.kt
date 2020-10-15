package ru.digitalhabbits.homework3.model

data class PersonRequest(
    var firstName: String,
    var middleName: String? = null,
    var lastName: String,
    var age: Int? = null
)