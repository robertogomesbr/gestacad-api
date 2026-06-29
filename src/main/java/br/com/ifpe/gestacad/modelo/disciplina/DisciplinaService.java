package br.com.ifpe.gestacad.modelo.disciplina;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ifpe.gestacad.modelo.acesso.Usuario;
import jakarta.transaction.Transactional;

@Service
public class DisciplinaService {

    @Autowired
    private DisciplinaRepository repository;

    @Transactional
    public Disciplina save(Disciplina disciplina, Usuario usuarioLogado) {

        if(repository.verificarDuplicidade(disciplina.getNome(), disciplina.getCurso().getId()) > 0) {
            throw new RuntimeException("Já existe uma disciplina cadastrada com o mesmo nome para este curso.");
        }

        disciplina.setHabilitado(Boolean.TRUE);
        disciplina.setCriadoPor(usuarioLogado);
        return repository.save(disciplina);
    }

    public List<Disciplina> listarTodos() {

        return repository.findAll();
    }

    public Disciplina obterPorID(Long id) {

        return repository.findById(id).get();
    }

    @Transactional
    public void update(Long id, Disciplina disciplinaAlterada, Usuario usuarioLogado) {

        if(repository.verificarDuplicidade(disciplinaAlterada.getNome(), disciplinaAlterada.getCurso().getId()) > 0) {
            throw new RuntimeException("Já existe uma disciplina cadastrada com o mesmo nome para este curso.");
        }

        Disciplina disciplina = repository.findById(id).get();
        disciplina.setNome(disciplinaAlterada.getNome());
        disciplina.setChTotal(disciplinaAlterada.getChTotal());
        disciplina.setPeriodoOfertado(disciplinaAlterada.getPeriodoOfertado());
        disciplina.setUltimaModificacaoPor(usuarioLogado);
        repository.save(disciplina);
    }

    @Transactional
    public void delete(Long id) {

        Disciplina disciplina = repository.findById(id).get();
        disciplina.setHabilitado(Boolean.FALSE);

        repository.save(disciplina);
    }
    
public List<Disciplina> filtrar(String nome, Long idCurso) {
    

    if (nome != null && !nome.trim().isEmpty() && idCurso != null) {
        return repository.findByNomeContainingIgnoreCaseAndCursoIdOrderByNomeAsc(nome, idCurso);
    }
    
    else if (nome != null && !nome.trim().isEmpty()) {
        return repository.findByNomeContainingIgnoreCaseOrderByNomeAsc(nome);
    }

   else if (idCurso != null) {
        return repository.findByCursoIdOrderByNomeAsc(idCurso);
    }
    
    return repository.findAll();
}

}