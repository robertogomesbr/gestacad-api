package br.com.ifpe.gestacad.modelo.curso;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CursoRepository extends JpaRepository<Curso, Long> {

  @Query(value = "SELECT c FROM Curso c WHERE LOWER(c.nome) LIKE LOWER(CONCAT('%', :nome, '%')) ORDER BY c.nome ASC")
  List<Curso> consultarPorNome(String nome);

  @Query(value = "SELECT c FROM Curso c WHERE LOWER(c.area) LIKE LOWER(CONCAT('%', :area, '%')) ORDER BY c.area ASC")
  List<Curso> consultarPorArea(String area);

  @Query(value = "SELECT c FROM Curso c WHERE LOWER(c.nome) LIKE LOWER(CONCAT('%', :nome, '%')) AND LOWER(c.area) LIKE LOWER(CONCAT('%', :area, '%')) ORDER BY c.nome ASC")
  List<Curso> consultarPorNomeEArea(String nome, String area);

  @Query("SELECT COUNT(c) FROM Curso c WHERE c.nome = :nome AND c.area = :area")
  Long verificarDuplicidade(String nome, String area);

  @Query("SELECT COUNT(c) FROM Curso c WHERE c.id <> :id AND c.nome = :nome AND c.area = :area")
  Long verificarDuplicidadeAtualizacao(Long id, String nome, String area);

}
