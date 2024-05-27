package com.example.roteiro01.controller;

import com.example.roteiro01.entity.Task;
import com.example.roteiro01.service.TaskService;
import com.example.roteiro01.model.ErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping("/{id}")
    public ResponseEntity<Task> obterTarefa(@PathVariable Long id) {
        Task tarefa = taskService.obterTarefaPorId(id);
        if (tarefa != null) {
            return ResponseEntity.ok(tarefa);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<?> criarTarefa(@Valid @RequestBody Task tarefa) {
        try {
            Task tarefaCriada = taskService.criarTarefa(tarefa);
            URI location = URI.create("/tasks/" + tarefaCriada.getId());
            return ResponseEntity.created(location).body(tarefaCriada);
        } catch (IllegalArgumentException e) {
            ErrorMessage errorMessage = new ErrorMessage(e.getMessage());
            return ResponseEntity.badRequest().body(errorMessage);
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<Task> atualizarTarefa(@PathVariable Long id, @Valid @RequestBody Task tarefa) {
        Task tarefaAtualizada = taskService.atualizarTarefa(id, tarefa);
        if (tarefaAtualizada != null) {
            return ResponseEntity.ok(tarefaAtualizada);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarTarefa(@PathVariable Long id) {
        taskService.deletarTarefa(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<Task>> obterTodasTarefas() {
        List<Task> tarefas = taskService.obterTodasTarefas();
        return ResponseEntity.ok(tarefas);
    }

    @PutMapping("/{id}/done")
    public ResponseEntity<Task> concluirTarefa(@PathVariable Long id) {
        Task task = taskService.concluirTarefa(id);
        if (task != null) {
            return ResponseEntity.ok(task);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
