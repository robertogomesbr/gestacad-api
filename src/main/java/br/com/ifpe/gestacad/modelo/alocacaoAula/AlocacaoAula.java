package br.com.ifpe.gestacad.modelo.alocacaoAula;

import org.hibernate.annotations.SQLRestriction;

import br.com.ifpe.gestacad.modelo.disciplina.Disciplina;
import br.com.ifpe.gestacad.modelo.professor.Professor;
import br.com.ifpe.gestacad.modelo.sala.Sala;
import br.com.ifpe.gestacad.modelo.turma.Turma;
import br.com.ifpe.gestacad.util.entity.EntidadeAuditavel;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "AlocacaoAula")
@SQLRestriction("habilitado = true")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class AlocacaoAula extends EntidadeAuditavel{
    
    @ManyToOne
    private Turma turma;

    @ManyToOne
    private Disciplina disciplina;

    @ManyToOne
    private Sala sala;

    @ManyToOne
    private Professor professor;

}
