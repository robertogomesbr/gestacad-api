package br.com.ifpe.gestacad.modelo.turma;

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
@Table(name = "Turma")
@SQLRestriction("habilitado = true")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Turma extends EntidadeAuditavel {

    @Column
    private Integer periodo;

    @Column
    private String curso;

}
