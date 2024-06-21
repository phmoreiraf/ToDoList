package com.example.roteiro01.integration;

import com.example.roteiro01.Roteiro01Application;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@RunWith(JUnitPlatform.class)
@SpringBootTest(classes = { Roteiro01Application.class }, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
public class TaskControllerIntegrationTest {

    @Before
    public void setup() {
        RestAssured.baseURI = "http://localhost:8080";
        RestAssured.port = 8080;
    }

    @Test
    public void listAllTasks() {
        get("/api/task").then().statusCode(204);
    }

    @Test
    public void criarDataTask() {
        String requestData = """
            {
                "descricao": "Task Data",
                "prioridade": "ALTA",
                "diaPlanejado": "2024-06-01"
            }
            """;

        given().contentType("application/json")
                .body(requestData)
                .when()
                .post("/api/task/create/data")
                .then()
                .statusCode(201)
                .body("descricao", equalTo("Task Data"))
                .body("prioridade", equalTo("ALTA"))
                .body("diaPlanejado", equalTo("2024-06-01"));
    }

    @Test
    public void criarPrazoTask() {
        String requestPrazo = """
            {
                "descricao": "Task Prazo",
                "prioridade": "MEDIA",
                "diasPlanejados": 15
            }
            """;

        given().contentType("application/json")
                .body(requestPrazo)
                .when()
                .post("/api/task/create/prazo")
                .then()
                .statusCode(201)
                .body("descricao", equalTo("Task Prazo"))
                .body("prioridade", equalTo("MEDIA"))
                .body("diasPlanejados", equalTo(15));
    }

    @Test
    public void marcarConcluida() {
        Long taskId = 1L;

        given().pathParam("id", taskId)
                .when()
                .patch("/api/task/{id}")
                .then()
                .statusCode(200)
                .body("completed", equalTo(true))
                .body("id", equalTo(taskId.intValue()));
    }

    @Test
    public void deletarTask() {
        Long taskId = 1L;

        given().pathParam("id", taskId)
                .when()
                .delete("/api/task/{id}")
                .then()
                .statusCode(200);
    }
}