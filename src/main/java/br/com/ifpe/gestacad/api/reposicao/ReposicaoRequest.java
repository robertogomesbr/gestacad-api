package br.com.ifpe.gestacad.api.reposicao;

import java.time.LocalDate;
import java.time.LocalTime;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.ifpe.gestacad.modelo.reposicao.Reposicao;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReposicaoRequest {

    private Long idDisciplina;

    private Long idTurma;

    private Long idProfessor;

    private Long idSala;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataAulaOriginal;
    
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataReposicao;

    @JsonFormat(pattern = "HH:mm")
    private LocalTime horarioInicio;

    @JsonFormat(pattern = "HH:mm")
    private LocalTime horarioFim;

    @NotBlank(message = "O status da reposição é obrigatório")
    @Length(max = 30, message = "O status deve possuir no máximo {max} caracteres")
    private String statusReposicao;

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
