package com.example.roteiro01.entity;

public enum TaskType {
    DATA("Data"),
    PRAZO("Prazo"),
    LIVRE("Livre");

    private final String descricao;

    TaskType(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}