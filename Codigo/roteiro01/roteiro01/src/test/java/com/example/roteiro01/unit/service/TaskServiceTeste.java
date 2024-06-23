package com.example.roteiro01.unit.service;

import com.example.roteiro01.entity.Task;
import com.example.roteiro01.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.example.roteiro01.service.TaskService;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
    public void testListAllTasks() {
        Task task1 = new Task();
        task1.setDescription("Test Task 1");
        Task task2 = new Task();
        task2.setDescription("Test Task 2");

        when(taskRepository.findAll()).thenReturn(Arrays.asList(task1, task2));

        assertEquals(2, taskService.listAllTasks().size());
    }

    @Test
    public void testGetTaskById() {
        Task task = new Task();
        task.setId(1L);
        task.setDescription("Test Task");

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        assertEquals(task, taskService.getTaskById(1L).get());
    }

    @Test
    public void testSaveTask() {
        Task task = new Task();
        task.setDescription("Test Task");

        when(taskRepository.save(task)).thenReturn(task);

        assertEquals(task, taskService.saveTask(task));
    }

    @Test
    public void testDeleteTask() {
        Task task = new Task();
        task.setId(1L);
        task.setDescription("Test Task");

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        taskService.deleteTask(1L);

        verify(taskRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testUpdateTask() {
        Task existingTask = new Task();
        existingTask.setDescription("Existing Task");

        Task updatedTask = new Task();
        updatedTask.setDescription("Updated Task");

        when(taskRepository.save(existingTask)).thenReturn(updatedTask);

        assertEquals(updatedTask, taskService.updateTask(existingTask, updatedTask));
    }

    @Test
    public void testCompleteTask() {
        Task task = new Task();
        task.setCompleted(false);

        Task completedTask = new Task();
        completedTask.setCompleted(true);

        when(taskRepository.save(task)).thenReturn(completedTask);

        assertEquals(completedTask, taskService.completeTask(task));
    }

    @Test
    public void testValidateTaskDate() {
        assertEquals(true, taskService.validateTaskDate(LocalDate.now().plusDays(1)));
        assertEquals(false, taskService.validateTaskDate(LocalDate.now().minusDays(1)));
    }
}