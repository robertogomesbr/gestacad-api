package br.com.ifpe.gestacad.modelo.turma;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TurmaRepository extends JpaRepository<Turma, Long> {
    
    List<Turma> findByNomeContainingIgnoreCaseOrderByNomeAsc(String nome);
    List<Turma> findByTurnoContainingIgnoreCase(String turno);

    @Query(value = "SELECT t FROM Turma t WHERE t.curso.id = :idCurso")
    List<Turma> consultarPorCurso(Long idCurso);

    // Nova query para combinar Nome e Curso (Relacionamento)
    @Query(value = "SELECT t FROM Turma t WHERE t.nome ilike %:nome% AND t.curso.id = :idCurso")
    List<Turma> consultarPorNomeECurso(String nome, Long idCurso);

    // Nova query para combinar Turno e Curso (Relacionamento)
    @Query(value = "SELECT t FROM Turma t WHERE t.turno ilike %:turno% AND t.curso.id = :idCurso")
    List<Turma> consultarPorTurnoECurso(String turno, Long idCurso);

    //verificar duplicidade de turma no cadastro
    @Query("SELECT COUNT(t) FROM Turma t WHERE t.turno = :turno AND t.semestreEntrada = :semestreEntrada AND t.anoEntrada = :anoEntrada AND t.curso.id = :idCurso")
    Long verificarDuplicidade( String turno, Long idCurso, String semestreEntrada, Integer anoEntrada);

       @Query("SELECT COUNT(t) FROM Turma t WHERE t.id <> :id and t.turno = :turno AND t.semestreEntrada = :semestreEntrada AND t.anoEntrada = :anoEntrada AND t.curso.id = :idCurso")
    Long verificarDuplicidadeAtualizacao( Long id, String turno, Long idCurso, String semestreEntrada, Integer anoEntrada);
}
