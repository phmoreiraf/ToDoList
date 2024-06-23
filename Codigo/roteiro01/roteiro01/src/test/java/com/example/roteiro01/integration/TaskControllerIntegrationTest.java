package com.example.roteiro01.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TaskControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testListAll() throws Exception {
        mockMvc.perform(get("/task"))
                .andExpect(status().isOk());
    }

    @Test
    public void testCriar() throws Exception {
        String taskJson = "{\"descricao\":\"Test Task\"}";
        mockMvc.perform(post("/task")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(taskJson))
                .andExpect(status().isCreated());
    }

    @Test
    public void testCriarDataTask() throws Exception {
        String taskJson = "{\"descricao\":\"Test Task\", \"data\":\"2022-12-31\"}";
        mockMvc.perform(post("/task/create/data")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(taskJson))
                .andExpect(status().isCreated());
    }

    @Test
    public void testCriarPrazoTask() throws Exception {
        String taskJson = "{\"descricao\":\"Test Task\", \"prazo\":10}";
        mockMvc.perform(post("/task/create/prazo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(taskJson))
                .andExpect(status().isCreated());
    }

    @Test
    public void testMarcarTarefaConcluida() throws Exception {
        mockMvc.perform(patch("/task/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testAtualizarTarefa() throws Exception {
        String taskJson = "{\"descricao\":\"Updated Task\"}";
        mockMvc.perform(put("/task/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(taskJson))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeletar() throws Exception {
        mockMvc.perform(delete("/task/1"))
                .andExpect(status().isOk());
    }
}