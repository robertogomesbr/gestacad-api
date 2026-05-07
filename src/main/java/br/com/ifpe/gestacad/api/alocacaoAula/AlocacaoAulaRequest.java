package br.com.ifpe.gestacad.api.alocacaoAula;

import br.com.ifpe.gestacad.modelo.alocacaoAula.AlocacaoAula;
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

    private String semestreLetivo;
    
    public AlocacaoAula build() {

        return AlocacaoAula.builder()
            .semestreLetivo(semestreLetivo)
            .build();
    }
}
