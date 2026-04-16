package br.com.ifpe.gestacad.modelo.horario;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class HorarioService {
    
    @Autowired
    private HorarioRepository repository;

    @Transactional
    public Horario save(Horario horario) {
        
        horario.setHabilitado(Boolean.TRUE);
        return repository.save(horario);
    }

    public List<Horario> listarTodos() {

        return repository.findAll();
    }

    public Horario obterPorID(Long id) {

        return repository.findById(id).get();
    }

    @Transactional
    public void update(Long id, Horario horarioAlterado) {

        Horario horario = repository.findById(id).get();
        horario.setHorario(horarioAlterado.getHorario());

        repository.save(horario);
    }

    @Transactional
    public void delete(Long id) {

        Horario horario = repository.findById(id).get();
        horario.setHabilitado(Boolean.FALSE);

        repository.save(horario);
    }
}
