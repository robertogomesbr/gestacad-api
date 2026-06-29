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

        if(repository.verificarDuplicidade(turma.getTurno(), turma.getCurso().getId(), turma.getSemestreEntrada(), turma.getAnoEntrada()) > 0) {
            throw new RuntimeException("Já existe uma turma cadastrada com os mesmos dados.");
        }

        turma.setHabilitado(Boolean.TRUE);
        turma.setStatusTurma(Boolean.TRUE);
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

        if(repository.verificarDuplicidade(turmaAlterada.getTurno(), turmaAlterada.getCurso().getId(), turmaAlterada.getSemestreEntrada(), turmaAlterada.getAnoEntrada()) > 0) {
            throw new RuntimeException("Já existe uma turma cadastrada com os mesmos dados.");
        }

        Turma turma = repository.findById(id).get();
        turma.setCurso(turmaAlterada.getCurso());
        turma.setNome(turmaAlterada.getNome());
        turma.setTurno(turmaAlterada.getTurno());
        turma.setAnoEntrada(turmaAlterada.getAnoEntrada());
        turma.setSemestreEntrada(turmaAlterada.getSemestreEntrada());
        turma.setQtdMaximaAlunos(turmaAlterada.getQtdMaximaAlunos());
        turma.setQtdAlunosMatriculados(turmaAlterada.getQtdAlunosMatriculados());
        turma.setStatusTurma(turmaAlterada.getStatusTurma());

        repository.save(turma);
    }

    @Transactional
    public void delete(Long id) {

        Turma turma = repository.findById(id).get();
        turma.setHabilitado(Boolean.FALSE);

        repository.save(turma);
    }

    public List<Turma> filtrar(String nome, String turno, Long idCurso) {

       List<Turma> listaTurmas = repository.findAll();
   
    if ((nome != null && !"".equals(nome)) && (turno == null || "".equals(turno)) && (idCurso == null)) {
        listaTurmas = repository.findByNomeContainingIgnoreCaseOrderByNomeAsc(nome);
    } 

    else if ((nome == null || "".equals(nome)) && (turno != null && !"".equals(turno)) && (idCurso == null)) {    
        listaTurmas = repository.findByTurnoContainingIgnoreCase(turno);
    } 

    else if ((nome == null || "".equals(nome)) && (turno == null || "".equals(turno)) && (idCurso != null)) {
        listaTurmas = repository.consultarPorCurso(idCurso);
    } 

    else if ((nome != null && !"".equals(nome)) && (turno == null || "".equals(turno)) && (idCurso != null)) {
        listaTurmas = repository.consultarPorNomeECurso(nome, idCurso);
    }

    else if ((nome == null || "".equals(nome)) && (turno != null && !"".equals(turno)) && (idCurso != null)) {
        listaTurmas = repository.consultarPorTurnoECurso(turno, idCurso);
    }
   
    else if ((nome == null || "".equals(nome)) && (turno == null || "".equals(turno)) && (idCurso == null)) {
        listaTurmas = repository.findAll();
    }

       return listaTurmas;
}

}