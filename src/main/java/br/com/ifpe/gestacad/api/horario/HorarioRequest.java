package br.com.ifpe.gestacad.api.horario;

import org.hibernate.validator.constraints.Length;

import br.com.ifpe.gestacad.modelo.horario.Horario;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HorarioRequest {
    
    @NotBlank(message = "O Horario é de preenchimento obrigatório")
    @Length(max = 50, message = "O Horario deverá ter no máximo {max} caracteres")
    private String horario;

    @NotBlank(message = "O Dia da Semana é de preenchimento obrigatório")
    @Length(max = 50, message = "O Dia da Semana deverá ter no máximo {max} caracteres")
    private String diaSemana;

    public Horario build() {

        return Horario.builder()
            .horario(horario)
            .diaSemana(diaSemana)
            .build();
    }
}
