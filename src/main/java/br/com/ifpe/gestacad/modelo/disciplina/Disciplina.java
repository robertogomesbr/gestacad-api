package br.com.ifpe.gestacad.modelo.disciplina;

import java.time.LocalTime;

import org.hibernate.annotations.SQLRestriction;

import br.com.ifpe.gestacad.util.entity.EntidadeAuditavel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Disciplina")
@SQLRestriction("habilitado = true")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Disciplina extends EntidadeAuditavel {

    @Column
    private String nome;

    @Column
    private String area;

    @Column
    private LocalTime horarioInicio;

    @Column
    private LocalTime horarioFim;

}
