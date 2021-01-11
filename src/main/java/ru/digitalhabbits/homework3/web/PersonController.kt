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
import ru.digitalhabbits.homework3.model.PersonRequest
import ru.digitalhabbits.homework3.model.PersonResponse
import ru.digitalhabbits.homework3.model.PersonResponseList

class PersonController {
    private val requestSpecification: RequestSpecification = RequestSpecBuilder()
        .setBaseUri(System.getProperty("targetUrl"))
        .setBasePath("/persons")
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

    fun person(id: Int): PersonResponse =
        RestAssured.given(requestSpecification)
            .pathParam("id", id)
            .get("/{id}")
            .then()
            .statusCode(HttpStatus.SC_OK)
            .contentType(ContentType.JSON)
            .extract()
            .body()
            .`as`(PersonResponse::class.java)

    fun persons(): List<PersonResponse> =
        RestAssured.given(requestSpecification)
            .get()
            .then()
            .statusCode(HttpStatus.SC_OK)
            .contentType(ContentType.JSON)
            .extract()
            .body()
            .`as`(PersonResponseList::class.java)

    fun createPerson(request: PersonRequest): Int =
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

    fun updatePerson(request: PersonRequest, id: Int): PersonResponse =
        RestAssured.given(requestSpecification)
            .body(request)
            .contentType(ContentType.JSON)
            .pathParam("id", id)
            .patch("/{id}")
            .then()
            .statusCode(HttpStatus.SC_OK)
            .extract()
            .body()
            .`as`(PersonResponse::class.java)

    fun deletePerson(id: Int) {
        RestAssured.given(requestSpecification)
            .pathParam("id", id)
            .delete("/{id}")
            .then()
            .statusCode(HttpStatus.SC_NO_CONTENT)
    }
}