package br.com.ifpe.gestacad.api.funcionario;

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

import br.com.ifpe.gestacad.modelo.acesso.Perfil;
import br.com.ifpe.gestacad.modelo.funcionario.Funcionario;
import br.com.ifpe.gestacad.modelo.funcionario.FuncionarioService;
import br.com.ifpe.gestacad.modelo.funcionario.TipoFuncionario;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/funcionario")
@CrossOrigin
@Tag(name = "API Funcionário", description = "API responsável pelos serviços de funcionários no sistema")
public class FuncionarioController {

    @Autowired
    private FuncionarioService funcionarioService;

    @Operation(summary = "Serviço responsável pela criação de um funcionário no sistema.", description = "Endpoint responsável por cadastrar um novo funcionário no sistema.")
    @PostMapping
    public ResponseEntity<Funcionario> save(@RequestBody @Valid FuncionarioRequest request) {

        Funcionario funcionarioNovo = request.build();

        if (funcionarioNovo.getTipo().equals(TipoFuncionario.ADMINISTRADOR)) {
            funcionarioNovo.getUsuario().getRoles().add(new Perfil(Perfil.ROLE_ADMIN));
        } else if (funcionarioNovo.getTipo().equals(TipoFuncionario.PROFESSOR)) {
            funcionarioNovo.getUsuario().getRoles().add(new Perfil(Perfil.ROLE_PROFESSOR));
        }

        Funcionario funcionario = funcionarioService.save(funcionarioNovo);
        return new ResponseEntity<>(funcionario, HttpStatus.CREATED);
    }

    @Operation(summary = "Serviço responsável por listar todos os funcionários do sistema.", description = "Endpoint responsável por retornar a lista de todos os funcionários cadastrados.")
    @GetMapping
    public List<Funcionario> listarTodos() {

        return funcionarioService.listarTodos();
    }

    @Operation(summary = "Serviço responsável por buscar um funcionário pelo ID.", description = "Endpoint responsável por retornar os dados de um funcionário específico a partir do seu ID.")
    @GetMapping("/{id}")
    public Funcionario obterPorID(@PathVariable Long id) {

        return funcionarioService.obterPorID(id);
    }

    @Operation(summary = "Serviço responsável por atualizar um funcionário pelo ID.", description = "Endpoint responsável por atualizar os dados de um funcionário existente a partir do seu ID.")
    @PutMapping("/{id}")
    public ResponseEntity<Funcionario> update(
            @PathVariable("id") Long id,
            @RequestBody FuncionarioRequest request) {

        funcionarioService.update(id, request.build());
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Serviço responsável por remover um funcionário pelo ID.", description = "Endpoint responsável por excluir um funcionário do sistema a partir do seu ID.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        funcionarioService.delete(id);
        return ResponseEntity.ok().build();
    }
}