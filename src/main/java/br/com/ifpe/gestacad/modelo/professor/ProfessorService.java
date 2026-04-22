package br.com.ifpe.gestacad.modelo.professor;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class ProfessorService {

    @Autowired
    private ProfessorRepository repository;

    @Transactional
    public Professor save(Professor professor) {

        professor.setHabilitado(Boolean.TRUE);
        return repository.save(professor);
    }

    public List<Professor> listarTodos() {

        return repository.findAll();
    }

    public Professor obterPorID(Long id) {

        return repository.findById(id).get();
    }

    @Transactional
    public void update(Long id, Professor professorAlterado) {

        Professor professor = repository.findById(id).get();
        professor.setNome(professorAlterado.getNome());
        professor.setCpf(professorAlterado.getCpf());
        professor.setSenha(professorAlterado.getSenha());
        professor.setSiape(professorAlterado.getSiape());
        professor.setEmail(professorAlterado.getEmail());
        professor.setAtivo(professorAlterado.isAtivo());

        repository.save(professor);
    }

    @Transactional
    public void delete(Long id) {

        Professor professor = repository.findById(id).get();
        professor.setHabilitado(Boolean.FALSE);

        repository.save(professor);
    }
}