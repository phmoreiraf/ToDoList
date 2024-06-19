package com.example.roteiro01.dto;

import com.example.roteiro01.entity.Priority;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
public class TaskCriarDataDTO {
    @NotBlank(message = "A descrição da tarefa é obrigatória")
    private String descricao;

    @NotNull(message = "A prioridade da tarefa é obrigatória")
    private Priority prioridade;

    @FutureOrPresent(message = "A data prevista de conclusão deve ser no presente ou no futuro")
    private LocalDate dataPrevistaConclusao;
}
