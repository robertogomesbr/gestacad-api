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

        if(repository.verificarDuplicidade(curso.getNome(), curso.getArea()) > 0) {
            throw new RuntimeException("Já existe um curso cadastrado com o mesmo nome e área.");
        }
        
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
    public void update(Long id, Curso cursoAlterada, Usuario usuarioLogado) {

        if(repository.verificarDuplicidade(cursoAlterada.getNome(), cursoAlterada.getArea()) > 0) {
            throw new RuntimeException("Já existe um curso cadastrado com o mesmo nome e área.");
        }

        Curso curso = repository.findById(id).get();
        curso.setNome(cursoAlterada.getNome());
        curso.setQtdPeriodos(cursoAlterada.getQtdPeriodos());
        curso.setArea(cursoAlterada.getArea());
        curso.setUltimaModificacaoPor(usuarioLogado);

        repository.save(curso);
    }

    @Transactional
    public void delete(Long id) {

        Curso curso = repository.findById(id).get();
        curso.setHabilitado(Boolean.FALSE);

        repository.save(curso);
    }

    public List<Curso> filtrar(String nome, String area) {

       List<Curso> listaCursos = repository.findAll();

       if ((nome != null && !"".equals(nome)) &&
           (area == null || "".equals(area))) {
               listaCursos = repository.consultarPorNome(nome);
       } else if (
           (nome == null || "".equals(nome)) &&
           (area != null && !"".equals(area))) {    
               listaCursos = repository.consultarPorArea(area);
           }else if
              ((nome != null && !"".equals(nome)) &&
               (area != null && !"".equals(area))) {
                   listaCursos = repository.consultarPorNomeEArea(nome, area);
               }
            
       return listaCursos;
}

}
