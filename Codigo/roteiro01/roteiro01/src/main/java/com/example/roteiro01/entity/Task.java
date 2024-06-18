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

import java.time.temporal.ChronoUnit;
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
    private String descricao;
    private LocalDate DataCriacao;

    private Priority priority;
    private TaskType tipoTask;

    private Boolean finalizado;
    private LocalDate dataPlanejada;

    private Integer datasPlanejadas;
    private LocalDate ultimaModicacaoData;

    public Task(String descricao) {
        this.descricao = descricao;
        this.finalizado = false;
        this.tipoTask = TaskType.LIVRE;
        this.priority = Priority.AUSENTE;
    }

    public Task(String descricao, TaskType type, Priority priority) {
        this.descricao = descricao;
        this.finalizado = false;
        this.tipoTask = type;
        this.priority = priority;
        this.DataCriacao = LocalDate.now();
    }

    // DATA
    public Task(String descricao, TaskType type, Priority priority, LocalDate plannedDate) {
        this.descricao = descricao;
        this.tipoTask = type;
        this.priority = priority;
        this.dataPlanejada = plannedDate;
        this.finalizado = false;
        this.DataCriacao = LocalDate.now();
    }

    // PRAZO
    public Task(String descricao, TaskType type, Priority priority, Integer plannedDays) {
        this.descricao = descricao;
        this.tipoTask = type;
        this.priority = priority;
        this.datasPlanejadas = plannedDays;
        this.finalizado = false;
        this.DataCriacao = LocalDate.now();
        this.ultimaModicacaoData = LocalDate.now();
    }

    @Override
    public String toString() {
        String status = calculateStatus();
        return String.format("Task [id=%d, descricao=%s, priority=%s, type=%s, status=%s]",
                this.id, this.descricao, this.priority, tipoTask.getDescricao(), status);
    }

    private String calculateStatus() {
        if (this.finalizado) {
            return "Concluída";
        }
        LocalDate currentDate = LocalDate.now();
        long daysLate;
        switch (this.tipoTask) {
            case DATA:
                if (dataPlanejada != null) {
                    daysLate = ChronoUnit.DAYS.between(dataPlanejada, currentDate);
                    return dataPlanejada.isBefore(currentDate) ? daysLate + " dias de atraso" : "Prevista";
                }
                break;
            case PRAZO:
                if (datasPlanejadas != null) {
                    LocalDate dueDate = DataCriacao.plusDays(datasPlanejadas);
                    daysLate = ChronoUnit.DAYS.between(dueDate, currentDate);
                    return dueDate.isBefore(currentDate) ? daysLate + " dias de atraso" : "Prevista";
                }
                break;
            case LIVRE:
                return "Prevista";
        }
        return "Prevista";
    }

    // Talvez chamar quando for enviar as tarefas para o frontend (checar)
    public void diminuirDiasPrazo() {
        LocalDate currentDate = LocalDate.now();

        LocalDate referenceDate;
        if (this.getUltimaModicacaoData() != null) {
            referenceDate = this.getUltimaModicacaoData();
        } else {
            referenceDate = this.getDataCriacao();
        }

        long diasPassados = ChronoUnit.DAYS.between(referenceDate, currentDate);

        int prazoRestante = this.getDatasPlanejadas() - (int) diasPassados;

        this.setDatasPlanejadas(prazoRestante);

    }

}