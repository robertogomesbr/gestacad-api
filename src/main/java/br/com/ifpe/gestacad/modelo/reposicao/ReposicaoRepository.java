package br.com.ifpe.gestacad.modelo.reposicao;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.ifpe.gestacad.modelo.sala.Sala;
public interface ReposicaoRepository extends JpaRepository<Reposicao, Long> {

    // ==========================================
    // QUERIES DE VALIDAÇÃO PARA CRIAÇÃO (SAVE)
    // ==========================================

    @Query("""
            SELECT COUNT(r) FROM Reposicao r WHERE r.habilitado = true
            AND r.dataReposicao = :dataReposicao
            AND r.sala.id = :salaId
            AND r.horarioInicio < :horarioFim
            AND r.horarioFim > :horarioInicio
        """)
    Long verificarConflitoSala(
            @Param("salaId") Long salaId, 
            @Param("dataAulaOriginal") LocalDate dataAulaOriginal, 
            @Param("dataReposicao") LocalDate dataReposicao, 
            @Param("horarioInicio") LocalTime horarioInicio, 
            @Param("horarioFim") LocalTime horarioFim);

    @Query("""
            SELECT COUNT(r) FROM Reposicao r WHERE r.habilitado = true
            AND r.dataReposicao = :dataReposicao
            AND r.turma.id = :turmaId
            AND r.horarioInicio < :horarioFim
            AND r.horarioFim > :horarioInicio
        """)
    Long verificarConflitoTurma(
            @Param("turmaId") Long turmaId,  
            @Param("dataAulaOriginal") LocalDate dataAulaOriginal, 
            @Param("dataReposicao") LocalDate dataReposicao, 
            @Param("horarioInicio") LocalTime horarioInicio, 
            @Param("horarioFim") LocalTime horarioFim);

    @Query("""
            SELECT COUNT(r) FROM Reposicao r WHERE r.habilitado = true
            AND r.disciplina.id = :disciplinaId
            AND r.turma.id = :turmaId
            AND r.dataReposicao = :dataReposicao
            AND r.horarioInicio < :horarioFim
            AND r.horarioFim > :horarioInicio
        """)
    Long verificarReposicaoDuplicada(
            @Param("disciplinaId") Long disciplinaId, 
            @Param("turmaId") Long turmaId, 
            @Param("dataAulaOriginal") LocalDate dataAulaOriginal, 
            @Param("dataReposicao") LocalDate dataReposicao, 
            @Param("horarioInicio") LocalTime horarioInicio, 
            @Param("horarioFim") LocalTime horarioFim);


    // ==========================================
    // QUERIES DE VALIDAÇÃO PARA ATUALIZAÇÃO (UPDATE)
    // ==========================================

    @Query("""
            SELECT COUNT(r) FROM Reposicao r WHERE r.habilitado = true
            AND r.id <> :reposicaoId
            AND r.dataReposicao = :dataReposicao
            AND r.sala.id = :salaId
            AND r.horarioInicio < :horarioFim
            AND r.horarioFim > :horarioInicio
        """)
    Long verificarConflitoSalaAtualizacao(
            @Param("reposicaoId") Long reposicaoId, 
            @Param("salaId") Long salaId,  
            @Param("dataAulaOriginal") LocalDate dataAulaOriginal, 
            @Param("dataReposicao") LocalDate dataReposicao, 
            @Param("horarioInicio") LocalTime horarioInicio, 
            @Param("horarioFim") LocalTime horarioFim);

    @Query("""
            SELECT COUNT(r) FROM Reposicao r WHERE r.habilitado = true
            AND r.id <> :reposicaoId
            AND r.dataReposicao = :dataReposicao
            AND r.turma.id = :turmaId
            AND r.horarioInicio < :horarioFim
            AND r.horarioFim > :horarioInicio
        """)
    Long verificarConflitoTurmaAtualizacao(
            @Param("reposicaoId") Long reposicaoId, 
            @Param("turmaId") Long turmaId,  
            @Param("dataAulaOriginal") LocalDate dataAulaOriginal, 
            @Param("dataReposicao") LocalDate dataReposicao, 
            @Param("horarioInicio") LocalTime horarioInicio, 
            @Param("horarioFim") LocalTime horarioFim);

    @Query("""
            SELECT COUNT(r) FROM Reposicao r WHERE r.habilitado = true
            AND r.id <> :reposicaoId
            AND r.disciplina.id = :disciplinaId
            AND r.turma.id = :turmaId
            AND r.dataReposicao = :dataReposicao
            AND r.horarioInicio < :horarioFim
            AND r.horarioFim > :horarioInicio
        """)
    Long verificarReposicaoDuplicadaAtualizacao(
            @Param("reposicaoId") Long reposicaoId, 
            @Param("disciplinaId") Long disciplinaId, 
            @Param("turmaId") Long turmaId,  
            @Param("dataAulaOriginal") LocalDate dataAulaOriginal, 
            @Param("dataReposicao") LocalDate dataReposicao, 
            @Param("horarioInicio") LocalTime horarioInicio, 
            @Param("horarioFim") LocalTime horarioFim);

    // ==========================================
    // BUSCA DINÂMICA DE SALAS DISPONÍVEIS
    // ==========================================
    @Query("""
        SELECT s FROM Sala s 
        WHERE s.habilitado = true 
        AND s.id NOT IN (
            SELECT r.sala.id FROM Reposicao r 
            WHERE r.habilitado = true 
            AND r.dataReposicao = :dataReposicao
            AND r.horarioInicio < :horarioFim
            AND r.horarioFim > :horarioInicio
        )
    """)
    List<Sala> listarSalasDisponiveis(
        @Param("dataReposicao") LocalDate dataReposicao,
        @Param("horarioInicio") LocalTime horarioInicio,
        @Param("horarioFim") LocalTime horarioFim
    );
}

