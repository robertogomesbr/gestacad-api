package br.com.ifpe.gestacad.api.acesso;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ifpe.gestacad.modelo.acesso.Perfil;
import br.com.ifpe.gestacad.modelo.acesso.Usuario;
import br.com.ifpe.gestacad.modelo.acesso.UsuarioService;
import br.com.ifpe.gestacad.modelo.mensagens.EmailService;
import br.com.ifpe.gestacad.modelo.professor.Professor;
import br.com.ifpe.gestacad.modelo.professor.ProfessorRepository;
import br.com.ifpe.gestacad.modelo.segurança.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
@Tag(
        name = "API Autenticação",
        description = "API responsável por autenticar um usuário no sistema"
)
public class AuthenticationController {

    private final JwtService jwtService;

    private final UsuarioService usuarioService;

    private final EmailService emailService;

    @Autowired
    private ProfessorRepository professorRepository;


    public AuthenticationController(JwtService jwtService, UsuarioService usuarioService, EmailService emailService) {

        this.jwtService = jwtService;
        this.usuarioService = usuarioService;
        this.emailService = emailService;
    }

    @Operation(
            summary = "Serviço responsável por autenticar um usuário no sistema.",
            description = "Exemplo de descrição de um endpoint responsável por autenticar um usuário no sistema."
    )
    @PostMapping
    public Map<Object, Object> signin(@RequestBody AuthenticationRequest data) {

        Usuario authenticatedUser = usuarioService.authenticate(data.getUsername(), data.getPassword());

        String jwtToken = jwtService.generateToken(authenticatedUser);

        Map<Object, Object> loginResponse = new HashMap<>();
        loginResponse.put("username", authenticatedUser.getUsername());
        loginResponse.put("token", jwtToken);
        loginResponse.put("tokenExpiresIn", jwtService.getExpirationTime());

        boolean isAdmin = authenticatedUser.getRoles().stream()
                .anyMatch(role -> role.getNome().equals(Perfil.ROLE_ADMIN));

        boolean isProfessor = authenticatedUser.getRoles().stream()
                .anyMatch(role -> role.getNome().equals(Perfil.ROLE_PROFESSOR));

        if (isAdmin) {

            emailService.enviarEmailLoginAdmin(authenticatedUser);

        } 
        
        if (isProfessor) {

            Professor professor = professorRepository.findByUsuario(authenticatedUser)
                    .orElseThrow();

            emailService.enviarEmailLoginProfessor(professor);
        }

        return loginResponse;
    }
}
