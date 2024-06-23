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
    public void testListAllTasks() {
        Task task1 = new Task();
        task1.setDescription("Test Task 1");
        taskRepository.save(task1);

        Task task2 = new Task();
        task2.setDescription("Test Task 2");
        taskRepository.save(task2);

        List<Task> tasks = taskRepository.findAll();

        assertEquals(2, tasks.size());
        assertEquals("Test Task 1", tasks.get(0).getDescription());
        assertEquals("Test Task 2", tasks.get(1).getDescription());
    }

    @Test
    public void testCreateTask() {
        Task task = new Task();
        task.setDescription("Test Task");

        assertNull(task.getId()); // id should be null before save

        Task savedTask = taskRepository.save(task);

        assertNotNull(savedTask.getId()); // id should be auto-generated after save
        assertEquals("Test Task", savedTask.getDescription());
    }

    @Test
    public void testUpdateTask() {
        Task task = new Task();
        task.setDescription("Test Task");
        Task savedTask = taskRepository.save(task);

        savedTask.setDescription("Updated Task");
        Task updatedTask = taskRepository.save(savedTask);

        assertEquals("Updated Task", updatedTask.getDescription());
    }

    @Test
    public void testCompleteTask() {
        Task task = new Task();
        task.setDescription("Test Task");
        task.setCompleted(false);
        Task savedTask = taskRepository.save(task);

        savedTask.setCompleted(true);
        Task completedTask = taskRepository.save(savedTask);

        assertTrue(completedTask.getCompleted());
    }
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