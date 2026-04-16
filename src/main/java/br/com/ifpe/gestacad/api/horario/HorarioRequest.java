package br.com.ifpe.gestacad.api.horario;

import br.com.ifpe.gestacad.modelo.horario.Horario;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HorarioRequest {
    
    private String horario;

    public Horario build() {

        return Horario.builder()
            .horario(horario)
            .build();
    }
}
