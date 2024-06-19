package com.example.roteiro01.dto;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskCriarDTO {

    @Size(min = 20, message = "Descrição da tarefa deve possuir no minimo 20 caracteres")
    private String descricao;
}
