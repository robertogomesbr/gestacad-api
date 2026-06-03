package br.com.ifpe.gestacad.api.sala;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ifpe.gestacad.modelo.sala.Sala;
import br.com.ifpe.gestacad.modelo.sala.SalaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/sala")
@CrossOrigin
@Tag(
        name = "API Sala",
        description = "API responsável pelos serviços de sala no sistema"
)
public class SalaController {

    @Autowired
    private SalaService salaService;

    @Operation(
            summary = "Serviço responsável pela criação de uma sala no sistema.",
            description = "Exemplo de um endpoint responsável pela criação de uma sala no sistema"
    )
    @PostMapping
    public ResponseEntity<Sala> save(@RequestBody @Valid SalaRequest request) {

        Sala sala = salaService.save(request.build());
        return new ResponseEntity<>(sala, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Serviço responsável por listas as salas do sistema.",
            description = "Exemplo de um endpoint responsável por listas as salas do sistema"
    )
    @GetMapping
    public List<Sala> listarTodos() {

        return salaService.listarTodos();
    }

    @Operation(
            summary = "Serviço responsável por listas as salas do sistema a partir do ID.",
            description = "Exemplo de um endpoint responsável por listas as salas do sistema a partir do ID."
    )
    @GetMapping("/{id}")
    public Sala obterPorID(@PathVariable Long id) {

        return salaService.obterPorID(id);
    }

    @Operation(
            summary = "Serviço responsável por atualizar uma sala do sistema a partir do ID.",
            description = "Exemplo de um endpoint responsável por atualizar uma sala do sistema a partir do ID."
    )
    @PutMapping("/{id}")
    public ResponseEntity<Sala> update(@PathVariable("id") Long id, @RequestBody @Valid SalaRequest request) {

        salaService.update(id, request.build());
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Serviço responsável por deletar uma sala do sistema a partir do ID.",
            description = "Exemplo de um endpoint responsável por deletar uma sala do sistema a partir do ID."
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        salaService.delete(id);
        return ResponseEntity.ok().build();
    }
}
