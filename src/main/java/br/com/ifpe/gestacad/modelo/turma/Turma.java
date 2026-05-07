package br.com.ifpe.gestacad.modelo.turma;

import org.hibernate.annotations.SQLRestriction;

import br.com.ifpe.gestacad.modelo.curso.Curso;
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
@Table(name = "Turma")
@SQLRestriction("habilitado = true")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Turma extends EntidadeAuditavel {

    @ManyToOne
    private Curso curso;

    @Column
    private String nome;

    @Column
    private Integer anoEntrada;

    @Column
    private String semestreEntrada;

    @Column
    private Integer qtdMaximaAlunos;

    @Column
    private Integer qtdAlunosMatriculados;
    
    @Column
    private Boolean statusTurma;

}
