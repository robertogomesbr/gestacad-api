package br.com.ifpe.gestacad.api.sala;

import br.com.ifpe.gestacad.modelo.sala.Sala;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SalaRequest {
    
    private String blocoSelecionado;

    private Integer numero;

    private String tipo;

    private Boolean status;

    public Sala build() {

        return Sala.builder()
            .blocoSelecionado(blocoSelecionado)
            .numero(numero)
            .tipo(tipo)
            .status(status)
            .build();
    }
    
}
