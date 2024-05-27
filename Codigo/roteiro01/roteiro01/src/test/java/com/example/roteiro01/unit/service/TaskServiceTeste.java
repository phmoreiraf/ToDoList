package com.example.roteiro01.unit.service;

import com.example.roteiro01.entity.Task;
import com.example.roteiro01.repository.TaskRepository;
import com.example.roteiro01.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TaskServiceTeste {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testObterTarefaPorId() {
        // Mocking
        Task task = new Task();
        task.setId(1L);
        task.setCompleted(false);  // Adicione esta linha
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        // Test
        Task result = taskService.obterTarefaPorId(1L);

        // Verification
        assertEquals(task, result);
    }

    @Test
    void testCriarTarefa() {
        // Mocking
        Task task = new Task();
        task.setId(1L);
        task.setCompleted(false);  // Adicione esta linha
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        // Test
        Task result = taskService.criarTarefa(task);

        // Verification
        assertEquals(task, result);
    }

    @Test
    void testAtualizarTarefa() {
        // Mocking
        Task task = new Task();
        task.setId(1L);
        task.setCompleted(false);  // Adicione esta linha
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        // Test
        Task result = taskService.atualizarTarefa(1L, task);

        // Verification
        assertEquals(task, result);
    }

    @Test
    void testDeletarTarefa() {
        // Mocking
        Task task = new Task();
        task.setId(1L);
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        doNothing().when(taskRepository).deleteById(1L);

        // Test
        taskService.deletarTarefa(1L);

        // Verification
        verify(taskRepository, times(1)).deleteById(1L);
    }

    @Test
    void testObterTodasTarefas() {
        // Mocking
        List<Task> tasks = new ArrayList<>();
        when(taskRepository.findAll()).thenReturn(tasks);

        // Test
        List<Task> result = taskService.obterTodasTarefas();

        // Verification
        assertEquals(tasks, result);
    }

    @Test
    void testConcluirTarefa() {
        // Mocking
        Task task = new Task();
        task.setId(1L);
        task.setDone(false);  // Adicione esta linha
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        task.setDone(true);
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        // Test
        Task result = taskService.concluirTarefa(1L);

        // Verification
        assertEquals(task, result);
        assertTrue(result.isDone());
    }
}