package br.com.ifpe.gestacad.api.horario;

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

import br.com.ifpe.gestacad.modelo.horario.Horario;
import br.com.ifpe.gestacad.modelo.horario.HorarioService;

@RestController
@RequestMapping("/api/horario")
@CrossOrigin
public class HorarioController {

    @Autowired
    private HorarioService horarioService;

    @PostMapping
    public ResponseEntity<Horario> save(@RequestBody HorarioRequest request) {
        
        Horario horario = horarioService.save(request.build());
        return new ResponseEntity<Horario>(horario, HttpStatus.CREATED);
    }

    @GetMapping
    public List<Horario> listarTodos() {

        return horarioService.listarTodos();
    }

    @GetMapping("/{id}")
    public Horario obterPorID(@PathVariable Long id) {
        
        return horarioService.obterPorID(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Horario> update(@PathVariable("id") Long id, @RequestBody HorarioRequest request) {

        horarioService.update(id, request.build());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        
        horarioService.delete(id);
        return ResponseEntity.ok().build();
    }
    
}
