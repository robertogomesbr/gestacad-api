package br.com.ifpe.gestacad.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import br.com.ifpe.gestacad.modelo.acesso.Perfil;
import br.com.ifpe.gestacad.modelo.acesso.PerfilRepository;
import br.com.ifpe.gestacad.modelo.acesso.Usuario;
import br.com.ifpe.gestacad.modelo.acesso.UsuarioRepository;

@Component
public class AdminInitializer implements CommandLineRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PerfilRepository perfilRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {

        Perfil perfilAdmin = perfilRepository.findByNome(Perfil.ROLE_ADMIN)
                .orElseGet(() -> {
                    Perfil novoPerfil = new Perfil();
                    novoPerfil.setNome(Perfil.ROLE_ADMIN);
                    novoPerfil.setHabilitado(true);
                    return perfilRepository.save(novoPerfil);
                });

        boolean adminExiste = usuarioRepository.existsByRolesNome(Perfil.ROLE_ADMIN);

        if (!adminExiste) {
            Usuario admin = new Usuario();
            admin.setUsername("gestacad@uorak.com");
            admin.setPassword(passwordEncoder.encode("123"));
            admin.setHabilitado(true);

            admin.setRoles(List.of(perfilAdmin));

            usuarioRepository.save(admin);
        }

    }
}
