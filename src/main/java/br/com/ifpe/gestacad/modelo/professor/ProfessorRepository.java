package br.com.ifpe.gestacad.modelo.professor;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.ifpe.gestacad.modelo.acesso.Usuario;

public interface ProfessorRepository extends JpaRepository<Professor, Long> {

  Optional<Professor> findByUsuario(Usuario usuario);

  List<Professor> findByNomeContainingIgnoreCaseOrderByNomeAsc(String nome);

  List<Professor> findByCpfContainingIgnoreCase(String cpf);

  // query para evitar duplicidade no email
  @Query("SELECT COUNT(p) FROM Professor p WHERE p.usuario.username = :username")
  Long verificarDuplicidadeEmail(Usuario username);

  @Query("SELECT COUNT(p) FROM Professor p WHERE p.id <> :id AND p.username = :nome")
  Long verificarDuplicidadeEmailAtualizacao(Long id, Usuario username);
}
