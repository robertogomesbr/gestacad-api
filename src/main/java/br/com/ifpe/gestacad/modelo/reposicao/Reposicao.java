package br.com.ifpe.gestacad.modelo.reposicao;

import java.time.LocalDate;
import java.time.LocalTime;

import org.hibernate.annotations.SQLRestriction;

import br.com.ifpe.gestacad.modelo.disciplina.Disciplina;
import br.com.ifpe.gestacad.modelo.professor.Professor;
import br.com.ifpe.gestacad.modelo.sala.Sala;
import br.com.ifpe.gestacad.modelo.turma.Turma;
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
@Table(name = "Reposicao")
@SQLRestriction("habilitado = true")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Reposicao extends EntidadeAuditavel {
    
    @ManyToOne
    private Disciplina disciplina;

    @ManyToOne
    private Turma turma;

    @ManyToOne
    private Professor professor;
    
    @ManyToOne
    private Sala sala;

    @Column
    private LocalDate dataAulaOriginal;

    @Column
    private LocalDate dataReposicao;

    @Column
    private LocalTime horarioInicio;

    @Column
    private LocalTime horarioFim;

    @Column
    private Boolean statusReposicao;
    
}
