package com.example.roteiro01.unit.service;

import com.example.roteiro01.dto.TaskAtualizarDTO;
import com.example.roteiro01.entity.Priority;
import com.example.roteiro01.entity.Task;
import com.example.roteiro01.repository.TaskRepository;
import com.example.roteiro01.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
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
    void testCriarTarefa() {
        // Mocking
        Task task = new Task();
        task.setId(1L);
        task.setDescricao("Nova tarefa");
        task.setFinalizado(false);

        when(taskRepository.save(any(Task.class))).thenAnswer(i -> {
            Task savedTask = (Task) i.getArguments()[0];
            savedTask.setId(1L);
            return savedTask;
        });

        // Test
        String descricao = "Nova tarefa";
        Task result = taskService.criar(descricao);

        // Verification
        assertEquals(task.getId(), result.getId());
        assertEquals(task.getDescricao(), result.getDescricao());
        assertFalse(result.getFinalizado());
    }
    @Test
    void testAtualizarTarefa() {
        // Mocking
        Task task = new Task();
        task.setId(1L);
        task.setFinalizado(false);

        TaskAtualizarDTO taskDto = new TaskAtualizarDTO();
        taskDto.setDescricao("Nova descrição");
        taskDto.setPrioridade(Priority.ALTA);
        taskDto.setDataPrevistaConclusao(LocalDate.now().plusDays(5));
        taskDto.setDiasPlanejados(10);

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(taskRepository.save(any(Task.class))).thenAnswer(i -> i.getArguments()[0]);

        // Test
        Task result = taskService.atualizarTarefa(1L, taskDto);

        // Verification
        assertEquals(task.getId(), result.getId());
        assertEquals(taskDto.getDescricao(), result.getDescricao());
        assertEquals(taskDto.getPrioridade(), result.getPriority());
        assertEquals(taskDto.getDataPrevistaConclusao(), result.getDataPlanejada());
        assertEquals(taskDto.getDiasPlanejados(), result.getDatasPlanejadas());
    }

    @Test
    void testDeletarTarefa() {
        // Mocking
        Task task = new Task();
        task.setId(1L);
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        doNothing().when(taskRepository).deleteById(1L);

        // Test
        taskService.deletar(1L);

        // Verification
        verify(taskRepository, times(1)).deleteById(1L);
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
        assertEquals(task.getId(), result.getId());
        assertTrue(result.getFinalizado());
    }
}