package com.example.roteiro01.entity;

public enum Priority {
    ALTA("Alta"),
    MEDIA("Média"),
    BAIXA("Baixa"),
    AUSENTE("Ausente");

    private final String descricao;

    Priority(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}