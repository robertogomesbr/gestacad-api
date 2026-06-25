package br.com.ifpe.gestacad.modelo.disciplina;


import org.hibernate.annotations.SQLRestriction;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
@Table(name = "Disciplina")
@SQLRestriction("habilitado = true")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Disciplina extends EntidadeAuditavel {

    @JsonIgnore
    @ManyToOne
    private Curso curso;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false)
    private Integer chTotal;

    @Column(length = 20)
    private String periodoOfertado;

}
