package br.com.ifpe.gestacad.api.turma;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.ifpe.gestacad.modelo.curso.CursoService;
import br.com.ifpe.gestacad.modelo.turma.Turma;
import br.com.ifpe.gestacad.modelo.turma.TurmaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/turma")
@CrossOrigin
@Tag(name = "API Turma", description = "API responsável pelos serviços de turma no sistema.")
public class TurmaController {

    @Autowired
    private TurmaService turmaService;

    @Autowired
    CursoService cursoService;

    @Operation(summary = "Serviço responsável pela criação de uma turma no sistema.", description = "Exemplo de um endpoint responsável pela criação de uma turma no sistema")
    @PostMapping
    public ResponseEntity<Turma> save(@RequestBody @Valid TurmaRequest request) {

        Turma turmaNova = request.build();
        turmaNova.setCurso(cursoService.obterPorID(request.getIdCurso()));
        Turma turma = turmaService.save(turmaNova);

        return new ResponseEntity<>(turma, HttpStatus.CREATED);
    }

    @Operation(summary = "Serviço responsável por listas as turmas do sistema.", description = "Exemplo de um endpoint responsável por listas as turmas do sistema")
    @GetMapping
    public List<Turma> listarTodos() {

        return turmaService.listarTodos();
    }

    @Operation(summary = "Serviço responsável por listas as turmas do sistema a partir do ID.", description = "Exemplo de um endpoint responsável por listas as turmas do sistema a partir do ID.")
    @GetMapping("/{id}")
    public Turma obterPorID(@PathVariable Long id) {

        return turmaService.obterPorID(id);
    }

    @Operation(summary = "Serviço responsável por atualizar a turma do sistema a partir do ID.", description = "Exemplo de um endpoint responsável por atualizar a turma do sistema a partir do ID.")
    @PutMapping("/{id}")
    public ResponseEntity<Turma> update(@PathVariable("id") Long id, @RequestBody @Valid TurmaRequest request) {

        Turma turma = request.build();
        turma.setCurso(cursoService.obterPorID(request.getIdCurso()));
        turmaService.update(id, turma);

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Serviço responsável por deletar uma turma do sistema a partir do ID.", description = "Exemplo de um endpoint responsável por deletar uma turma do sistema a partir do ID.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        turmaService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/filtrar")
    public List<Turma> filtrar(
            @RequestParam(value = "nome", required = false) String nome,
            @RequestParam(value = "turno", required = false) String turno,
            @RequestParam(value = "idCurso", required = false) Long idCurso) {

        return turmaService.filtrar(nome, turno, idCurso);
    }

}
