package br.com.ifpe.gestacad.api.curso;

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
import br.com.ifpe.gestacad.modelo.curso.Curso;
import br.com.ifpe.gestacad.modelo.curso.CursoService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/curso")
@CrossOrigin
public class CursoController {
    
    @Autowired
    private CursoService cursoService;

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<Curso> save(@RequestBody @Valid CursoRequest cursoRequest, HttpServletRequest request) {

        Curso curso = cursoService.save(cursoRequest.build(), usuarioService.obterUsuarioLogado(request));
        return new ResponseEntity<>(curso, HttpStatus.CREATED);
    }

    @GetMapping
    public List<Curso> listarTodos() {

        return cursoService.listarTodos();
    }

    @GetMapping("/{id}")
    public Curso obterPorID(@PathVariable Long id) {

        return cursoService.obterPorID(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Curso> update(@PathVariable("id") Long id, @RequestBody @Valid CursoRequest request) {

        cursoService.update(id, request.build());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        cursoService.delete(id);
        return ResponseEntity.ok().build();
    }
}
