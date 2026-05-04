package br.com.ifpe.gestacad.api.sala;

import org.hibernate.validator.constraints.Length;

import br.com.ifpe.gestacad.modelo.sala.Sala;
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
public class SalaRequest {
    
    @NotBlank(message = "O Blobo é de preenchimento obrigatório")
    @Length(max = 50, message = "O Bloco deverá ter no máximo {max} caracteres")
    private String blocoSelecionado;

    @NotNull(message = "O Número é de preenchimento obrigatório")
    private Integer numero;

    @NotBlank(message = "O Tipo é de preenchimento obrigatório")
    @Length(max = 50, message = "O Tipo deverá ter no máximo {max} caracteres")
    private String tipo;

    public Sala build() {

        return Sala.builder()
            .blocoSelecionado(blocoSelecionado)
            .numero(numero)
            .tipo(tipo)
            .build();
    }
    
}
