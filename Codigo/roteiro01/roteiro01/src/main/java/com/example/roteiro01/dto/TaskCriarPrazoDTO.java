package com.example.roteiro01.dto;


import com.example.roteiro01.entity.Priority;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class TaskCriarPrazoDTO {
    @NotBlank(message = "A descrição da tarefa é obrigatória")
    private String descricao;

    @NotNull(message = "A prioridade da tarefa é obrigatória")
    private Priority prioridade;

    @Positive(message = "O prazo previsto de conclusão deve ser um número positivo")
    private Integer diasPlanejados;
}
