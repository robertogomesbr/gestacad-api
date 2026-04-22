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

    private Integer periodo;
    private String curso;

    public Turma build() {

        return Turma.builder()
            .periodo(periodo)
            .curso(curso)
            .build();
    }
}