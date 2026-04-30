package br.com.ifpe.gestacad.api.disciplina;

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
import br.com.ifpe.gestacad.modelo.disciplina.Disciplina;
import br.com.ifpe.gestacad.modelo.disciplina.DisciplinaService;
import br.com.ifpe.gestacad.modelo.horario.Horario;
import br.com.ifpe.gestacad.modelo.professor.ProfessorService;
import br.com.ifpe.gestacad.modelo.turma.TurmaService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/disciplina")
@CrossOrigin
public class DisciplinaController {

    @Autowired
    private DisciplinaService disciplinaService;

    @Autowired
    private ProfessorService professorService;

    @Autowired
    private TurmaService turmaService;

    @PostMapping
    public ResponseEntity<Disciplina> save(@RequestBody DisciplinaRequest request) {

        Disciplina disciplinaNovo = request.build();
        disciplinaNovo.setProfessor(professorService.obterPorID(request.getIdProfessor()));
        disciplinaNovo.setTurma(turmaService.obterPorID(request.getIdTurma()));
        Disciplina disciplina = disciplinaService.save(disciplinaNovo);

        return new ResponseEntity<>(disciplina, HttpStatus.CREATED);
    }

    @GetMapping
    public List<Disciplina> listarTodos() {

        return disciplinaService.listarTodos();
    }

    @GetMapping("/{id}")
    public Disciplina obterPorID(@PathVariable Long id) {

        return disciplinaService.obterPorID(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Disciplina> update(@PathVariable("id") Long id, @RequestBody DisciplinaRequest request) {

        Disciplina disciplina = request.build();
        disciplina.setProfessor(professorService.obterPorID(request.getIdProfessor()));
        disciplina.setTurma(turmaService.obterPorID(request.getIdTurma()));
        disciplinaService.update(id, disciplina);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        disciplinaService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/horario/{disciplinaId}")
    public ResponseEntity<Horario> adicionarHorario(@PathVariable("disciplinaId") Long disciplinaId,
            @RequestBody @Valid HorarioRequest request) {

        Horario horario = disciplinaService.adicionarHorario(disciplinaId, request.build());
        return new ResponseEntity<>(horario, HttpStatus.CREATED);
    }

    @PutMapping("/horario/{horarioId}")
    public ResponseEntity<Horario> atualizarHorario(@PathVariable("horarioId") Long horarioId,
            @RequestBody HorarioRequest request) {

        Horario horario = disciplinaService.atualizarHorario(horarioId, request.build());
        return new ResponseEntity<>(horario, HttpStatus.OK);
    }

    @DeleteMapping("/horario/{horarioId}")
    public ResponseEntity<Void> removerHorario(@PathVariable("horarioId") Long horarioId) {

        disciplinaService.removerHorario(horarioId);
        return ResponseEntity.noContent().build();
    }

}