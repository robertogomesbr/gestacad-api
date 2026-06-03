package br.com.ifpe.gestacad.api.alocacaoAula;

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

import br.com.ifpe.gestacad.api.horario.HorarioRequest;
import br.com.ifpe.gestacad.modelo.acesso.UsuarioService;
import br.com.ifpe.gestacad.modelo.alocacaoAula.AlocacaoAula;
import br.com.ifpe.gestacad.modelo.alocacaoAula.AlocacaoAulaService;
import br.com.ifpe.gestacad.modelo.disciplina.DisciplinaService;
import br.com.ifpe.gestacad.modelo.horario.Horario;
import br.com.ifpe.gestacad.modelo.professor.ProfessorService;
import br.com.ifpe.gestacad.modelo.sala.SalaService;
import br.com.ifpe.gestacad.modelo.turma.TurmaService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/alocacao-aula")
@CrossOrigin
@Tag(
        name = "API Alocação de Aula",
        description = "API responsável pelos serviços de alocação de aulas e horários no sistema"
)
public class AlocacaoAulaController {

    @Autowired
    private AlocacaoAulaService alocacaoAulaService;

    @Autowired
    private TurmaService turmaService;

    @Autowired
    private DisciplinaService disciplinaService;

    @Autowired
    private SalaService salaService;

    @Autowired
    private ProfessorService professorService;

    @Autowired
    private UsuarioService usuarioService;

    @Operation(
            summary = "Serviço responsável pela criação de uma alocação de aula.",
            description = "Endpoint responsável por cadastrar uma nova alocação de aula associando turma, disciplina, sala e professor."
    )
    @PostMapping
    public ResponseEntity<AlocacaoAula> save(@RequestBody @Valid AlocacaoAulaRequest alocacaoAulaRequest, HttpServletRequest request) {

        AlocacaoAula alocacaoAulaNovo = alocacaoAulaRequest.build();
        alocacaoAulaNovo.setTurma(turmaService.obterPorID(alocacaoAulaRequest.getIdTurma()));
        alocacaoAulaNovo.setDisciplina(disciplinaService.obterPorID(alocacaoAulaRequest.getIdDisciplina()));
        alocacaoAulaNovo.setSala(salaService.obterPorID(alocacaoAulaRequest.getIdSala()));
        alocacaoAulaNovo.setProfessor(professorService.obterPorID(alocacaoAulaRequest.getIdProfessor()));

        AlocacaoAula alocacaoAula = alocacaoAulaService.save(alocacaoAulaNovo, usuarioService.obterUsuarioLogado(request));

        return new ResponseEntity<>(alocacaoAula, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Serviço responsável por listar todas as alocações de aula.",
            description = "Endpoint responsável por retornar todas as alocações de aula cadastradas."
    )
    @GetMapping
    public List<AlocacaoAula> listarTodos() {

        return alocacaoAulaService.listarTodos();
    }

    @Operation(
            summary = "Serviço responsável por buscar uma alocação de aula pelo ID.",
            description = "Endpoint responsável por retornar uma alocação de aula específica a partir do seu ID."
    )
    @GetMapping("/{id}")
    public AlocacaoAula obterPorID(@PathVariable Long id) {

        return alocacaoAulaService.obterPorID(id);
    }

    @Operation(
            summary = "Serviço responsável por atualizar uma alocação de aula.",
            description = "Endpoint responsável por atualizar uma alocação de aula existente a partir do seu ID."
    )
    @PutMapping("/{id}")
    public ResponseEntity<AlocacaoAula> update(
            @PathVariable("id") Long id,
            @RequestBody @Valid AlocacaoAulaRequest alocacaoAularequest, HttpServletRequest request) {

        AlocacaoAula alocacaoAula = alocacaoAularequest.build();
        alocacaoAula.setTurma(turmaService.obterPorID(alocacaoAularequest.getIdTurma()));
        alocacaoAula.setDisciplina(disciplinaService.obterPorID(alocacaoAularequest.getIdDisciplina()));
        alocacaoAula.setSala(salaService.obterPorID(alocacaoAularequest.getIdSala()));
        alocacaoAula.setProfessor(professorService.obterPorID(alocacaoAularequest.getIdProfessor()));

        alocacaoAulaService.update(id, alocacaoAula, usuarioService.obterUsuarioLogado(request));

        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Serviço responsável por remover uma alocação de aula.",
            description = "Endpoint responsável por excluir uma alocação de aula a partir do seu ID."
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        alocacaoAulaService.delete(id);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Serviço responsável por adicionar um horário a uma alocação de aula.",
            description = "Endpoint responsável por cadastrar um novo horário vinculado a uma alocação de aula."
    )
    @PostMapping("/horario/{alocacaoAulaId}")
    public ResponseEntity<Horario> adicionarHorario(
            @PathVariable Long alocacaoAulaId,
            @RequestBody @Valid HorarioRequest request) {

        Horario horario = alocacaoAulaService.adicionarHorario(
                alocacaoAulaId,
                request.build());

        return new ResponseEntity<>(horario, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Serviço responsável por atualizar um horário.",
            description = "Endpoint responsável por atualizar os dados de um horário existente."
    )
    @PutMapping("/horario/{horarioId}")
    public ResponseEntity<Horario> atualizarHorario(
            @PathVariable Long horarioId,
            @RequestBody @Valid HorarioRequest request) {

        Horario horario = request.build();

        if (request.getIdAlocacaoAula() != null) {
            horario.setAlocacaoAula(
                    alocacaoAulaService.obterPorID(request.getIdAlocacaoAula()));
        }

        horario = alocacaoAulaService.atualizarHorario(horarioId, horario);

        return new ResponseEntity<>(horario, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Serviço responsável por remover um horário.",
            description = "Endpoint responsável por excluir um horário associado a uma alocação de aula."
    )
    @DeleteMapping("/horario/{horarioId}")
    public ResponseEntity<Void> removerHorario(@PathVariable("horarioId") Long horarioId) {

        alocacaoAulaService.removerHorario(horarioId);
        return ResponseEntity.noContent().build();
    }
}