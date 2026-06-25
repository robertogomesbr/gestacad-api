package br.com.ifpe.gestacad.modelo.sala;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ifpe.gestacad.modelo.acesso.Usuario;
import jakarta.transaction.Transactional;

@Service
public class SalaService {

    @Autowired
    private SalaRepository repository;

    @Transactional
    public Sala save(Sala sala, Usuario usuarioLogado) {

        sala.setHabilitado(Boolean.TRUE);
        sala.setCriadoPor(usuarioLogado);
        return repository.save(sala);
    }

    public List<Sala> listarTodos() {

        return repository.findAll();
    }

    public Sala obterPorID(Long id) {

        return repository.findById(id).get();
    }

    @Transactional
    public void update(Long id, Sala salaAlterada, Usuario usuarioLogado) {

        Sala sala = repository.findById(id).get();
        sala.setBloco(salaAlterada.getBloco());
        sala.setNumero(salaAlterada.getNumero());
        sala.setTipo(salaAlterada.getTipo());
        sala.setUltimaModificacaoPor(usuarioLogado);

        repository.save(sala);
    }

    @Transactional
    public void delete(Long id) {

        Sala sala = repository.findById(id).get();
        sala.setHabilitado(Boolean.FALSE);

        repository.save(sala);
    }

public List<Sala> filtrar(String bloco, String tipo) {
    
    if (bloco != null && !bloco.trim().isEmpty() && tipo != null && !tipo.trim().isEmpty()) {
        return repository.findByBlocoContainingIgnoreCaseAndTipoContainingIgnoreCase(bloco, tipo);
    }
    
    else if (bloco != null && !bloco.trim().isEmpty()) {
        return repository.findByBlocoContainingIgnoreCase(bloco);
    }
    
    else if (tipo != null && !tipo.trim().isEmpty()) {
        return repository.findByTipoContainingIgnoreCase(tipo);
    }

    return repository.findAll();
}

}
