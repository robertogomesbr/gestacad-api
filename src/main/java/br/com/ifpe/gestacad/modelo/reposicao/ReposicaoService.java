package br.com.ifpe.gestacad.modelo.reposicao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class ReposicaoService {
    
    @Autowired
    private ReposicaoRepository repository;

    @Transactional
    public Reposicao save(Reposicao reposicao) {

        reposicao.setHabilitado(Boolean.TRUE);
        return repository.save(reposicao);
    }

    public List<Reposicao> listarTodos() {

        return repository.findAll();
    }

    public Reposicao obterPorID(Long id) {

        return repository.findById(id).get();
    }

    @Transactional
    public void update(Long id, Reposicao reposicaoAlterada) {

        Reposicao reposicao = repository.findById(id).get();
        reposicao.setDisciplina(reposicaoAlterada.getDisciplina());
        reposicao.setTurma(reposicaoAlterada.getTurma());
        reposicao.setProfessor(reposicaoAlterada.getProfessor());
        reposicao.setSala(reposicaoAlterada.getSala());
        reposicao.setDataAulaOriginal(reposicaoAlterada.getDataAulaOriginal());
        reposicao.setDataReposicao(reposicaoAlterada.getDataReposicao());
        reposicao.setHorarioInicio(reposicaoAlterada.getHorarioInicio());
        reposicao.setHorarioFim(reposicaoAlterada.getHorarioFim());
        reposicao.setStatusReposicao(reposicaoAlterada.getStatusReposicao());

        repository.save(reposicao);
    }

    @Transactional
    public void delete(Long id) {

        Reposicao reposicao = repository.findById(id).get();
        reposicao.setHabilitado(Boolean.FALSE);

        repository.save(reposicao);
    }
}
