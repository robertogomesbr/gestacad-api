package br.com.ifpe.gestacad.api.professor;

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

import br.com.ifpe.gestacad.modelo.acesso.UsuarioService;
import br.com.ifpe.gestacad.modelo.professor.Professor;
import br.com.ifpe.gestacad.modelo.professor.ProfessorService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/professor")
@CrossOrigin
public class ProfessorController {

    @Autowired
    private ProfessorService professorService;

    @Autowired
    private UsuarioService usuarioService;


    @PostMapping
    public ResponseEntity<Professor> save(@RequestBody  @Valid ProfessorRequest professorRequest, HttpServletRequest request) {

        Professor professor = professorService.save(professorRequest.build(), usuarioService.obterUsuarioLogado(request));
        return new ResponseEntity<>(professor, HttpStatus.CREATED);
    }

    @GetMapping
    public List<Professor> listarTodos() {

        return professorService.listarTodos();
    }

    @GetMapping("/{id}")
    public Professor obterPorID(@PathVariable Long id) {

        return professorService.obterPorID(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Professor> update(@PathVariable("id") Long id, @RequestBody ProfessorRequest professorRequest, HttpServletRequest request) {

        professorService.update(id, professorRequest.build(), usuarioService.obterUsuarioLogado(request));
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        professorService.delete(id);
        return ResponseEntity.ok().build();
    }
}