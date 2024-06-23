package com.example.roteiro01.integration;

import com.example.roteiro01.entity.Task;
import com.example.roteiro01.repository.TaskRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class TaskControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ObjectMapper objectMapper; // For object to JSON conversion

    @BeforeEach
    void setUp() {
        taskRepository.deleteAll();
    }

    @Test
    public void testListAllTasks() throws Exception {
        // Initially, expect an empty list (200 OK, but empty content)
        mockMvc.perform(get("/task"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0))); // Check for empty list

        // Add a task
        Task task = new Task();
        task.setDescription("Test Task");
        task.setType(Task.TaskType.GENERIC);
        taskRepository.save(task);

        // Now, expect the list to contain the added task
        mockMvc.perform(get("/task"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].description").value("Test Task"));
    }

    @Test
    public void testAddTask() throws Exception {
        Task newTask = new Task();
        newTask.setDescription("Test Task");
        newTask.setType(Task.TaskType.GENERIC);

        mockMvc.perform(post("/task")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newTask))) // Convert object to JSON
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.description").value("Test Task"));
    }

    @Test
    public void testAddTaskWithInvalidDate() throws Exception {
        Task newTask = new Task();
        newTask.setDescription("Test Task");
        newTask.setType(Task.TaskType.DATA);
        newTask.setDueDate(LocalDate.of(2020, 1, 1)); // Invalid date

        mockMvc.perform(post("/task")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newTask)))
                .andExpect(status().isBadRequest()); // Expecting 400 Bad Request
    }

    @Test
    public void testUpdateTask() throws Exception {
        Task task = new Task();
        task.setDescription("Test Task");
        task.setType(Task.TaskType.GENERIC);
        task = taskRepository.save(task);

        Task updatedTask = new Task();
        updatedTask.setDescription("Updated Task");
        updatedTask.setType(Task.TaskType.GENERIC);

        mockMvc.perform(put("/task/" + task.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedTask)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value("Updated Task"));
    }

    @Test
    public void testUpdateNonExistingTask() throws Exception {
        Task updatedTask = new Task();
        updatedTask.setDescription("Updated Task");
        updatedTask.setType(Task.TaskType.GENERIC);

        mockMvc.perform(put("/task/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedTask)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteTask() throws Exception {
        Task task = new Task();
        task.setDescription("Test Task");
        task.setType(Task.TaskType.GENERIC);
        task = taskRepository.save(task);

        mockMvc.perform(delete("/task/" + task.getId()))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/task/" + task.getId()))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCompleteTask() throws Exception {
        Task task = new Task();
        task.setDescription("Test Task");
        task.setType(Task.TaskType.GENERIC);
        task = taskRepository.save(task);

        mockMvc.perform(put("/task/" + task.getId() + "/complete"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.completed").value(true));
    }

    @Test
    public void testCompleteNonExistingTask() throws Exception {
        mockMvc.perform(put("/task/999/complete"))
                .andExpect(status().isNotFound());
    }
}