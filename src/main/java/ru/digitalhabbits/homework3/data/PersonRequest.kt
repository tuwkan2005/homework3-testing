package ru.digitalhabbits.homework3.data

import org.apache.commons.lang3.RandomStringUtils.randomAlphabetic
import org.apache.commons.lang3.RandomUtils.nextInt
import ru.digitalhabbits.homework3.model.PersonRequest

val PERSON_REQUEST = PersonRequest(
    firstName = randomAlphabetic(10),
    middleName = randomAlphabetic(10),
    lastName = randomAlphabetic(10),
    age = nextInt(18, 65)
)