package br.com.ifpe.gestacad.modelo.professor;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import br.com.ifpe.gestacad.modelo.acesso.Perfil;
import br.com.ifpe.gestacad.modelo.acesso.PerfilRepository;
import br.com.ifpe.gestacad.modelo.acesso.Usuario;
import br.com.ifpe.gestacad.modelo.acesso.UsuarioRepository;
import br.com.ifpe.gestacad.modelo.acesso.UsuarioService;
import br.com.ifpe.gestacad.modelo.mensagens.EmailService;
import jakarta.transaction.Transactional;

@Service
public class ProfessorService {

    @Autowired
    private ProfessorRepository repository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PerfilRepository perfilUsuarioRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional
    public Professor save(Professor professor, Usuario usuarioLogado) {

        if (repository.verificarDuplicidadeEmail(
                professor.getUsuario().getUsername()) > 0) {
            throw new RuntimeException("Já existe um professor cadastrado com o mesmo email.");
        }

        usuarioService.save(professor.getUsuario());

        for (Perfil perfil : professor.getUsuario().getRoles()) {
            perfil.setHabilitado(Boolean.TRUE);
            perfilUsuarioRepository.save(perfil);
        }

        professor.setHabilitado(Boolean.TRUE);
        professor.setCriadoPor(usuarioLogado);
        Professor professorSalvo = repository.save(professor);

        emailService.enviarEmailSolicitacaoCadastroProfessor(professorSalvo);

        return professorSalvo;
    }

    public List<Professor> listarTodos() {

        return repository.findAll();
    }

    public Professor obterPorID(Long id) {

        return repository.findById(id).get();
    }

    @Transactional
    public void update(Long id, Professor professorAlterado, Usuario usuarioLogado) {

        if (repository.verificarDuplicidadeEmailAtualizacao(
                id,
                professorAlterado.getUsuario().getUsername()) > 0) {
            throw new RuntimeException("Já existe um professor cadastrado com o mesmo email.");
        }

        Professor professor = repository.findById(id).get();
        professor.setNome(professorAlterado.getNome());
        professor.setCpf(professorAlterado.getCpf());
        professor.setSiape(professorAlterado.getSiape());
        professor.setEmail(professorAlterado.getEmail());
        professor.setAtivo(professorAlterado.isAtivo());

        professor.setUltimaModificacaoPor(usuarioLogado);

        repository.save(professor);
    }

    public List<Professor> filtrar(String nome, String cpf) {

        List<Professor> listaProfessores = repository.findAll();

        if ((nome != null && !"".equals(nome))) {
            listaProfessores = repository.findByNomeContainingIgnoreCaseOrderByNomeAsc(nome);
        } else if ((nome == null || "".equals(cpf))) {
            listaProfessores = repository.findByCpfContainingIgnoreCase(cpf);
        } else if ((nome != null && !"".equals(nome)) &&
                (cpf != null && !"".equals(cpf))) {
            listaProfessores = repository.findByNomeContainingIgnoreCaseOrderByNomeAsc(nome);
            listaProfessores = repository.findByCpfContainingIgnoreCase(cpf);
        }

        return listaProfessores;
    }

    @Transactional
    public void reprovarProfessor(Long professorId) {
        Professor professor = repository.findById(professorId)
                .orElseThrow(() -> new ResourceNotFoundException("Professor não encontrado"));

        Usuario usuario = professor.getUsuario();
        if (usuario != null) {
            usuario.setHabilitado(false);
            usuarioRepository.save(usuario);
        }

        repository.delete(professor);

    }

}