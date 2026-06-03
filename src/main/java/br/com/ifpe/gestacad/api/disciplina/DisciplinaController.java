package br.com.ifpe.gestacad.api.disciplina;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.ifpe.gestacad.modelo.curso.CursoService;
import br.com.ifpe.gestacad.modelo.disciplina.Disciplina;
import br.com.ifpe.gestacad.modelo.disciplina.DisciplinaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/disciplina")
@CrossOrigin
@Tag(
        name = "API Disciplina",
        description = "API responsável pelos serviços de disciplinas no sistema"
)
public class DisciplinaController {

    @Autowired
    private DisciplinaService disciplinaService;

    @Autowired
    private CursoService cursoService;

    @Operation(
            summary = "Serviço responsável pela criação de uma disciplina no sistema.",
            description = "Endpoint responsável por cadastrar uma nova disciplina associada a um curso."
    )
    @PostMapping
    public ResponseEntity<Disciplina> save(@RequestBody @Valid DisciplinaRequest request) {

        Disciplina disciplinaNovo = request.build();
        disciplinaNovo.setCurso(cursoService.obterPorID(request.getIdCurso()));
        Disciplina disciplina = disciplinaService.save(disciplinaNovo);

        return new ResponseEntity<>(disciplina, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Serviço responsável por listar todas as disciplinas do sistema.",
            description = "Endpoint responsável por retornar todas as disciplinas cadastradas."
    )
    @GetMapping
    public List<Disciplina> listarTodos() {

        return disciplinaService.listarTodos();
    }

    @Operation(
            summary = "Serviço responsável por buscar uma disciplina pelo ID.",
            description = "Endpoint responsável por retornar uma disciplina específica a partir do seu ID."
    )
    @GetMapping("/{id}")
    public Disciplina obterPorID(@PathVariable Long id) {

        return disciplinaService.obterPorID(id);
    }

    @Operation(
            summary = "Serviço responsável por atualizar uma disciplina pelo ID.",
            description = "Endpoint responsável por atualizar os dados de uma disciplina existente a partir do seu ID."
    )
    @PutMapping("/{id}")
    public ResponseEntity<Disciplina> update(
            @PathVariable("id") Long id,
            @RequestBody DisciplinaRequest request) {

        Disciplina disciplina = request.build();
        disciplina.setCurso(cursoService.obterPorID(request.getIdCurso()));
        disciplinaService.update(id, disciplina);

        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Serviço responsável por remover uma disciplina pelo ID.",
            description = "Endpoint responsável por excluir uma disciplina do sistema a partir do seu ID."
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        disciplinaService.delete(id);
        return ResponseEntity.ok().build();
    }
}