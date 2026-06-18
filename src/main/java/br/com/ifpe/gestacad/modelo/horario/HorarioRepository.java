package br.com.ifpe.gestacad.modelo.horario;

import java.time.LocalTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface HorarioRepository extends JpaRepository<Horario, Long> {

    // CREATE

    @Query("""
            SELECT COUNT(h) FROM Horario h WHERE h.habilitado = true
            AND h.diaSemana = :diaSemana
            AND h.alocacaoAula.professor.id = :professorId
            AND h.horarioInicio < :horarioFim
            AND h.horarioFim > :horarioInicio
        """)
    Long verificarConflitoProfessor(Long professorId, String diaSemana, LocalTime horarioInicio,
        LocalTime horarioFim);

    @Query("""
            SELECT COUNT(h) FROM Horario h WHERE h.habilitado = true
            AND h.diaSemana = :diaSemana
            AND h.alocacaoAula.sala.id = :salaId
            AND h.horarioInicio < :horarioFim
            AND h.horarioFim > :horarioInicio
        """)
    Long verificarConflitoSala(Long salaId, String diaSemana, LocalTime horarioInicio,
        LocalTime horarioFim);

    @Query("""
            SELECT COUNT(h) FROM Horario h WHERE h.habilitado = true
            AND h.diaSemana = :diaSemana
            AND h.alocacaoAula.turma.id = :turmaId
            AND h.horarioInicio < :horarioFim
            AND h.horarioFim > :horarioInicio
        """)
    Long verificarConflitoTurma(Long turmaId, String diaSemana, LocalTime horarioInicio,
        LocalTime horarioFim);

    @Query("""
            SELECT COUNT(h) FROM Horario h WHERE h.habilitado = true
            AND h.alocacaoAula.id = :alocacaoId
            AND h.diaSemana = :diaSemana
            AND h.horarioInicio = :horarioInicio
            AND h.horarioFim = :horarioFim
        """)
    Long verificarHorarioDuplicado(Long alocacaoId, String diaSemana, LocalTime horarioInicio,
        LocalTime horarioFim);

    // UPDATE

    @Query("""
            SELECT COUNT(h) FROM Horario h WHERE h.habilitado = true
            AND h.id <> :horarioId
            AND h.diaSemana = :diaSemana
            AND h.alocacaoAula.professor.id = :professorId
            AND h.horarioInicio < :horarioFim
            AND h.horarioFim > :horarioInicio
        """)
    Long verificarConflitoProfessorAtualizacao(Long horarioId, Long professorId, String diaSemana,
        LocalTime horarioInicio, LocalTime horarioFim);

    @Query("""
            SELECT COUNT(h) FROM Horario h WHERE h.habilitado = true
            AND h.id <> :horarioId
            AND h.diaSemana = :diaSemana
            AND h.alocacaoAula.sala.id = :salaId
            AND h.horarioInicio < :horarioFim
            AND h.horarioFim > :horarioInicio
        """)
    Long verificarConflitoSalaAtualizacao(Long horarioId, Long salaId, String diaSemana,
        LocalTime horarioInicio, LocalTime horarioFim);

    @Query("""
            SELECT COUNT(h) FROM Horario h WHERE h.habilitado = true
            AND h.id <> :horarioId
            AND h.diaSemana = :diaSemana
            AND h.alocacaoAula.turma.id = :turmaId
            AND h.horarioInicio < :horarioFim
            AND h.horarioFim > :horarioInicio
        """)
    Long verificarConflitoTurmaAtualizacao(Long horarioId, Long turmaId, String diaSemana,
        LocalTime horarioInicio, LocalTime horarioFim);

    @Query("""
            SELECT COUNT(h) FROM Horario h WHERE h.habilitado = true
            AND h.id <> :horarioId
            AND h.alocacaoAula.id = :alocacaoId
            AND h.diaSemana = :diaSemana
            AND h.horarioInicio = :horarioInicio
            AND h.horarioFim = :horarioFim
        """)
    Long verificarHorarioDuplicadoAtualizacao(Long horarioId, Long alocacaoId, String diaSemana,
        LocalTime horarioInicio, LocalTime horarioFim);

}
