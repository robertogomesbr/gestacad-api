package br.com.ifpe.gestacad.api.reposicao;

import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.ifpe.gestacad.modelo.reposicao.Reposicao;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReposicaoRequest {

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataAulaOriginal;
    
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataReposicao;

    @JsonFormat(pattern = "HH:mm")
    private LocalTime horarioInicio;

    @JsonFormat(pattern = "HH:mm")
    private LocalTime horarioFim;

    private Boolean statusReposicao;

    public Reposicao build() {

        return Reposicao.builder()
            .dataAulaOriginal(dataAulaOriginal)
            .dataReposicao(dataReposicao)
            .horarioInicio(horarioInicio)
            .horarioFim(horarioFim)
            .statusReposicao(statusReposicao)
            .build();
    }
}
