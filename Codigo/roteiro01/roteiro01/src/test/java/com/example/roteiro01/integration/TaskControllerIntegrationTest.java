package com.example.roteiro01.integration;


import com.example.roteiro01.entity.Task;
import com.example.roteiro01.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class TaskControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TaskRepository taskRepository;

    @BeforeEach
    void setUp() {
        taskRepository.deleteAll();
    }

    @Test
    public void testListAllTasks() throws Exception {
        mockMvc.perform(get("/task"))
                .andExpect(status().isNoContent());

        Task task = new Task();
        task.setDescription("Test Task");
        taskRepository.save(task);

        mockMvc.perform(get("/task"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].description").value("Test Task"));
    }

    @Test
    public void testAddTask() throws Exception {
        mockMvc.perform(post("/task")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"description\":\"Test Task\",\"type\":\"GENERIC\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.description").value("Test Task"));
    }

    @Test
    public void testAddTaskWithInvalidDate() throws Exception {
        mockMvc.perform(post("/task")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"description\":\"Test Task\",\"type\":\"DATA\",\"dueDate\":\"2020-01-01\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Data de execução deve ser igual ou superior a data atual."));
    }

    @Test
    public void testUpdateTask() throws Exception {
        Task task = new Task();
        task.setDescription("Test Task");
        taskRepository.save(task);

        mockMvc.perform(put("/task/" + task.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"description\":\"Updated Task\",\"type\":\"GENERIC\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value("Updated Task"));
    }

    @Test
    public void testUpdateNonExistingTask() throws Exception {
        mockMvc.perform(put("/task/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"description\":\"Updated Task\",\"type\":\"GENERIC\"}"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteTask() throws Exception {
        Task task = new Task();
        task.setDescription("Test Task");
        taskRepository.save(task);

        mockMvc.perform(delete("/task/" + task.getId()))
                .andExpect(status().isNoContent());

        mockMvc.perform(delete("/task/" + task.getId()))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCompleteTask() throws Exception {
        Task task = new Task();
        task.setDescription("Test Task");
        taskRepository.save(task);

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