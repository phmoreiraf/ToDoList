package com.example.roteiro01.unit.controller;

import com.example.roteiro01.entity.Task;
import com.example.roteiro01.service.TaskService;
import com.example.roteiro01.controller.TaskController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TaskControllerTest {

    @Mock
    private TaskService taskService;

    @InjectMocks
    private TaskController taskController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testObterTarefa() {
        // Mocking
        Task task = new Task();
        task.setId(1L);
        when(taskService.obterTarefaPorId(1L)).thenReturn(task);

        // Test
        ResponseEntity<Task> response = taskController.obterTarefa(1L);

        // Verification
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(task, response.getBody());
    }

    @Test
    void testCriarTarefa() {
        // Mocking
        Task task = new Task();
        task.setId(1L);
        when(taskService.criarTarefa(any(Task.class))).thenReturn(task);

        // Test
        ResponseEntity<?> response = taskController.criarTarefa(task);

        // Verification
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertTrue(response.getHeaders().containsKey("Location"));
        assertEquals("/tasks/1", response.getHeaders().getLocation().getPath());
        assertEquals(task, response.getBody());
    }

    @Test
    void testAtualizarTarefa() {
        // Mocking
        Task task = new Task();
        task.setId(1L);
        when(taskService.atualizarTarefa(1L, task)).thenReturn(task);

        // Test
        ResponseEntity<Task> response = taskController.atualizarTarefa(1L, task);

        // Verification
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(task, response.getBody());
    }

    @Test
    void testDeletarTarefa() {
        // Test
        ResponseEntity<Void> response = taskController.deletarTarefa(1L);

        // Verification
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(taskService, times(1)).deletarTarefa(1L);
    }

    @Test
    void testObterTodasTarefas() {
        // Mocking
        List<Task> tasks = new ArrayList<>();
        when(taskService.obterTodasTarefas()).thenReturn(tasks);

        // Test
        ResponseEntity<List<Task>> response = taskController.obterTodasTarefas();

        // Verification
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(tasks, response.getBody());
    }

    @Test
    void testConcluirTarefa() {
        // Mocking
        Task task = new Task();
        task.setId(1L);
        when(taskService.concluirTarefa(1L)).thenReturn(task);

        // Test
        ResponseEntity<Task> response = taskController.concluirTarefa(1L);

        // Verification
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(task, response.getBody());
    }
}