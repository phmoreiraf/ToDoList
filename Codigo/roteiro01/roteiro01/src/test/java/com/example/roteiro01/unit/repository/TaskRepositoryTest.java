package com.example.roteiro01.unit.repository;

import com.example.roteiro01.entity.Task;
import com.example.roteiro01.repository.TaskRepository;
import com.example.roteiro01.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import static org.mockito.ArgumentMatchers.any;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

//@DataJpaTest
@SpringBootTest
public class TaskRepositoryTest {

    @Autowired
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    @BeforeEach
    void setUp() {
        Task task = new Task();
        task.setId(1L);
        task.setDescricao("Tarefa 1");
        taskRepository.save(task);
    }

    @Test
    void testObterTarefaPorId() {
        Optional<Task> task = taskRepository.findById(1L);
        assertTrue(task.isPresent());
        assertEquals(1L, task.get().getId());
    }

    @Test
    void testObterTodasTarefas() {
        List<Task> tasks = taskRepository.findAll();
        assertFalse(tasks.isEmpty());
        assertEquals(1, tasks.size());
    }

    @Test
    void testCriarTarefa() {
        Task task = new Task();
        task.setId(2L);
        task.setDescricao("Tarefa 2");
        taskRepository.save(task);

        Optional<Task> savedTask = taskRepository.findById(2L);
        assertTrue(savedTask.isPresent());
        assertEquals(2L, savedTask.get().getId());
    }

    @Test
    void testAtualizarTarefa() {
        Optional<Task> task = taskRepository.findById(1L);
        assertTrue(task.isPresent());
        task.get().setDescricao("Tarefa atualizada");
        taskRepository.save(task.get());

        Optional<Task> updatedTask = taskRepository.findById(1L);
        assertTrue(updatedTask.isPresent());
        assertEquals("Tarefa atualizada", updatedTask.get().getDescricao());
    }

    @Test
    void testDeletarTarefa() {
        taskRepository.deleteById(1L);
        Optional<Task> task = taskRepository.findById(1L);
        assertFalse(task.isPresent());
    }

    @Test
    void testConcluirTarefa() {
        // Mocking
        Task task = new Task();
        task.setId(1L);
        task.setFinalizado(false);

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(taskRepository.save(any(Task.class))).thenAnswer(i -> {
            Task savedTask = (Task) i.getArguments()[0];
            savedTask.setFinalizado(true);
            return savedTask;
        });

        // Test
        Task result = taskService.marcarTarefaConcluida(1L);

        // Verification
        assertTrue(result.getFinalizado());
    }
}