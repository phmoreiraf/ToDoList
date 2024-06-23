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
import org.springframework.web.bind.annotation.RequestBody;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
    public void testListAllTasks() {
        Task task1 = new Task();
        task1.setDescription("Test Task 1");
        Task task2 = new Task();
        task2.setDescription("Test Task 2");

        when(taskService.listAllTasks()).thenReturn(Arrays.asList(task1, task2));

        ResponseEntity<List<Task>> response = taskController.listAll();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
    }

    @Test
    public void testAddTask() {
        Task task = new Task();
        task.setDescription("Test Task");

        when(taskService.saveTask(task)).thenReturn(task);

        ResponseEntity<?> response = taskController.addTask(task);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(task, response.getBody());
    }

    @Test
    public void testAddTaskWithInvalidDate() {
        Task task = new Task();
        task.setType(Task.TaskType.DATA);
        task.setDueDate(LocalDate.of(2020, 1, 1));

        ResponseEntity<?> response = taskController.addTask(task);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Data de execução deve ser igual ou superior a data atual.", response.getBody());
    }

    @Test
    public void testUpdateTask() {
        Task existingTask = new Task();
        existingTask.setId(1L);
        Task updatedTask = new Task();
        updatedTask.setDescription("Updated Task");

        when(taskService.getTaskById(1L)).thenReturn(Optional.of(existingTask));
        when(taskService.updateTask(existingTask, updatedTask)).thenReturn(updatedTask);

        ResponseEntity<?> response = taskController.updateTask(1L, updatedTask);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedTask, response.getBody());
    }

    @Test
    public void testUpdateNonExistingTask() {
        Task updatedTask = new Task();
        updatedTask.setDescription("Updated Task");

        when(taskService.getTaskById(1L)).thenReturn(Optional.empty());

        ResponseEntity<?> response = taskController.updateTask(1L, updatedTask);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testDeleteTask() {
        doNothing().when(taskService).deleteTask(1L);

        ResponseEntity<HttpStatus> response = taskController.deleteTask(1L);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void testCompleteTask() {
        Task task = new Task();
        task.setId(1L);
        task.setCompleted(false);

        Task completedTask = new Task();
        completedTask.setCompleted(true);

        when(taskService.getTaskById(1L)).thenReturn(Optional.of(task));
        when(taskService.completeTask(task)).thenReturn(completedTask);

        ResponseEntity<?> response = taskController.completeTask(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(completedTask, response.getBody());
    }

    @Test
    public void testCompleteNonExistingTask() {
        when(taskService.getTaskById(1L)).thenReturn(Optional.empty());

        ResponseEntity<?> response = taskController.completeTask(1L);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}