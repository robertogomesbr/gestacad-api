package br.com.ifpe.gestacad.modelo.disciplina;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class DisciplinaService {

    @Autowired
    private DisciplinaRepository repository;

    @Transactional
    public Disciplina save(Disciplina disciplina) {

        disciplina.setHabilitado(Boolean.TRUE);
        return repository.save(disciplina);
    }

    public List<Disciplina> listarTodos() {

        return repository.findAll();
    }

    public Disciplina obterPorID(Long id) {

        return repository.findById(id).get();
    }

    @Transactional
    public void update(Long id, Disciplina disciplinaAlterada) {

        Disciplina disciplina = repository.findById(id).get();
        disciplina.setNome(disciplinaAlterada.getNome());
        disciplina.setArea(disciplinaAlterada.getArea());
        disciplina.setHorarioInicio(disciplinaAlterada.getHorarioInicio());
        disciplina.setHorarioFim(disciplinaAlterada.getHorarioFim());

        repository.save(disciplina);
    }

    @Transactional
    public void delete(Long id) {

        Disciplina disciplina = repository.findById(id).get();
        disciplina.setHabilitado(Boolean.FALSE);

        repository.save(disciplina);
    }
}