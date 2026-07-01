package br.com.ifpe.gestacad.modelo.reposicao;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ifpe.gestacad.modelo.acesso.Usuario;
import br.com.ifpe.gestacad.modelo.mensagens.EmailService;
import br.com.ifpe.gestacad.modelo.professor.Professor;
import br.com.ifpe.gestacad.modelo.professor.ProfessorRepository;
import br.com.ifpe.gestacad.modelo.sala.Sala;
import jakarta.transaction.Transactional;

@Service
public class ReposicaoService {

    @Autowired
    private ReposicaoRepository repository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private ProfessorRepository professorRepository;

    public List<Sala> obterSalasDisponiveis(LocalDate dataReposicao, LocalTime horarioInicio, LocalTime horarioFim, String diaSemana, String semestreLetivo) {
        if (dataReposicao == null || horarioInicio == null || horarioFim == null || diaSemana == null || semestreLetivo == null) {
            throw new IllegalArgumentException("Todos os parâmetros de data, horários, dia da semana e semestre precisam ser informados.");
        }
        
        // Passa exatamente os 5 parâmetros recebidos para o Repository
        return repository.listarSalasDisponiveis(dataReposicao, horarioInicio, horarioFim, diaSemana, semestreLetivo);
    }
    @Transactional
    public Reposicao save(Reposicao reposicao, Usuario usuarioLogado) {

        Professor professor = professorRepository.findById(reposicao.getProfessor().getId())
                .orElseThrow(() -> new RuntimeException("Professor não encontrado no sistema."));

        if (Boolean.FALSE.equals(professor.isAtivo())) {
            throw new RuntimeException(
                    "Não é possível agendar reposições para um professor inativo no sistema. Aguarde a validação do Admin.");
        }

        if (reposicao.getDataReposicao().isBefore(reposicao.getDataAulaOriginal())) {
            throw new RuntimeException("A data de reposição não pode ser anterior à data da aula original.");

        }

        if(reposicao.getHorarioInicio().isAfter(reposicao.getHorarioFim())) {
            throw new RuntimeException("O horário de início não pode ser posterior ao horário de término.");
        }

         // Regra de Fim de Semana (Bloqueia Domingo)
        DayOfWeek diaSemana = reposicao.getDataReposicao().getDayOfWeek();
        if (diaSemana == DayOfWeek.SUNDAY) {
            throw new RuntimeException("Não é permitido agendar reposições de aulas aos domingos.");
        }
        //evita reposição repentina
        if(reposicao.getDataReposicao().atTime(reposicao.getHorarioInicio()).isBefore(java.time.LocalDateTime.now().plusHours(48))) {
            throw new RuntimeException("A reposição deve ser agendada com pelo menos 48 horas de antecedência.");

        }
        //evita agendamento de reposição para outro professor
        if (!usuarioLogado.getId().equals(professor.getUsuario().getId())) {
    throw new RuntimeException("Você não tem permissão para agendar reposições para outro professor.");
}


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
