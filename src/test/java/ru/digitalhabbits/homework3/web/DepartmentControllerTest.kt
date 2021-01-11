package ru.digitalhabbits.homework3.web

import org.apache.commons.lang3.RandomStringUtils
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.groups.Tuple.tuple
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import ru.digitalhabbits.homework3.data.DEPARTMENT_REQUEST
import ru.digitalhabbits.homework3.data.PERSON_REQUEST
import ru.digitalhabbits.homework3.model.DepartmentRequest

internal class DepartmentControllerTest {

    @Test
    @DisplayName("Department CRUD operations")
    fun crudOperationsTest() {
        val departmentController = DepartmentController()
        val personController = PersonController()

        var request = DEPARTMENT_REQUEST
        val departmentId = departmentController.createDepartment(request)
        var department = departmentController.department(departmentId)
        assertThat(department)
            .extracting("name", "closed")
            .contains(request.name, false)
        assertThat(department.persons)
            .isNullOrEmpty()

        request = DepartmentRequest(name = RandomStringUtils.randomAlphabetic(10))
        department = departmentController.updateDepartment(request, departmentId)
        assertThat(department)
            .extracting("name", "closed")
            .contains(request.name, false)

        var departments = departmentController.departments()
        assertThat(departments)
            .extracting("id", "name")
            .contains(tuple(department.id, department.name))

        val personId1 = personController.createPerson(PERSON_REQUEST)
        val personId2 = personController.createPerson(PERSON_REQUEST)
        val personId3 = personController.createPerson(PERSON_REQUEST)

        departmentController.addPersonToDepartmentSuccess(departmentId, personId1)
        departmentController.addPersonToDepartmentSuccess(departmentId, personId2)
        departmentController.addPersonToDepartmentSuccess(departmentId, personId3)
        department = departmentController.department(departmentId)
        assertThat(department.persons)
            .extracting("id")
            .containsExactlyInAnyOrder(personId1, personId2, personId3)

        departmentController.removePersonFromDepartment(departmentId, personId1)
        department = departmentController.department(departmentId)
        assertThat(department.persons)
            .extracting("id")
            .containsExactlyInAnyOrder(personId2, personId3)

        departmentController.removePersonFromDepartment(departmentId, personId1)

        departmentController.closeDepartment(departmentId)
        department = departmentController.department(departmentId)
        assertThat(department)
            .extracting("name", "closed")
            .contains(request.name, true)
        assertThat(department.persons)
            .isNullOrEmpty()

        departmentController.addPersonToDepartmentConflict(departmentId, personId1)

        departmentController.deleteDepartment(departmentId)
        departments = departmentController.departments()
        assertThat(departments)
            .extracting("id")
            .doesNotContain(departmentId)
    }
}