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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.ifpe.gestacad.modelo.acesso.UsuarioService;
import br.com.ifpe.gestacad.modelo.professor.Professor;
import br.com.ifpe.gestacad.modelo.professor.ProfessorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/professor")
@CrossOrigin
@Tag(
        name = "API Professor",
        description = "API responsável pelos serviços de professor no sistema"
)

public class ProfessorController {

    @Autowired
    private ProfessorService professorService;

    @Autowired
    private UsuarioService usuarioService;

    @Operation(
            summary = "Serviço responsável por salvar um professor no sistema.",
            description = "Exemplo de descrição de um endpoint responsável por inserir um professor no sistema."
    )

    @PostMapping
    public ResponseEntity<Professor> save(@RequestBody @Valid ProfessorRequest professorRequest, HttpServletRequest request) {

        Professor professor = professorService.save(professorRequest.build(), usuarioService.obterUsuarioLogado(request));
        return new ResponseEntity<>(professor, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Serviço responsável por listar todos os professores do sistema.",
            description = "Exemplo de descrição de um endpoint responsável por listar todos os professores do sistema."
    )

    @GetMapping
    public List<Professor> listarTodos() {

        return professorService.listarTodos();
    }

    @Operation(
            summary = "Serviço responsável por listar todos os professores do sistema pelo seu ID.",
            description = "Exemplo de descrição de um endpoint responsável por listar todos os professores do sistema pelo seu ID."
    )
    @GetMapping("/{id}")
    public Professor obterPorID(@PathVariable Long id) {

        return professorService.obterPorID(id);
    }

    @Operation(
            summary = "Serviço responsável por atualizar o professor pelo seu ID no sistema.",
            description = "Exemplo de descrição de um endpoint responsável por listar atualizar o professor pelo seu ID no sistema."
    )

    @PutMapping("/{id}")
    public ResponseEntity<Professor> update(@PathVariable("id") Long id, @RequestBody ProfessorRequest professorRequest, HttpServletRequest request) {

        professorService.update(id, professorRequest.build(), usuarioService.obterUsuarioLogado(request));
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Serviço responsável por deletar um professor pelo seu ID no sistema",
            description = "Exemplo de descrição de um endpoint responsável por deletar um professor pelo seu ID no sistema"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        professorService.delete(id);
        return ResponseEntity.ok().build();
    }

      @PostMapping("/filtrar")
   public List<Professor> filtrar(
           @RequestParam(value = "nome", required = false) String nome,
           @RequestParam(value = "cpf", required = false) String cpf) {

       return professorService.filtrar(nome, cpf);
   }

}
