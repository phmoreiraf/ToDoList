package com.example.roteiro01.integration;

import com.example.roteiro01.Roteiro01Application;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(classes = {Roteiro01Application.class},  webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
public class TaskControllerIntegrationTest {

    @BeforeEach
    public void setup() {
        RestAssured.baseURI = "http://localhost:8080";
        RestAssured.port = 8080;
    }

    @Test
    public void testObterTodasTarefas() {
        get("/api/tasks").then().statusCode(200);
    }

    @Test
    public void testObterTarefaPorId() {
        get("/api/tasks/1").then().statusCode(200)
                .assertThat().body("description",
                        equalTo("Primeira tarefa"));
    }

    @Test
    public void testCriarTarefa() {
        given()
                .contentType("application/json")
                .body("{\"description\": \"Nova tarefa\"}")
                .when()
                .post("/api/tasks")
                .then()
                .statusCode(201);
    }

    @Test
    public void testAtualizarTarefa() {
        given()
                .contentType("application/json")
                .body("{\"description\": \"Tarefa atualizada\"}")
                .when()
                .put("/api/tasks/1")
                .then()
                .statusCode(200);
    }

    @Test
    public void testDeletarTarefa() {
        given()
                .contentType("application/json")
                .when()
                .delete("/api/tasks/1")
                .then()
                .statusCode(204);
    }
}