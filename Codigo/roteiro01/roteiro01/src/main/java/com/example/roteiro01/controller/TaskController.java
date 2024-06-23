package com.example.roteiro01.controller;


import com.example.roteiro01.entity.Task;
import com.example.roteiro01.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping("/task")
    @Operation(summary = "Lista todas as tarefas da lista")
    public ResponseEntity<List<Task>> listAll() {
        List<Task> tasks = taskService.listAllTasks();
        if (tasks.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        tasks.forEach(task -> task.setDescription(task.getDescription() + " - Status: " + taskService.calculateStatus(task)));
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @PostMapping("/task")
    @Operation(summary = "Adiciona uma nova tarefa")
    public ResponseEntity<?> addTask(@Valid @RequestBody Task task) {
        if (task.getType() == Task.TaskType.DATA && task.getDueDate() != null && !taskService.validateTaskDate(task.getDueDate())) {
            return new ResponseEntity<>("Data de execução deve ser igual ou superior a data atual.", HttpStatus.BAD_REQUEST);
        }
        Task savedTask = taskService.saveTask(task);
        return new ResponseEntity<>(savedTask, HttpStatus.CREATED);
    }

    @PutMapping("/task/{id}")
    @Operation(summary = "Altera uma tarefa existente")
    public ResponseEntity<?> updateTask(@PathVariable("id") long id, @Valid @RequestBody Task task) {
        return taskService.getTaskById(id)
                .map(existingTask -> {
                    if (task.getType() == Task.TaskType.DATA && task.getDueDate() != null && !taskService.validateTaskDate(task.getDueDate())) {
                        return new ResponseEntity<>("Data de execução deve ser igual ou superior a data atual.", HttpStatus.BAD_REQUEST);
                    }
                    Task updatedTask = taskService.updateTask(existingTask, task);
                    return new ResponseEntity<>(updatedTask, HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/task/{id}")
    @Operation(summary = "Exclui uma tarefa existente")
    public ResponseEntity<HttpStatus> deleteTask(@PathVariable("id") long id) {
        taskService.deleteTask(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/task/{id}/complete")
    @Operation(summary = "Conclui uma tarefa")
    public ResponseEntity<?> completeTask(@PathVariable("id") long id) {
        return taskService.getTaskById(id)
                .map(task -> {
                    Task completedTask = taskService.completeTask(task);
                    return new ResponseEntity<>(completedTask, HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}