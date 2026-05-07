package br.com.ifpe.gestacad.api.disciplina;

import br.com.ifpe.gestacad.modelo.disciplina.Disciplina;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DisciplinaRequest {

    private Long idCurso;

    private String nome;
    private Integer chTotal;
    private String periodoOfertado;

    public Disciplina build() {

        return Disciplina.builder()
            .nome(nome)
            .chTotal(chTotal)
            .periodoOfertado(periodoOfertado)
            .build();
    }
}