package ru.digitalhabbits.homework3.web

import io.restassured.RestAssured
import io.restassured.builder.RequestSpecBuilder
import io.restassured.builder.ResponseSpecBuilder
import io.restassured.filter.log.LogDetail
import io.restassured.http.ContentType
import io.restassured.parsing.Parser
import io.restassured.specification.RequestSpecification
import org.apache.http.HttpHeaders
import org.apache.http.HttpStatus
import org.hamcrest.Matchers
import ru.digitalhabbits.homework3.model.*

class DepartmentController {
    private val requestSpecification: RequestSpecification = RequestSpecBuilder()
        .setBaseUri(System.getProperty("targetUrl"))
        .setBasePath("/departments")
        .setAccept(ContentType.JSON)
        .setContentType(ContentType.JSON)
        .log(LogDetail.ALL)
        .build()

    init {
        RestAssured.defaultParser = Parser.JSON
        RestAssured.responseSpecification = ResponseSpecBuilder()
            .expectResponseTime(Matchers.lessThan(15000L))
            .build()
    }

    fun department(id: Int): DepartmentResponse =
        RestAssured.given(requestSpecification)
            .pathParam("id", id)
            .get("/{id}")
            .then()
            .statusCode(HttpStatus.SC_OK)
            .contentType(ContentType.JSON)
            .extract()
            .body()
            .`as`(DepartmentResponse::class.java)

    fun departments(): DepartmentListResponse =
        RestAssured.given(requestSpecification)
            .get()
            .then()
            .statusCode(HttpStatus.SC_OK)
            .contentType(ContentType.JSON)
            .extract()
            .body()
            .`as`(DepartmentListResponse::class.java)

    fun createDepartment(request: DepartmentRequest): Int =
        RestAssured.given(requestSpecification)
            .body(request)
            .contentType(ContentType.JSON)
            .post()
            .then()
            .statusCode(HttpStatus.SC_CREATED)
            .extract()
            .header(HttpHeaders.LOCATION)
            .toString()
            .split("/")
            .last()
            .toInt()

    fun updateDepartment(request: DepartmentRequest, id: Int): DepartmentResponse =
        RestAssured.given(requestSpecification)
            .body(request)
            .contentType(ContentType.JSON)
            .pathParam("id", id)
            .patch("/{id}")
            .then()
            .statusCode(HttpStatus.SC_OK)
            .extract()
            .body()
            .`as`(DepartmentResponse::class.java)

    fun deleteDepartment(id: Int) {
        RestAssured.given(requestSpecification)
            .pathParam("id", id)
            .delete("/{id}")
            .then()
            .statusCode(HttpStatus. SC_NO_CONTENT)
    }

    fun addPersonToDepartmentSuccess(departmentId: Int, personId: Int) {
        addPersonToDepartment(departmentId, personId).statusCode(HttpStatus.SC_NO_CONTENT)
    }

    fun addPersonToDepartmentConflict(departmentId: Int, personId: Int) {
        addPersonToDepartment(departmentId, personId).statusCode(HttpStatus.SC_CONFLICT)
    }

    fun removePersonFromDepartment(departmentId: Int, personId: Int) {
        RestAssured.given(requestSpecification)
            .pathParam("departmentId", departmentId)
            .pathParam("personId", personId)
            .delete("/{departmentId}/{personId}")
            .then()
            .statusCode(HttpStatus. SC_NO_CONTENT)
    }

    fun closeDepartment(id: Int) {
        RestAssured.given(requestSpecification)
            .pathParam("id", id)
            .post("/{id}/close")
            .then()
            .statusCode(HttpStatus. SC_NO_CONTENT)
    }

    private fun addPersonToDepartment(departmentId: Int, personId: Int) =
        RestAssured.given(requestSpecification)
            .pathParam("departmentId", departmentId)
            .pathParam("personId", personId)
            .post("/{departmentId}/{personId}")
            .then()
}
