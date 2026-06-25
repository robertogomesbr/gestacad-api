package br.com.ifpe.gestacad.modelo.reposicao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ifpe.gestacad.modelo.acesso.Usuario;
import br.com.ifpe.gestacad.modelo.mensagens.EmailService;
import jakarta.transaction.Transactional;

@Service
public class ReposicaoService {
    
    @Autowired
    private ReposicaoRepository repository;

    @Autowired
    private EmailService emailService;

    @Transactional
    public Reposicao save(Reposicao reposicao, Usuario usuarioLogado) {

        reposicao.setHabilitado(Boolean.TRUE);
        reposicao.setCriadoPor(usuarioLogado);
        reposicao.setStatusReposicao("PENDENTE");

        emailService.enviarEmailReposicao(reposicao);

        return repository.save(reposicao);
    }

    public List<Reposicao> listarTodos() {

        return repository.findAll();
    }

    public Reposicao obterPorID(Long id) {

        return repository.findById(id).get();
    }

    @Transactional
    public void update(Long id, Reposicao reposicaoAlterada, Usuario usuarioLogado) {

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

        reposicao.setUltimaModificacaoPor(usuarioLogado);

        repository.save(reposicao);
    }

    @Transactional
    public void delete(Long id) {

        Reposicao reposicao = repository.findById(id).get();
        reposicao.setHabilitado(Boolean.FALSE);

        repository.save(reposicao);
    }

       public Reposicao salvar(Reposicao novaReposicao) {
        
        if (repository.verificarReposicaoDuplicada(
                novaReposicao.getDisciplina().getId(),
                novaReposicao.getTurma().getId(),
                novaReposicao.getDataReposicao(),
                novaReposicao.getHorarioInicio(),
                novaReposicao.getHorarioFim()) > 0) {
            throw new RuntimeException("Esta reposição já está cadastrada para este mesmo horário.");
        }

        if (repository.verificarConflitoProfessor(
                novaReposicao.getProfessor().getId(),
                novaReposicao.getDataReposicao(),
                novaReposicao.getHorarioInicio(),
                novaReposicao.getHorarioFim()) > 0) {
            throw new RuntimeException("O professor selecionado já possui uma atividade agendada neste horário.");
        }

        if (repository.verificarConflitoSala(
                novaReposicao.getSala().getId(),
                novaReposicao.getDataReposicao(),
                novaReposicao.getHorarioInicio(),
                novaReposicao.getHorarioFim()) > 0) {
            throw new RuntimeException("A sala selecionada já está ocupada neste horário.");
        }

        if (repository.verificarConflitoTurma(
                novaReposicao.getTurma().getId(),
                novaReposicao.getDataReposicao(),
                novaReposicao.getHorarioInicio(),
                novaReposicao.getHorarioFim()) > 0) {
            throw new RuntimeException("A turma selecionada já possui outra aula ou reposição neste horário.");
        }

        return repository.save(novaReposicao);
    }

    public Reposicao atualizar(Long id, Reposicao reposicaoAtualizada) {
        
        if (repository.verificarReposicaoDuplicadaAtualizacao(
                id,
                reposicaoAtualizada.getDisciplina().getId(),
                reposicaoAtualizada.getTurma().getId(),
                reposicaoAtualizada.getDataReposicao(),
                reposicaoAtualizada.getHorarioInicio(),
                reposicaoAtualizada.getHorarioFim()) > 0) {
            throw new RuntimeException("Já existe outra reposição idêntica cadastrada para este mesmo horário.");
        }

        if (repository.verificarConflitoProfessorAtualizacao(
                id,
                reposicaoAtualizada.getProfessor().getId(),
                reposicaoAtualizada.getDataReposicao(),
                reposicaoAtualizada.getHorarioInicio(),
                reposicaoAtualizada.getHorarioFim()) > 0) {
            throw new RuntimeException("O professor selecionado possui conflito com outra atividade neste horário.");
        }

        if (repository.verificarConflitoSalaAtualizacao(
                id,
                reposicaoAtualizada.getSala().getId(),
                reposicaoAtualizada.getDataReposicao(),
                reposicaoAtualizada.getHorarioInicio(),
                reposicaoAtualizada.getHorarioFim()) > 0) {
            throw new RuntimeException("A sala selecionada está ocupada por outra atividade neste horário.");
        }

        if (repository.verificarConflitoTurmaAtualizacao(
                id,
                reposicaoAtualizada.getTurma().getId(),
                reposicaoAtualizada.getDataReposicao(),
                reposicaoAtualizada.getHorarioInicio(),
                reposicaoAtualizada.getHorarioFim()) > 0) {
            throw new RuntimeException("A turma possui conflito com outra atividade agendada neste horário.");
        }

        reposicaoAtualizada.setId(id);
        return repository.save(reposicaoAtualizada);
    }
}
