package br.com.ifpe.gestacad.api.curso;

import br.com.ifpe.gestacad.modelo.curso.Curso;
import br.com.ifpe.gestacad.modelo.sala.Sala;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CursoRequest {

    private String nome;
    
    private Integer qtdPeriodos;

    private String area;

    private String turno;

    public Curso build() {

        return Curso.builder()
            .nome(nome)
            .qtdPeriodos(qtdPeriodos)
            .area(area)
            .turno(turno)
            .build();
    }

}
