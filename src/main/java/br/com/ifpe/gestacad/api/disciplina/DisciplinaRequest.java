package br.com.ifpe.gestacad.api.disciplina;

import org.hibernate.validator.constraints.Length;

import br.com.ifpe.gestacad.modelo.disciplina.Disciplina;
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
public class DisciplinaRequest {

    private Long idCurso;

    @NotBlank(message ="O Nome é de preenchimento obrigatório")
    @Length(max = 100, message = "O Nome deverá ter no máximo {max} caracteres")
    private String nome;

    @NotNull(message ="A carga horária total é obrigatória")
    private Integer chTotal;

    @Length(max = 20)
    private String periodoOfertado;

    public Disciplina build() {

        return Disciplina.builder()
            .nome(nome)
            .chTotal(chTotal)
            .periodoOfertado(periodoOfertado)
            .build();
    }
}