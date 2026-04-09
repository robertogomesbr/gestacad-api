package br.com.ifpe.gestacad.modelo.sala;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class SalaService {
    
    @Autowired
    private SalaRepository repository;

    @Transactional
    public Sala save(Sala sala) {
        
        sala.setHabilitado(Boolean.TRUE);
        return repository.save(sala);
    }

    public List<Sala> listarTodos() {

        return repository.findAll();
    }

    public Sala obterPorID(Long id) {

        return repository.findById(id).get();
    }

    @Transactional
    public void update(Long id, Sala salaAlterada) {

        Sala sala = repository.findById(id).get();
        sala.setBlocoSelecionado(salaAlterada.getBlocoSelecionado());
        sala.setNumero(salaAlterada.getNumero());
        sala.setTipo(salaAlterada.getTipo());
        sala.setStatus(salaAlterada.getStatus());

        repository.save(sala);
    }

    @Transactional
    public void delete(Long id) {

        Sala sala = repository.findById(id).get();
        sala.setHabilitado(Boolean.FALSE);

        repository.save(sala);
    }
}
