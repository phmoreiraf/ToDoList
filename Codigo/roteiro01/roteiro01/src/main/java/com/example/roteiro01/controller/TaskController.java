package com.example.roteiro01.controller;

import com.example.roteiro01.dto.TaskAtualizarDTO;
import com.example.roteiro01.dto.TaskCriarDTO;
import com.example.roteiro01.dto.TaskCriarDataDTO;
import com.example.roteiro01.dto.TaskCriarPrazoDTO;
import com.example.roteiro01.entity.Task;
import com.example.roteiro01.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/task")
@RequiredArgsConstructor
@EnableWebMvc
public class TaskController {

    public TaskService taskService;

    @Operation(description = "Lista todas as tarefas da lista")
    @GetMapping()
    public ResponseEntity<List<Task>> listAll() {
        try {
            List<Task> taskList = new ArrayList<Task>();
            taskService.findAll().forEach(taskList::add);
            if (taskList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(taskList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(description = "Cria uma nova tarefa passando somente a descrição")
    @PostMapping()
    public ResponseEntity<Task> criar(@RequestBody TaskCriarDTO taskDto) {
        try {
            Task taskCriada = taskService.criar(taskDto.getDescricao());
            return new ResponseEntity<>(taskCriada, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/create/data")
    public ResponseEntity<Task> criarDataTask(@RequestBody TaskCriarDataDTO taskDto) {
        try {
            Task taskCriada = taskService.criarDataTask(taskDto);
            return new ResponseEntity<>(taskCriada, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/create/prazo")
    public ResponseEntity<Task> criarPrazoTask(@RequestBody TaskCriarPrazoDTO taskDto) {
        try {
            Task taskCriada = taskService.criarPrazoTask(taskDto);
            return new ResponseEntity<>(taskCriada, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(description = "Marca a tarefa, cuja ID foi passado, como concluída")
    @PatchMapping("/{id}")
    public ResponseEntity<Task> marcarTarefaConcluida(@PathVariable Long id) {
        try {
            Task updatedTask = taskService.marcarTarefaConcluida(id);
            return new ResponseEntity<>(updatedTask, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(description =  "Atualiza uma tarefa existente")
    @PutMapping("/{id}")
    public ResponseEntity<Task> atualizarTarefa(@PathVariable Long id, @Valid @RequestBody TaskAtualizarDTO taskDto) {
        try {
            Task updatedTask = taskService.atualizarTarefa(id, taskDto);
            return new ResponseEntity<>(updatedTask, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(description = "Deleta a tarefa, cuja ID foi passado")
    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        try {
            taskService.deletar(id);
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
}