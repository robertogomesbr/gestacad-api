package br.com.ifpe.gestacad.modelo.sala;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SalaRepository extends JpaRepository<Sala, Long> {
    

   
    @Query("SELECT s FROM Sala s WHERE LOWER(s.tipo) LIKE LOWER(CONCAT('%', :tipo, '%')) ORDER BY s.bloco ASC, s.numero ASC")
    List<Sala> consultarPorTipo(String tipo);

    @Query("SELECT s FROM Sala s WHERE LOWER(s.bloco) LIKE LOWER(CONCAT('%', :bloco, '%'))")
    List<Sala> consultarPorBloco(String bloco);

    @Query("SELECT s FROM Sala s WHERE s.numero = :numero ORDER BY s.bloco ASC, s.numero ASC")
    List<Sala> consultarPorNumero(Integer numero);

    @Query("SELECT s FROM Sala s WHERE LOWER(s.bloco) LIKE LOWER(CONCAT('%', :bloco, '%')) AND LOWER(s.tipo) LIKE LOWER(CONCAT('%', :tipo, '%'))")
    List<Sala> consultarPorBlocoETipo(String bloco, String tipo);

    @Query("SELECT s FROM Sala s WHERE LOWER(s.bloco) LIKE LOWER(CONCAT('%', :bloco, '%')) AND LOWER(s.tipo) LIKE LOWER(CONCAT('%', :tipo, '%')) AND s.numero = :numero ORDER BY s.bloco ASC, s.numero ASC")
    List<Sala> consultarPorBlocoETipoENumero(String bloco, String tipo, Integer numero);

    //evitar duplicidade em cadastrar salas
    @Query("SELECT COUNT(s) FROM Sala s WHERE s.bloco = :bloco AND s.numero = :numero AND s.tipo = :tipo")
    Long verificarDuplicidade(@Param("bloco") String bloco, @Param("numero") Integer numero, @Param("tipo") String tipo);
}
