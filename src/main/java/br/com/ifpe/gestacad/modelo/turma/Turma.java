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

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false, length = 50)
    private String turno;

    @Column(nullable = false)
    private Integer anoEntrada;

    @Column(nullable = false, length = 50)
    private String semestreEntrada;

    @Column(nullable = false)
    private Integer qtdMaximaAlunos;

    @Column(nullable = false)
    private Integer qtdAlunosMatriculados;
    
    private Boolean statusTurma;

}
