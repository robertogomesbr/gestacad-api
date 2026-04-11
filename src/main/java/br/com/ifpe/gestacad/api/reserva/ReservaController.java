package br.com.ifpe.gestacad.api.reserva;

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

import br.com.ifpe.gestacad.modelo.reserva.Reserva;
import br.com.ifpe.gestacad.modelo.reserva.ReservaService;
import br.com.ifpe.gestacad.modelo.sala.SalaService;

@RestController
@RequestMapping("/api/reserva")
@CrossOrigin
public class ReservaController {
    
    @Autowired
    private ReservaService reservaService;

    @Autowired
    private SalaService salaService;

    @PostMapping
    public ResponseEntity<Reserva> save(@RequestBody ReservaRequest request) {
        
        Reserva reservaNovo = request.build();
        reservaNovo.setSala(salaService.obterPorID(request.getIdSala()));
        Reserva reserva = reservaService.save(reservaNovo);

        return new ResponseEntity<Reserva>(reserva, HttpStatus.CREATED);
    }

    @GetMapping
    public List<Reserva> listarTodos() {

        return reservaService.listarTodos();
    }

    @GetMapping("/{id}")
    public Reserva obterPorID(@PathVariable Long id) {
        
        return reservaService.obterPorID(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Reserva> update(@PathVariable("id") Long id, @RequestBody ReservaRequest request) {

        Reserva reserva = request.build();
        reserva.setSala(salaService.obterPorID(request.getIdSala()));
        reservaService.update(id, reserva);
        
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        
        reservaService.delete(id);
        return ResponseEntity.ok().build();
    }
}
