package br.com.ifpe.gestacad.api.horario;

import java.time.LocalTime;

import org.hibernate.validator.constraints.Length;

import br.com.ifpe.gestacad.modelo.horario.Horario;
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
public class HorarioRequest {

    private Long idAlocacaoAula;
    
    @NotNull(message = "O Horario início é de preenchimento obrigatório")
    private LocalTime horarioInicio;

    @NotNull(message = "O Horario fim é de preenchimento obrigatório")
    private LocalTime horarioFim;

    @NotBlank(message = "O Dia da Semana é de preenchimento obrigatório")
    @Length(max = 50, message = "O Dia da Semana deverá ter no máximo {max} caracteres")
    private String diaSemana;

    public Horario build() {

        return Horario.builder()
            .horarioInicio(horarioInicio)
            .horarioFim(horarioFim)
            .diaSemana(diaSemana)
            .build();
    }
}
