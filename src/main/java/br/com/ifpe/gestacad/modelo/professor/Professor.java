package br.com.ifpe.gestacad.modelo.professor;

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
@Table(name = "Professor")
@SQLRestriction("habilitado = true")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Professor extends EntidadeAuditavel {

    @Column
    private String nome;

    @Column
    private String cpf;

    @Column
    private String senha;

    @Column
    private String siape;

    @Column
    private String email;

    @Column
    private boolean ativo;
}
