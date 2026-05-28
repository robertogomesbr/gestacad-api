package br.com.ifpe.gestacad.modelo.curso;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ifpe.gestacad.modelo.acesso.Usuario;
import jakarta.transaction.Transactional;

@Service
public class CursoService {
    
    @Autowired
    private CursoRepository repository;

    @Transactional
    public Curso save(Curso curso, Usuario usuarioLogado) {
        
        curso.setHabilitado(Boolean.TRUE);
        curso.setCriadoPor(usuarioLogado);

        return repository.save(curso);
    }
    
    public List<Curso> listarTodos() {

        return repository.findAll();
    }

    public Curso obterPorID(Long id) {

        return repository.findById(id).get();
    }

    @Transactional
    public void update(Long id, Curso cursoAlterada) {

        Curso curso = repository.findById(id).get();
        curso.setNome(cursoAlterada.getNome());
        curso.setQtdPeriodos(cursoAlterada.getQtdPeriodos());
        curso.setArea(cursoAlterada.getArea());

        repository.save(curso);
    }

    @Transactional
    public void delete(Long id) {

        Curso curso = repository.findById(id).get();
        curso.setHabilitado(Boolean.FALSE);

        repository.save(curso);
    }
}
