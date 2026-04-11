package br.com.ifpe.gestacad.modelo.reserva;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class ReservaService {
    
    @Autowired
    private ReservaRepository repository;

    @Transactional
    public Reserva save(Reserva reserva) {

        reserva.setHabilitado(Boolean.TRUE);
        return repository.save(reserva);
    }
    
    public List<Reserva> listarTodos() {

        return repository.findAll();
    }

    public Reserva obterPorID(Long id) {
        
        return repository.findById(id).get();
    }

    @Transactional
    public void update(Long id, Reserva reservaAlterado) {

        Reserva reserva = repository.findById(id).get();
        reserva.setSala(reservaAlterado.getSala());
        reserva.setDataReserva(reservaAlterado.getDataReserva());
        reserva.setHorarioInicio(reservaAlterado.getHorarioInicio());
        reserva.setHorarioFim(reservaAlterado.getHorarioFim());
        reserva.setStatusReserva(reservaAlterado.getStatusReserva());

        repository.save(reserva);
    }
    
    @Transactional
    public void delete(Long id) {

        Reserva reserva = repository.findById(id).get();
        reserva.setHabilitado(Boolean.FALSE);

        repository.save(reserva);
    }
}
