package br.com.ifpe.gestacad.modelo.professor;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.ifpe.gestacad.modelo.acesso.Usuario;

public interface ProfessorRepository extends JpaRepository<Professor, Long> {
    
    Optional<Professor> findByUsuario(Usuario usuario);
}
