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
        if (repository.verificarReposicaoDuplicada(
                reposicao.getDisciplina().getId(),
                reposicao.getTurma().getId(),
                reposicao.getDataAulaOriginal(),
                reposicao.getDataReposicao(),
                reposicao.getHorarioInicio(),
                reposicao.getHorarioFim()) > 0) {
            throw new RuntimeException("Esta reposição já está cadastrada para este mesmo horário.");
        }

        if (repository.verificarConflitoSala(
                reposicao.getSala().getId(),
                reposicao.getDataAulaOriginal(),
                reposicao.getDataReposicao(),
                reposicao.getHorarioInicio(),
                reposicao.getHorarioFim()) > 0) {
            throw new RuntimeException("A sala selecionada já está ocupada neste horário.");
        }

        if (repository.verificarConflitoTurma(
                reposicao.getTurma().getId(),
                reposicao.getDataAulaOriginal(),
                reposicao.getDataReposicao(),
                reposicao.getHorarioInicio(),
                reposicao.getHorarioFim()) > 0) {
            throw new RuntimeException("A turma selecionada já possui outra aula ou reposição neste horário.");
        }

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
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reposição não encontrada com o ID: " + id));
    }

    @Transactional
    public void update(Long id, Reposicao reposicaoAlterada, Usuario usuarioLogado) {
        if (repository.verificarReposicaoDuplicadaAtualizacao(
                id,
                reposicaoAlterada.getDisciplina().getId(),
                reposicaoAlterada.getTurma().getId(),
                reposicaoAlterada.getDataAulaOriginal(),
                reposicaoAlterada.getDataReposicao(),
                reposicaoAlterada.getHorarioInicio(),
                reposicaoAlterada.getHorarioFim()) > 0) {
            throw new RuntimeException("Já existe outra reposição idêntica cadastrada para este mesmo horário.");
        }

        if (repository.verificarConflitoProfessorAtualizacao(
                id,
                reposicaoAlterada.getProfessor().getId(),
                reposicaoAlterada.getDataAulaOriginal(),
                reposicaoAlterada.getDataReposicao(),
                reposicaoAlterada.getHorarioInicio(),
                reposicaoAlterada.getHorarioFim()) > 0) {
            throw new RuntimeException("O professor selecionado possui conflito com outra atividade neste horário.");
        }

        if (repository.verificarConflitoSalaAtualizacao(
                id,
                reposicaoAlterada.getSala().getId(),
                reposicaoAlterada.getDataAulaOriginal(),
                reposicaoAlterada.getDataReposicao(),
                reposicaoAlterada.getHorarioInicio(),
                reposicaoAlterada.getHorarioFim()) > 0) {
            throw new RuntimeException("A sala selecionada está ocupada por outra atividade neste horário.");
        }

        if (repository.verificarConflitoTurmaAtualizacao(
                id,
                reposicaoAlterada.getTurma().getId(),
                reposicaoAlterada.getDataAulaOriginal(),
                reposicaoAlterada.getDataReposicao(),
                reposicaoAlterada.getHorarioInicio(),
                reposicaoAlterada.getHorarioFim()) > 0) {
            throw new RuntimeException("A turma possui conflito com outra atividade agendada neste horário.");
        }

        Reposicao reposicao = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reposição não encontrada com o ID: " + id));

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
        Reposicao reposicao = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reposição não encontrada com o ID: " + id));
        reposicao.setHabilitado(Boolean.FALSE);
        repository.save(reposicao);
    }
}
