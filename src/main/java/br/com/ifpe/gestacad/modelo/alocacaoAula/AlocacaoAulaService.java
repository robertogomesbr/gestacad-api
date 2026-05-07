package br.com.ifpe.gestacad.modelo.alocacaoAula;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class AlocacaoAulaService {
    
    @Autowired
    private AlocacaoAulaRepository repository;

    @Transactional
    public AlocacaoAula save(AlocacaoAula alocacaoAula) {

        alocacaoAula.setHabilitado(Boolean.TRUE);
        return repository.save(alocacaoAula);
    }
    
    public List<AlocacaoAula> listarTodos() {

        return repository.findAll();
    }

    public AlocacaoAula obterPorID(Long id) {
        
        return repository.findById(id).get();
    }

    @Transactional
    public void update(Long id, AlocacaoAula alocacaoAulaAlterado) {

        AlocacaoAula alocacaoAula = repository.findById(id).get();
        alocacaoAula.setTurma(alocacaoAula.getTurma());
        alocacaoAula.setDisciplina(alocacaoAula.getDisciplina());
        alocacaoAula.setSala(alocacaoAulaAlterado.getSala());
        alocacaoAula.setProfessor(alocacaoAulaAlterado.getProfessor());

        repository.save(alocacaoAula);
    }
    
    @Transactional
    public void delete(Long id) {

        AlocacaoAula alocacaoAula = repository.findById(id).get();
        alocacaoAula.setHabilitado(Boolean.FALSE);

        repository.save(alocacaoAula);
    }
}
