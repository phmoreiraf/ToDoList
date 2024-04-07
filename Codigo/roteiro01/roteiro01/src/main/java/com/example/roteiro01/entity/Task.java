package com.example.roteiro01.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(name = "Descrição da tarefa deve possuir pelo menos 10 caracteres")
    @Size(min = 10, message = "Descrição da tarefa deve possuir pelo menos 10 caracteres")
    private String description;

    private Boolean completed;

    private TaskType type; // Tipo de tarefa
    private Long deadlineInDays; // Prazo em dias
    private LocalDate dueDate; // Data de vencimento
    private Priority priority; // Nível de prioridade
    private TaskStatus status; // Status da tarefa

    public Task(String description){
        this.description = description;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", completed=" + completed +
                ", type=" + type +
                ", deadlineInDays=" + deadlineInDays +
                ", dueDate=" + dueDate +
                ", priority=" + priority +
                ", status=" + status +
                '}';
    }
}
