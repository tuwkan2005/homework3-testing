package ru.digitalhabbits.homework3.data

import org.apache.commons.lang3.RandomStringUtils.randomAlphabetic
import ru.digitalhabbits.homework3.model.DepartmentRequest

val DEPARTMENT_REQUEST = DepartmentRequest(
    name = randomAlphabetic(10)
)