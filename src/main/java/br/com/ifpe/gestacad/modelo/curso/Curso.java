package br.com.ifpe.gestacad.modelo.curso;

import java.util.List;

import org.hibernate.annotations.SQLRestriction;

import br.com.ifpe.gestacad.modelo.disciplina.Disciplina;
import br.com.ifpe.gestacad.util.entity.EntidadeAuditavel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Curso")
@SQLRestriction("habilitado = true")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Curso extends EntidadeAuditavel {

    @OneToMany(mappedBy = "curso", orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Disciplina> disciplinas;

    @Column
    private String nome;

    @Column
    private Integer qtdPeriodos;

    @Column
    private String area;

}
