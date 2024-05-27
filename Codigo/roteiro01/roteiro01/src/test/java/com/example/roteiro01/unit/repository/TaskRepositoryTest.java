package com.example.roteiro01.unit.repository;

import com.example.roteiro01.entity.Task;
import com.example.roteiro01.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@SpringBootTest
public class TaskRepositoryTest {

    @Autowired
    private TaskRepository taskRepository;

    @BeforeEach
    void setUp() {
        Task task = new Task();
        task.setId(1L);
        task.setDescription("Tarefa 1");
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
        task.setDescription("Tarefa 2");
        taskRepository.save(task);

        Optional<Task> savedTask = taskRepository.findById(2L);
        assertTrue(savedTask.isPresent());
        assertEquals(2L, savedTask.get().getId());
    }

    @Test
    void testAtualizarTarefa() {
        Optional<Task> task = taskRepository.findById(1L);
        assertTrue(task.isPresent());
        task.get().setDescription("Tarefa atualizada");
        taskRepository.save(task.get());

        Optional<Task> updatedTask = taskRepository.findById(1L);
        assertTrue(updatedTask.isPresent());
        assertEquals("Tarefa atualizada", updatedTask.get().getDescription());
    }

    @Test
    void testDeletarTarefa() {
        taskRepository.deleteById(1L);
        Optional<Task> task = taskRepository.findById(1L);
        assertFalse(task.isPresent());
    }

    @Test
    void testConcluirTarefa() {
        Optional<Task> task = taskRepository.findById(1L);
        assertTrue(task.isPresent());
        task.get().setDone(true);
        taskRepository.save(task.get());

        Optional<Task> concludedTask = taskRepository.findById(1L);
        assertTrue(concludedTask.isPresent());
        assertEquals(true, concludedTask.get().isDone());
    }
}