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

import br.com.ifpe.gestacad.modelo.reposicao.Reposicao;
import br.com.ifpe.gestacad.modelo.reposicao.ReposicaoService;

@RestController
@RequestMapping("/api/reposicao")
@CrossOrigin
public class ReposicaoController {
    
    @Autowired
    private ReposicaoService reposicaoService;

    @PostMapping
    public ResponseEntity<Reposicao> save(@RequestBody ReposicaoRequest request) {
        
        Reposicao reposicao = reposicaoService.save(request.build());
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

        reposicaoService.update(id, request.build());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        
        reposicaoService.delete(id);
        return ResponseEntity.ok().build();
    }
}
