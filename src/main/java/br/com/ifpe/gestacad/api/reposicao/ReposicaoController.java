package br.com.ifpe.gestacad.api.reposicao;

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

import br.com.ifpe.gestacad.modelo.disciplina.DisciplinaService;
import br.com.ifpe.gestacad.modelo.professor.ProfessorService;
import br.com.ifpe.gestacad.modelo.reposicao.Reposicao;
import br.com.ifpe.gestacad.modelo.reposicao.ReposicaoService;
import br.com.ifpe.gestacad.modelo.sala.SalaService;
import br.com.ifpe.gestacad.modelo.turma.TurmaService;

@RestController
@RequestMapping("/api/reposicao")
@CrossOrigin
public class ReposicaoController {
    
    @Autowired
    private ReposicaoService reposicaoService;

    @Autowired
    private DisciplinaService disciplinaService;

    @Autowired
    private TurmaService turmaService;

    @Autowired
    private ProfessorService professorService;

    @Autowired
    private SalaService salaService;

    @PostMapping
    public ResponseEntity<Reposicao> save(@RequestBody ReposicaoRequest request) {
        
        Reposicao reposicaoNova = request.build();
        reposicaoNova.setDisciplina(disciplinaService.obterPorID(request.getIdDisciplina()));
        reposicaoNova.setTurma(turmaService.obterPorID(request.getIdTurma()));
        reposicaoNova.setProfessor(professorService.obterPorID(request.getIdProfessor()));
        reposicaoNova.setSala(salaService.obterPorID(request.getIdSala()));
        Reposicao reposicao = reposicaoService.save(reposicaoNova);

        return new ResponseEntity<Reposicao>(reposicao, HttpStatus.CREATED);
    }

    @GetMapping
    public List<Reposicao> listarTodos() {

        return reposicaoService.listarTodos();
    }

    @GetMapping("/{id}")
    public Reposicao obterPorID(@PathVariable Long id) {
        
        return reposicaoService.obterPorID(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Reposicao> update(@PathVariable("id") Long id, @RequestBody ReposicaoRequest request) {

        Reposicao reposicao = request.build();
        reposicao.setDisciplina(disciplinaService.obterPorID(request.getIdDisciplina()));
        reposicao.setTurma(turmaService.obterPorID(request.getIdTurma()));
        reposicao.setProfessor(professorService.obterPorID(request.getIdProfessor()));
        reposicao.setSala(salaService.obterPorID(request.getIdSala()));
        reposicaoService.update(id, reposicao);
        
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        
        reposicaoService.delete(id);
        return ResponseEntity.ok().build();
    }
}
