package br.com.ifpe.gestacad.modelo.reserva;

import java.time.LocalDate;
import java.time.LocalTime;

import org.hibernate.annotations.SQLRestriction;

import br.com.ifpe.gestacad.modelo.sala.Sala;
import br.com.ifpe.gestacad.util.entity.EntidadeAuditavel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Reserva")
@SQLRestriction("habilitado = true")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Reserva extends EntidadeAuditavel{
    
    @ManyToOne
    private Sala sala;

    @Column
    private LocalDate dataReserva;

    @Column
    private LocalTime horarioInicio;

    @Column
    private LocalTime horarioFim;

    @Column
    private Boolean statusReserva;

}
