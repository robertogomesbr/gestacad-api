package br.com.ifpe.gestacad.modelo.turma_disciplina;

import org.hibernate.annotations.SQLRestriction;

import br.com.ifpe.gestacad.util.entity.EntidadeAuditavel;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Turma_Disciplina")
@SQLRestriction("habilitado = true")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class TurmaDisciplina extends EntidadeAuditavel {

    private Integer idTurma;

    private Integer idDisciplina;
}
