package br.com.ifpe.gestacad.api.turma;

import org.hibernate.validator.constraints.Length;

import br.com.ifpe.gestacad.modelo.turma.Turma;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TurmaRequest {

    private String nome;

    private String anoEntrada;

    private String semestreEntrada;
    
    private Integer qtdMaximaAlunos;

    private Integer qtdAlunosMatriculados;
       
    private Boolean statusTurma;

    public Turma build() {

        return Turma.builder()
            .nome(nome)
            .anoEntrada(anoEntrada)
            .semestreEntrada(semestreEntrada)
            .qtdMaximaAlunos(qtdMaximaAlunos)
            .qtdAlunosMatriculados(qtdAlunosMatriculados)
            .statusTurma(statusTurma)
            .build();
    }
}