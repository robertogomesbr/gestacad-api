package br.com.ifpe.gestacad.api.curso;

import org.hibernate.validator.constraints.Length;

import br.com.ifpe.gestacad.modelo.curso.Curso;
import jakarta.validation.constraints.Min;
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
public class CursoRequest {

    @NotBlank(message ="O Nome é de preenchimento obrigatório")
    @Length(max = 50, message = "O Nome deverá ter no máximo {max} caracteres")
    private String nome;
    
    @NotNull(message = "A quantidade de períodos precisa ser preenchida")
    @Min(value = 1, message = "A quantidade de períodos deve ser maior que zero")
    private Integer qtdPeriodos;

    @NotBlank(message ="A área é de preenchimento obrigatória")
    @Length(max = 50, message = "A área deverá ter no máximo {max} caracteres")
    private String area;

    public Curso build() {

        return Curso.builder()
            .nome(nome)
            .qtdPeriodos(qtdPeriodos)
            .area(area)
            .build();
    }

}
