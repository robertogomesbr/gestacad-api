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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.ifpe.gestacad.modelo.acesso.UsuarioService;
import br.com.ifpe.gestacad.modelo.curso.Curso;
import br.com.ifpe.gestacad.modelo.curso.CursoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/curso")
@CrossOrigin
@Tag(name = "API Curso", description = "API responsável pelos serviços de cursos no sistema")
public class CursoController {

        @Autowired
        private CursoService cursoService;

        @Autowired
        private UsuarioService usuarioService;

        @Operation(summary = "Serviço responsável pela criação de um curso no sistema.", description = "Endpoint responsável por cadastrar um novo curso associado ao usuário autenticado.")
        @PostMapping
        public ResponseEntity<Curso> save(
                        @RequestBody @Valid CursoRequest cursoRequest,
                        HttpServletRequest request) {

                Curso curso = cursoService.save(
                                cursoRequest.build(),
                                usuarioService.obterUsuarioLogado(request));

                return new ResponseEntity<>(curso, HttpStatus.CREATED);
        }

        @Operation(summary = "Serviço responsável por listar todos os cursos do sistema.", description = "Endpoint responsável por retornar todos os cursos cadastrados.")
        @GetMapping
        public List<Curso> listarTodos() {

                return cursoService.listarTodos();
        }

        @Operation(summary = "Serviço responsável por buscar um curso pelo ID.", description = "Endpoint responsável por retornar um curso específico a partir do seu ID.")
        @GetMapping("/{id}")
        public Curso obterPorID(@PathVariable Long id) {

                return cursoService.obterPorID(id);
        }

        @Operation(summary = "Serviço responsável por atualizar um curso pelo ID.", description = "Endpoint responsável por atualizar os dados de um curso existente a partir do seu ID.")
        @PutMapping("/{id}")
        public ResponseEntity<Curso> update(
                        @PathVariable("id") Long id,
                        @RequestBody @Valid CursoRequest cursoRequest, HttpServletRequest request) {

                cursoService.update(id, cursoRequest.build(), usuarioService.obterUsuarioLogado(request));
                return ResponseEntity.ok().build();
        }

        @Operation(summary = "Serviço responsável por remover um curso pelo ID.", description = "Endpoint responsável por excluir um curso do sistema a partir do seu ID.")
        @DeleteMapping("/{id}")
        public ResponseEntity<Void> delete(@PathVariable Long id) {

                cursoService.delete(id);
                return ResponseEntity.ok().build();
        }

        @PostMapping("/filtrar")
        public List<Curso> filtrar(
                        @RequestParam(value = "nome", required = false) String nome,
                        @RequestParam(value = "area", required = false) String area) {

                return cursoService.filtrar(nome, area);
        }

}
