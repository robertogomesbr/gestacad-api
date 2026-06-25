package br.com.ifpe.gestacad.api.reposicao;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import br.com.ifpe.gestacad.modelo.acesso.UsuarioService;
import br.com.ifpe.gestacad.modelo.disciplina.DisciplinaService;
import br.com.ifpe.gestacad.modelo.professor.ProfessorService;
import br.com.ifpe.gestacad.modelo.reposicao.Reposicao;
import br.com.ifpe.gestacad.modelo.reposicao.ReposicaoService;
import br.com.ifpe.gestacad.modelo.sala.SalaService;
import br.com.ifpe.gestacad.modelo.turma.TurmaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/reposicao")
@CrossOrigin
@Tag(
        name = "API Reposição",
        description = "API responsável pelos serviços de reposição no sistema"
)
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

    @Autowired
    private UsuarioService usuarioService;

    @Operation(
            summary = "Serviço responsável pela criação de uma reposição no sistema.",
            description = "Exemplo de um endpoint responsável pela criação de uma reposição no sistema"
    )
    @PostMapping
    public ResponseEntity<?> save(@RequestBody @Valid ReposicaoRequest reposicaoRequest, HttpServletRequest request) {
        try {
            Reposicao reposicaoNova = reposicaoRequest.build();
            reposicaoNova.setDisciplina(disciplinaService.obterPorID(reposicaoRequest.getIdDisciplina()));
            reposicaoNova.setTurma(turmaService.obterPorID(reposicaoRequest.getIdTurma()));
            reposicaoNova.setProfessor(professorService.obterPorID(reposicaoRequest.getIdProfessor()));
            reposicaoNova.setSala(salaService.obterPorID(reposicaoRequest.getIdSala()));
            
            Reposicao reposicao = reposicaoService.save(reposicaoNova, usuarioService.obterUsuarioLogado(request));
            return new ResponseEntity<>(reposicao, HttpStatus.CREATED);
            
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getLocalizedMessage());
        }
    }

    @Operation(
            summary = "Serviço responsável por listar as reposições do sistema.",
            description = "Exemplo de um endpoint responsável por listar as reposições do sistema."
    )
    @GetMapping
    public List<Reposicao> listarTodos() {
        return reposicaoService.listarTodos();
    }

    @Operation(
            summary = "Serviço responsável por listar a reposição do sistema a partir do seu ID.",
            description = "Exemplo de um endpoint responsável por listar a reposição do sistema a partir do seu ID."
    )
    @GetMapping("/{id}")
    public Reposicao obtenerPorID(@PathVariable Long id) {
        return reposicaoService.obterPorID(id);
    }

    @Operation(
            summary = "Serviço responsável por atualizar a reposição do sistema a partir do seu ID.",
            description = "Exemplo de um endpoint responsável por atualizar a reposição do sistema a partir do seu ID."
    )
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody ReposicaoRequest reposicaoRequest, HttpServletRequest request) {
        try {
            Reposicao reposicao = reposicaoRequest.build();
            reposicao.setDisciplina(disciplinaService.obterPorID(reposicaoRequest.getIdDisciplina()));
            reposicao.setTurma(turmaService.obterPorID(reposicaoRequest.getIdTurma()));
            reposicao.setProfessor(professorService.obterPorID(reposicaoRequest.getIdProfessor()));
            reposicao.setSala(salaService.obterPorID(reposicaoRequest.getIdSala()));
            
            reposicaoService.update(id, reposicao, usuarioService.obterUsuarioLogado(request));
            return ResponseEntity.ok().build();
            
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getLocalizedMessage());
        }
    }

    @Operation(
            summary = "Serviço responsável por deletar a reposição do sistema a partir do seu ID.",
            description = "Exemplo de um endpoint responsável por deletar a reposição do sistema a partir do seu ID."
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        reposicaoService.delete(id);
        return ResponseEntity.ok().build();
    }
}
