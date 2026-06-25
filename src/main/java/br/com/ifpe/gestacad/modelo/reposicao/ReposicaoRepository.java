package br.com.ifpe.gestacad.modelo.reposicao;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ReposicaoRepository extends JpaRepository<Reposicao, Long> {

    @Query("""
            SELECT COUNT(r) FROM Reposicao r WHERE r.habilitado = true
            AND r.dataReposicao = :dataReposicao
            AND r.professor.id = :professorId
            AND r.horarioInicio < :horarioFim
            AND r.horarioFim > :horarioInicio
        """)
    Long verificarConflitoProfessor(Long professorId, LocalDate dataReposicao, LocalTime horarioInicio, LocalTime horarioFim);

    @Query("""
            SELECT COUNT(r) FROM Reposicao r WHERE r.habilitado = true
            AND r.dataReposicao = :dataReposicao
            AND r.sala.id = :salaId
            AND r.horarioInicio < :horarioFim
            AND r.horarioFim > :horarioInicio
        """)
    Long verificarConflitoSala(Long salaId, LocalDate dataReposicao, LocalTime horarioInicio, LocalTime horarioFim);

    @Query("""
            SELECT COUNT(r) FROM Reposicao r WHERE r.habilitado = true
            AND r.dataReposicao = :dataReposicao
            AND r.turma.id = :turmaId
            AND r.horarioInicio < :horarioFim
            AND r.horarioFim > :horarioInicio
        """)
    Long verificarConflitoTurma(Long turmaId, LocalDate dataReposicao, LocalTime horarioInicio, LocalTime horarioFim);

    @Query("""
            SELECT COUNT(r) FROM Reposicao r WHERE r.habilitado = true
            AND r.disciplina.id = :disciplinaId
            AND r.turma.id = :turmaId
            AND r.dataReposicao = :dataReposicao
            AND r.horarioInicio = :horarioInicio
            AND r.horarioFim = :horarioFim
        """)
    Long verificarReposicaoDuplicada(Long disciplinaId, Long turmaId, LocalDate dataReposicao, LocalTime horarioInicio, LocalTime horarioFim);

    // UPDATE

    @Query("""
            SELECT COUNT(r) FROM Reposicao r WHERE r.habilitado = true
            AND r.id <> :reposicaoId
            AND r.dataReposicao = :dataReposicao
            AND r.professor.id = :professorId
            AND r.horarioInicio < :horarioFim
            AND r.horarioFim > :horarioInicio
        """)
    Long verificarConflitoProfessorAtualizacao(Long reposicaoId, Long professorId, LocalDate dataReposicao, LocalTime horarioInicio, LocalTime horarioFim);

    @Query("""
            SELECT COUNT(r) FROM Reposicao r WHERE r.habilitado = true
            AND r.id <> :reposicaoId
            AND r.dataReposicao = :dataReposicao
            AND r.sala.id = :salaId
            AND r.horarioInicio < :horarioFim
            AND r.horarioFim > :horarioInicio
        """)
    Long verificarConflitoSalaAtualizacao(Long reposicaoId, Long salaId, LocalDate dataReposicao, LocalTime horarioInicio, LocalTime horarioFim);

    @Query("""
            SELECT COUNT(r) FROM Reposicao r WHERE r.habilitado = true
            AND r.id <> :reposicaoId
            AND r.dataReposicao = :dataReposicao
            AND r.turma.id = :turmaId
            AND r.horarioInicio < :horarioFim
            AND r.horarioFim > :horarioInicio
        """)
    Long verificarConflitoTurmaAtualizacao(Long reposicaoId, Long turmaId, LocalDate dataReposicao, LocalTime horarioInicio, LocalTime horarioFim);

    @Query("""
            SELECT COUNT(r) FROM Reposicao r WHERE r.habilitado = true
            AND r.id <> :reposicaoId
            AND r.disciplina.id = :disciplinaId
            AND r.turma.id = :turmaId
            AND r.dataReposicao = :dataReposicao
            AND r.horarioInicio = :horarioInicio
            AND r.horarioFim = :horarioFim
        """)
    Long verificarReposicaoDuplicadaAtualizacao(Long reposicaoId, Long disciplinaId, Long turmaId, LocalDate dataReposicao, LocalTime horarioInicio, LocalTime horarioFim);
}

