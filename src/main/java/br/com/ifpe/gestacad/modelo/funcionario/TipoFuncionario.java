package br.com.ifpe.gestacad.modelo.funcionario;

import com.fasterxml.jackson.annotation.JsonValue;

public enum TipoFuncionario {

    ADMINISTRADOR(
            "Administrador"),
    PROFESSOR(
            "Professor");

    private final String tipo;

    private TipoFuncionario(String tipo) {
        this.tipo = tipo;
    }

    @JsonValue
    public String getTipo() {
        return tipo;
    }

}