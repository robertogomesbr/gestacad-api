package br.com.ifpe.gestacad.api.disciplina;

import java.time.LocalTime;

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

    private String nome;
    private String area;
    private LocalTime horarioInicio;
    private LocalTime horarioFim;

    public Disciplina build() {

        return Disciplina.builder()
            .nome(nome)
            .area(area)
            .horarioInicio(horarioInicio)
            .horarioFim(horarioFim)
            .build();
    }
}