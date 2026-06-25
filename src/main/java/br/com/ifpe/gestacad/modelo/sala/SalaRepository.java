package br.com.ifpe.gestacad.modelo.sala;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SalaRepository extends JpaRepository<Sala, Long> {
    
  List<Sala> findByTipoContainingIgnoreCase(String tipo);
List<Sala> findByBlocoContainingIgnoreCaseAndTipoContainingIgnoreCase(String bloco, String tipo);
  List<Sala> findByBlocoContainingIgnoreCase(String bloco);




}
