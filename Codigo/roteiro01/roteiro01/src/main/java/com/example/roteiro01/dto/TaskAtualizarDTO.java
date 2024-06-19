package com.example.roteiro01.dto;

import com.example.roteiro01.entity.Priority;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class TaskAtualizarDTO {
    private String descricao;
    private Priority prioridade;
    private LocalDate dataPrevistaConclusao;
    @Positive(message = "O prazo previsto de conclusão deve ser um número positivo")
    private Integer diasPlanejados;
}