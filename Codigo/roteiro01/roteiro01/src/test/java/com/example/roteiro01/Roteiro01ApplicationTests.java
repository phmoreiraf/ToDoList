package com.example.roteiro01;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class Roteiro01ApplicationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void contextLoads() {
// O contexto do aplicativo é carregado quando o teste é iniciado.
// Se houver algum problema com a configuração do Spring Boot,
// uma exceção será lançada e o teste falhará.
    }
    @Test
    void shouldReturn200WhenGettingAllTasks() {
        // Teste de fumaça para verificar se o endpoint de tarefas está funcionando
        ResponseEntity<String> response = restTemplate.getForEntity("/api/tasks", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void shouldReturn200WhenGettingTaskById() {
        // Teste de fumaça para verificar se o endpoint de tarefa por id está funcionando
        // Supondo que haja uma tarefa com id 1 no banco de dados
        ResponseEntity<String> response = restTemplate.getForEntity("/api/tasks/1", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void shouldReturn201WhenCreatingTask() {
        // Teste de fumaça para verificar se o endpoint de criação de tarefa está funcionando
        ResponseEntity<String> response = restTemplate.postForEntity("/api/tasks", "{ \"description\": \"Nova tarefa\" }", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    void shouldReturn200WhenUpdatingTask() {
        // Teste de fumaça para verificar se o endpoint de atualização de tarefa está funcionando
        // Supondo que haja uma tarefa com id 1 no banco de dados
        restTemplate.put("/api/tasks/1", "{ \"description\": \"Tarefa atualizada\" }");
        ResponseEntity<String> response = restTemplate.getForEntity("/api/tasks/1", String.class);
        assertThat(response.getBody()).contains("Tarefa atualizada");
    }

    @Test
    void shouldReturn204WhenDeletingTask() {
        // Teste de fumaça para verificar se o endpoint de deleção de tarefa está funcionando
        // Supondo que haja uma tarefa com id 1 no banco de dados
        restTemplate.delete("/api/tasks/1");
        ResponseEntity<String> response = restTemplate.getForEntity("/api/tasks/1", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}