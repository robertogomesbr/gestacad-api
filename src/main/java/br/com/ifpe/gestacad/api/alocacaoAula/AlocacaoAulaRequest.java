package br.com.ifpe.gestacad.api.alocacaoAula;

import org.hibernate.validator.constraints.Length;

import br.com.ifpe.gestacad.modelo.alocacaoAula.AlocacaoAula;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlocacaoAulaRequest {

    private Long idTurma;

    private Long idDisciplina;

    private Long idSala;

    private Long idProfessor;

    @NotBlank(message = "O Semestre Letivo é de preenchimento obrigatório")
    @Length(max = 50, message = "O Semestre Letivo deverá ter no máximo {max} caracteres")
    private String semestreLetivo;
    
    public AlocacaoAula build() {

        return AlocacaoAula.builder()
            .semestreLetivo(semestreLetivo)
            .build();
    }
}
