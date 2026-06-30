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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.ifpe.gestacad.modelo.acesso.UsuarioService;
import br.com.ifpe.gestacad.modelo.curso.CursoService;
import br.com.ifpe.gestacad.modelo.disciplina.Disciplina;
import br.com.ifpe.gestacad.modelo.disciplina.DisciplinaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/disciplina")
@CrossOrigin
@Tag(name = "API Disciplina", description = "API responsável pelos serviços de disciplinas no sistema")
public class DisciplinaController {

        @Autowired
        private DisciplinaService disciplinaService;

        @Autowired
        private CursoService cursoService;

        @Autowired
        private UsuarioService usuarioService;

        @Operation(summary = "Serviço responsável pela criação de uma disciplina no sistema.", description = "Endpoint responsável por cadastrar uma nova disciplina associada a um curso.")
        @PostMapping
        public ResponseEntity<Disciplina> save(@RequestBody @Valid DisciplinaRequest disciplinaRequest,
                        HttpServletRequest request) {

                Disciplina disciplinaNovo = disciplinaRequest.build();
                disciplinaNovo.setCurso(cursoService.obterPorID(disciplinaRequest.getIdCurso()));
                Disciplina disciplina = disciplinaService.save(disciplinaNovo,
                                usuarioService.obterUsuarioLogado(request));

                return new ResponseEntity<>(disciplina, HttpStatus.CREATED);
        }

        @Operation(summary = "Serviço responsável por listar todas as disciplinas do sistema.", description = "Endpoint responsável por retornar todas as disciplinas cadastradas.")
        @GetMapping
        public List<Disciplina> listarTodos() {

                return disciplinaService.listarTodos();
        }

        @Operation(summary = "Serviço responsável por buscar uma disciplina pelo ID.", description = "Endpoint responsável por retornar uma disciplina específica a partir do seu ID.")
        @GetMapping("/{id}")
        public Disciplina obterPorID(@PathVariable Long id) {

                return disciplinaService.obterPorID(id);
        }

        @Operation(summary = "Serviço responsável por atualizar uma disciplina pelo ID.", description = "Endpoint responsável por atualizar os dados de uma disciplina existente a partir do seu ID.")
        @PutMapping("/{id}")
        public ResponseEntity<Disciplina> update(
                        @PathVariable("id") Long id,
                        @RequestBody DisciplinaRequest disciplinaRequest, HttpServletRequest request) {

                Disciplina disciplina = disciplinaRequest.build();
                disciplina.setCurso(cursoService.obterPorID(disciplinaRequest.getIdCurso()));
                disciplinaService.update(id, disciplina, usuarioService.obterUsuarioLogado(request));

                return ResponseEntity.ok().build();
        }

        @Operation(summary = "Serviço responsável por remover uma disciplina pelo ID.", description = "Endpoint responsável por excluir uma disciplina do sistema a partir do seu ID.")
        @DeleteMapping("/{id}")
        public ResponseEntity<Void> delete(@PathVariable Long id) {

                disciplinaService.delete(id);
                return ResponseEntity.ok().build();
        }

        @PostMapping("/filtrar")
        public List<Disciplina> filtrar(
                        @RequestParam(value = "nome", required = false) String nome,
                        @RequestParam(value = "idCurso", required = false) Long idCurso) {

                return disciplinaService.filtrar(nome, idCurso);
        }

}