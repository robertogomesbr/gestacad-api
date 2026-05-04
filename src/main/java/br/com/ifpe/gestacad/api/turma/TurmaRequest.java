package br.com.ifpe.gestacad.api.turma;

import org.hibernate.validator.constraints.Length;

import br.com.ifpe.gestacad.modelo.turma.Turma;
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
public class TurmaRequest {

    @NotNull(message = "O Período é de preenchimento obrigatório")
    private Integer periodo;

    @NotBlank(message = "O Curso é de preenchimento obrigatório")
    @Length(max = 100, message = "O Curso deverá ter no máximo {max} caracteres")
    private String curso;

    public Turma build() {

        return Turma.builder()
            .periodo(periodo)
            .curso(curso)
            .build();
    }
}