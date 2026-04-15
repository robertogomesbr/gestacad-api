package br.com.ifpe.gestacad.modelo.professor;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfessorRepository extends JpaRepository<Professor, Long> {
    
}
