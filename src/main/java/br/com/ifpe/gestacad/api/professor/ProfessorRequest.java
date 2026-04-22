package br.com.ifpe.gestacad.api.professor;

import br.com.ifpe.gestacad.modelo.professor.Professor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfessorRequest {

    private String nome;
    private String cpf;
    private String senha;
    private String siape;
    private String email;
    private boolean ativo;

    public Professor build() {

        return Professor.builder()
            .nome(nome)
            .cpf(cpf)
            .senha(senha)
            .siape(siape)
            .email(email)
            .ativo(ativo)
            .build();
    }
}