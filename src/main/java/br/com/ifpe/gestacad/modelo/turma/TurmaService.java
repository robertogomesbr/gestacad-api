package br.com.ifpe.gestacad.modelo.turma;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class TurmaService {

    @Autowired
    private TurmaRepository repository;

    @Transactional
    public Turma save(Turma turma) {

        turma.setHabilitado(Boolean.TRUE);
        return repository.save(turma);
    }

    public List<Turma> listarTodos() {

        return repository.findAll();
    }

    public Turma obterPorID(Long id) {

        return repository.findById(id).get();
    }

    @Transactional
    public void update(Long id, Turma turmaAlterada) {

        Turma turma = repository.findById(id).get();
        turma.setPeriodo(turmaAlterada.getPeriodo());
        turma.setCurso(turmaAlterada.getCurso());

        repository.save(turma);
    }

    @Transactional
    public void delete(Long id) {

        Turma turma = repository.findById(id).get();
        turma.setHabilitado(Boolean.FALSE);

        repository.save(turma);
    }
}