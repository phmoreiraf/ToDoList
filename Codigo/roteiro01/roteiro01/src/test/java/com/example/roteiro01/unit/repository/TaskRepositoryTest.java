package com.example.roteiro01.unit.repository;

import com.example.roteiro01.entity.Task;
import com.example.roteiro01.repository.TaskRepository;
import com.example.roteiro01.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class TaskRepositoryTest {

    @Autowired
    private TaskRepository taskRepository;

    @Test
    public void testSaveAndFindById() {
        Task task = new Task();
        task.setDescription("Test Task");

        Task savedTask = taskRepository.save(task);
        Optional<Task> retrievedTask = taskRepository.findById(savedTask.getId());

        assertTrue(retrievedTask.isPresent());
        assertEquals("Test Task", retrievedTask.get().getDescription());
    }

    @Test
    public void testDeleteById() {
        Task task = new Task();
        task.setDescription("Test Task");

        Task savedTask = taskRepository.save(task);
        taskRepository.deleteById(savedTask.getId());

        Optional<Task> retrievedTask = taskRepository.findById(savedTask.getId());
        assertTrue(retrievedTask.isEmpty());
    }
}