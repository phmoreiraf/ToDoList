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
@Schema(description = "Todos os detalhes sobre uma tarefa.")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(name = "description")
    @Size(min = 10, message = "Descrição da tarefa deve possuir pelo menos 10 caracteres")
    private String description;

    private Boolean completed = false;

    private LocalDate dueDate; // Data prevista para conclusão para tarefas do tipo Data
    private Integer dueInDays; // Prazo previsto em dias para tarefas do tipo Prazo

    private TaskType type = TaskType.LIVRE; // Tipo de tarefa
    private Priority priority; // Nível de prioridade da tarefa

    public Task(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Task [id=" + id + ", description=" + description + ", completed=" + completed + ", dueDate=" + dueDate + ", dueInDays=" + dueInDays + ", type=" + type + ", priority=" + priority + "]";
    }

    public enum TaskType {
        DATA, PRAZO, LIVRE, GENERIC
    }

    public enum Priority {
        ALTA, MEDIA, BAIXA
    }
}