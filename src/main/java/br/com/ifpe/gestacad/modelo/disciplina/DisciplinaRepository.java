package br.com.ifpe.gestacad.modelo.disciplina;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DisciplinaRepository extends JpaRepository<Disciplina, Long> {

    // 1. Busca pelo ID do Curso mapeado na Disciplina
    List<Disciplina> findByCursoIdOrderByNomeAsc(Long idCurso);

    // 2. Busca aproximada por nome da Disciplina
    List<Disciplina> findByNomeContainingIgnoreCaseOrderByNomeAsc(String nome);

    // 3. COMBINADO: Busca aproximada por Nome E ID exato do Curso
    List<Disciplina> findByNomeContainingIgnoreCaseAndCursoIdOrderByNomeAsc(String nome, Long idCurso);
}

