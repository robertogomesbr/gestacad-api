package br.com.ifpe.gestacad.api.turma;

import org.hibernate.validator.constraints.Length;

import br.com.ifpe.gestacad.modelo.turma.Turma;
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

    private Long idCurso;

    @NotBlank(message = "O Nome é de preenchimento obrigatório")
    @Length(max = 100, message = "O Nome deverá ter no máximo {max} caracteres")
    private String nome;

    @NotNull(message = "O Ano de Entrada é de preenchimento obrigatório")
    private Integer anoEntrada;

    @NotBlank(message = "O Semestre de Entrada é de preenchimento obrigatório")
    @Length(max = 50, message = "O Semestre de Entrada deverá ter no máximo {max} caracteres")
    private String semestreEntrada;
    
    @NotNull(message = "O Qtd. Máxima de Alunos é de preenchimento obrigatório")
    private Integer qtdMaximaAlunos;

    @NotNull(message = "O Qtd. de Alunos Matriculados é de preenchimento obrigatório")
    private Integer qtdAlunosMatriculados;
    
    @NotNull(message = "O Status da Turma é de preenchimento obrigatório")
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