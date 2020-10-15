package ru.digitalhabbits.homework3.web

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import ru.digitalhabbits.homework3.data.PERSON_REQUEST
import ru.digitalhabbits.homework3.model.PersonRequest

internal class PersonControllerTest {

    @Test
    @DisplayName("Persons CRUD operations")
    fun crudOperationsTest() {
        val controller = PersonController()

        var request = PERSON_REQUEST
        val personId = controller.createPerson(request)
        var person = controller.person(personId)

        assertThat(person)
            .extracting("fullName", "age", "department.name")
            .contains("${request.firstName} ${request.middleName} ${request.lastName}", request.age, null)

        request = PersonRequest(firstName = "Alex", lastName = "Romanov")
        person = controller.updatePerson(request)
        assertThat(person)
            .extracting("fullName", "age", "department.name")
            .contains("${request.firstName} ${request.middleName} ${request.lastName}", null, null)

        var persons = controller.persons()
        assertThat(persons).contains(person)

        controller.deletePerson(personId)
        persons = controller.persons()
        assertThat(persons).doesNotContain(person)
    }
}