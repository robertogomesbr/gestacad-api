package br.com.ifpe.gestacad.api.turma;

import br.com.ifpe.gestacad.modelo.turma.Turma;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TurmaRequest {

    private Long idCurso;

    private String nome;

    private Integer anoEntrada;

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