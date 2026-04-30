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

    private Long idProfessor;
    private Long idTurma;
    private Long idHorario;
    private String nome;
    private String area;
    private String turno;

    public Disciplina build() {

        return Disciplina.builder()
            .nome(nome)
            .area(area)
            .turno(turno)
            .build();
    }
}