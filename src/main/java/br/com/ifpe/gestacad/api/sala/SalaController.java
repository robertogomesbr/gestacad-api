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

@RestController
@RequestMapping("/api/sala")
@CrossOrigin
public class SalaController {
    
    @Autowired
    private SalaService salaService;

    @PostMapping
    public ResponseEntity<Sala> save(@RequestBody SalaRequest request) {
        
        Sala sala = salaService.save(request.build());
        return new ResponseEntity<Sala>(sala, HttpStatus.CREATED);
    }

    @GetMapping
    public List<Sala> listarTodos() {

        return salaService.listarTodos();
    }

    @GetMapping("/{id}")
    public Sala obterPorID(@PathVariable Long id) {
        
        return salaService.obterPorID(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Sala> update(@PathVariable("id") Long id, @RequestBody SalaRequest request) {

        salaService.update(id, request.build());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        
        salaService.delete(id);
        return ResponseEntity.ok().build();
    }
}
