package br.com.ifpe.gestacad.api.alocacaoAula;

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

import br.com.ifpe.gestacad.api.horario.HorarioRequest;
import br.com.ifpe.gestacad.modelo.alocacaoAula.AlocacaoAula;
import br.com.ifpe.gestacad.modelo.alocacaoAula.AlocacaoAulaService;
import br.com.ifpe.gestacad.modelo.disciplina.DisciplinaService;
import br.com.ifpe.gestacad.modelo.horario.Horario;
import br.com.ifpe.gestacad.modelo.professor.ProfessorService;
import br.com.ifpe.gestacad.modelo.sala.SalaService;
import br.com.ifpe.gestacad.modelo.turma.TurmaService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/alocacao-aula")
@CrossOrigin
public class AlocacaoAulaController {

    @Autowired
    private AlocacaoAulaService alocacaoAulaService;

    @Autowired
    private TurmaService turmaService;

    @Autowired
    private DisciplinaService disciplinaService;

    @Autowired
    private SalaService salaService;

    @Autowired
    private ProfessorService professorService;

    @PostMapping
    public ResponseEntity<AlocacaoAula> save(@RequestBody AlocacaoAulaRequest request) {

        AlocacaoAula alocacaoAulaNovo = request.build();
        alocacaoAulaNovo.setTurma(turmaService.obterPorID(request.getIdTurma()));
        alocacaoAulaNovo.setDisciplina(disciplinaService.obterPorID(request.getIdDisciplina()));
        alocacaoAulaNovo.setSala(salaService.obterPorID(request.getIdSala()));
        alocacaoAulaNovo.setProfessor(professorService.obterPorID(request.getIdProfessor()));
        AlocacaoAula alocacaoAula = alocacaoAulaService.save(alocacaoAulaNovo);

        return new ResponseEntity<AlocacaoAula>(alocacaoAula, HttpStatus.CREATED);
    }

    @GetMapping
    public List<AlocacaoAula> listarTodos() {

        return alocacaoAulaService.listarTodos();
    }

    @GetMapping("/{id}")
    public AlocacaoAula obterPorID(@PathVariable Long id) {

        return alocacaoAulaService.obterPorID(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AlocacaoAula> update(@PathVariable("id") Long id, @RequestBody AlocacaoAulaRequest request) {

        AlocacaoAula alocacaoAula = request.build();
        alocacaoAula.setTurma(turmaService.obterPorID(request.getIdTurma()));
        alocacaoAula.setDisciplina(disciplinaService.obterPorID(request.getIdDisciplina()));
        alocacaoAula.setSala(salaService.obterPorID(request.getIdSala()));
        alocacaoAula.setProfessor(professorService.obterPorID(request.getIdProfessor()));
        alocacaoAulaService.update(id, alocacaoAula);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        alocacaoAulaService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/horario/{alocacaoAulaId}")
    public ResponseEntity<Horario> adicionarHorario(@PathVariable("alocacaoAulaId") Long alocacaoAulaId,
            @RequestBody @Valid HorarioRequest request) {

        Horario horarioAula = alocacaoAulaService.adicionarHorario(alocacaoAulaId, request.build());

        return new ResponseEntity<Horario>(horarioAula, HttpStatus.CREATED);
    }

    @PutMapping("/horario/{horarioId}")
    public ResponseEntity<Horario> atualizarHorario(@PathVariable("horarioId") Long horarioId,
            @RequestBody HorarioRequest request) {

        Horario horario = alocacaoAulaService.atualizarHorario(horarioId, request.build());

        return new ResponseEntity<Horario>(horario, HttpStatus.OK);
    }

    @DeleteMapping("/horario/{horarioId}")
    public ResponseEntity<Void> removerHorario(@PathVariable("horarioId") Long horarioId) {

        alocacaoAulaService.removerHorario(horarioId);
        return ResponseEntity.noContent().build();
    }
}
