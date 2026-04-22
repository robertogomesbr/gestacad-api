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
import org.springframework.web.bind.annotation.RestController;

import br.com.ifpe.gestacad.modelo.turma.Turma;
import br.com.ifpe.gestacad.modelo.turma.TurmaService;

@RestController
@RequestMapping("/api/turma")
@CrossOrigin
public class TurmaController {

    @Autowired
    private TurmaService turmaService;

    @PostMapping
    public ResponseEntity<Turma> save(@RequestBody TurmaRequest request) {

        Turma turma = turmaService.save(request.build());
        return new ResponseEntity<Turma>(turma, HttpStatus.CREATED);
    }

    @GetMapping
    public List<Turma> listarTodos() {

        return turmaService.listarTodos();
    }

    @GetMapping("/{id}")
    public Turma obterPorID(@PathVariable Long id) {

        return turmaService.obterPorID(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Turma> update(@PathVariable("id") Long id, @RequestBody TurmaRequest request) {

        turmaService.update(id, request.build());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        turmaService.delete(id);
        return ResponseEntity.ok().build();
    }
}