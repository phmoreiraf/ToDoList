package com.example.roteiro01.unit.controller;

import com.example.roteiro01.controller.TaskController;
import com.example.roteiro01.dto.TaskAtualizarDTO;
import com.example.roteiro01.dto.TaskCriarDTO;
import com.example.roteiro01.entity.Task;
import com.example.roteiro01.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

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
    void testListAll() {
        // Mocking
        List<Task> tasks = new ArrayList<>();
        Task task1 = new Task();
        task1.setId(1L);
        task1.setDescricao("Tarefa 1");
        tasks.add(task1);

        Task task2 = new Task();
        task2.setId(2L);
        task2.setDescricao("Tarefa 2");
        tasks.add(task2);

        when(taskService.findAll()).thenReturn(tasks);

        // Test
        ResponseEntity<List<Task>> response = taskController.listAll();

        // Verification
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(tasks, response.getBody());
    }

    @Test
    void testCriar() {
        // Mocking
        Task task = new Task();
        task.setId(1L);
        when(taskService.criar(any(String.class))).thenReturn(task);

        // Test
        TaskCriarDTO taskDto = new TaskCriarDTO();
        taskDto.setDescricao("Nova tarefa");
        ResponseEntity<Task> response = taskController.criar(taskDto);

        // Verification
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(task, response.getBody());
    }

    @Test
    void testAtualizarTarefa() {
        // Mocking
        Task task = new Task();
        task.setId(1L);
        TaskAtualizarDTO taskDto = new TaskAtualizarDTO();
        when(taskService.atualizarTarefa(eq(1L), any(TaskAtualizarDTO.class))).thenReturn(task);

        // Test
        ResponseEntity<Task> response = taskController.atualizarTarefa(1L, taskDto);

        // Verification
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(task, response.getBody());
    }

    @Test
    void testDeletar() {
        // Test
        taskController.deletar(1L);

        // Verification
        verify(taskService, times(1)).deletar(1L);
    }
    @Test
    void testMarcarTarefaConcluida() {
        // Mocking
        Task task = new Task();
        task.setId(1L);
        when(taskService.marcarTarefaConcluida(1L)).thenReturn(task);

        // Test
        ResponseEntity<Task> response = taskController.marcarTarefaConcluida(1L);

        // Verification
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(task, response.getBody());
    }
}