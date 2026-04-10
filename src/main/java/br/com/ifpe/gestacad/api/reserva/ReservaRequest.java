package br.com.ifpe.gestacad.api.reserva;

import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.ifpe.gestacad.modelo.reserva.Reserva;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservaRequest {
    
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataReserva;

    @JsonFormat(pattern = "HH:mm")
    private LocalTime horarioInicio;

    @JsonFormat(pattern = "HH:mm")
    private LocalTime horarioFim;

    private Boolean statusReserva;
    
    public Reserva build() {

        return Reserva.builder()
            .dataReserva(dataReserva)
            .horarioInicio(horarioInicio)
            .horarioFim(horarioFim)
            .statusReserva(statusReserva)
            .build();
    }
}
