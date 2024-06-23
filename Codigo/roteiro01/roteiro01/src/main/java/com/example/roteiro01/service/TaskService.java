package com.example.roteiro01.service;

import com.example.roteiro01.entity.Task;
import com.example.roteiro01.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public List<Task> listAllTasks() {
        return taskRepository.findAll();
    }

    public Optional<Task> getTaskById(Long id) {
        return taskRepository.findById(id);
    }

    public Task saveTask(Task task) {
        return taskRepository.save(task);
    }

    public void deleteTask(Long id) {
        Optional<Task> task = taskRepository.findById(id);
        if (!task.isPresent()) {
            // Handle the error case here. You could log an error or simply return.
            System.err.println("Task not found with id " + id);
            return;
        }
        taskRepository.deleteById(id);
    }

    public Task updateTask(Task existingTask, Task updatedTask) {
        existingTask.setDescription(updatedTask.getDescription());
        existingTask.setCompleted(updatedTask.getCompleted());
        existingTask.setType(updatedTask.getType());
        existingTask.setPriority(updatedTask.getPriority());
        existingTask.setDueDate(updatedTask.getDueDate());
        existingTask.setDueInDays(updatedTask.getDueInDays());
        return taskRepository.save(existingTask);
    }

    public Task completeTask(Task task) {
        task.setCompleted(true);
        return taskRepository.save(task);
    }

    public String calculateStatus(Task task) {
        if (task.getCompleted() != null && task.getCompleted()) {
            return "Concluída";
        }
        Task.TaskType type = task.getType();
        if (type == null) {
            return "Tipo de tarefa não definido";  // Ou algum tratamento de erro adequado
        }
        switch (type) {
            case DATA:
                if (task.getDueDate() != null) {
                    return task.getDueDate().isBefore(LocalDate.now()) ?
                            "X dias de atraso" : "Prevista";
                }
                break;
            case PRAZO:
                if (task.getDueInDays() != null) {
                    LocalDate dueDate = LocalDate.now().plusDays(task.getDueInDays());
                    return dueDate.isBefore(LocalDate.now()) ?
                            "X dias de atraso" : "Prevista";
                }
                break;
            case LIVRE:
                return "Prevista";
            default:
                break;
        }
        return "Indefinido";
    }

    public boolean validateTaskDate(LocalDate dueDate) {
        return !dueDate.isBefore(LocalDate.now());
    }
}