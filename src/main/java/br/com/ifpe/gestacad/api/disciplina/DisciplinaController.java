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

import br.com.ifpe.gestacad.modelo.disciplina.Disciplina;
import br.com.ifpe.gestacad.modelo.disciplina.DisciplinaService;

@RestController
@RequestMapping("/api/disciplina")
@CrossOrigin
public class DisciplinaController {

    @Autowired
    private DisciplinaService disciplinaService;

    @PostMapping
    public ResponseEntity<Disciplina> save(@RequestBody DisciplinaRequest request) {

        Disciplina disciplina = disciplinaService.save(request.build());
        return new ResponseEntity<Disciplina>(disciplina, HttpStatus.CREATED);
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

        disciplinaService.update(id, request.build());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        disciplinaService.delete(id);
        return ResponseEntity.ok().build();
    }
}