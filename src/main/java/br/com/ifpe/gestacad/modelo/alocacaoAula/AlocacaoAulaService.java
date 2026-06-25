package br.com.ifpe.gestacad.modelo.alocacaoAula;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ifpe.gestacad.modelo.acesso.Usuario;
import br.com.ifpe.gestacad.modelo.horario.Horario;
import br.com.ifpe.gestacad.modelo.horario.HorarioRepository;
import br.com.ifpe.gestacad.util.exception.HorarioException;
import jakarta.transaction.Transactional;

@Service
public class AlocacaoAulaService {

    @Autowired
    private AlocacaoAulaRepository repository;

    @Autowired
    private HorarioRepository horarioRepository;

    @Transactional
    public AlocacaoAula save(AlocacaoAula alocacaoAula, Usuario usuarioLogado) {

        alocacaoAula.setHabilitado(Boolean.TRUE);
        alocacaoAula.setCriadoPor(usuarioLogado);
        return repository.save(alocacaoAula);
    }

    public List<AlocacaoAula> listarTodos() {

        return repository.findAll();
    }

    public AlocacaoAula obterPorID(Long id) {

        return repository.findById(id).get();
    }

    @Transactional
    public void update(Long id, AlocacaoAula alocacaoAulaAlterado, Usuario usuarioLogado) {

        AlocacaoAula alocacaoAula = repository.findById(id).get();
        alocacaoAula.setTurma(alocacaoAulaAlterado.getTurma());
        alocacaoAula.setDisciplina(alocacaoAulaAlterado.getDisciplina());
        alocacaoAula.setSala(alocacaoAulaAlterado.getSala());
        alocacaoAula.setProfessor(alocacaoAulaAlterado.getProfessor());
        alocacaoAula.setSemestreLetivo(alocacaoAulaAlterado.getSemestreLetivo());
        alocacaoAula.setUltimaModificacaoPor(usuarioLogado);

        repository.save(alocacaoAula);
    }

    @Transactional
    public void delete(Long id) {

        AlocacaoAula alocacaoAula = repository.findById(id).get();
        alocacaoAula.setHabilitado(Boolean.FALSE);

        repository.save(alocacaoAula);
    }

    @Transactional
    public Horario adicionarHorario(Long alocacaoAulaId, Horario horario) {

        AlocacaoAula alocacaoAula = this.obterPorID(alocacaoAulaId);

        validarHorario(alocacaoAula, horario);
        // Primeiro salva o Horario:

        horario.setAlocacaoAula(alocacaoAula);
        horario.setHabilitado(Boolean.TRUE);
        horarioRepository.save(horario);

        // Depois acrescenta o horario criado ao AlocacaoAula e atualiza o AlocacaoAula:

        List<Horario> listaHorario = alocacaoAula.getHorarios();

        if (listaHorario == null) {
            listaHorario = new ArrayList<Horario>();
        }

        listaHorario.add(horario);
        alocacaoAula.setHorarios(listaHorario);
        repository.save(alocacaoAula);

        return horario;
    }

    @Transactional
    public Horario atualizarHorario(Long id, Horario horarioAlterado) {

        Horario horario = horarioRepository.findById(id).get();

        validarHorarioAtualizacao(
                horario.getAlocacaoAula(),
                horarioAlterado,
                id);

        horario.setHorarioInicio(horarioAlterado.getHorarioInicio());
        horario.setHorarioFim(horarioAlterado.getHorarioFim());
        horario.setDiaSemana(horarioAlterado.getDiaSemana());

        if (horarioAlterado.getAlocacaoAula() != null) {
            horario.setAlocacaoAula(horarioAlterado.getAlocacaoAula());
        }

        return horarioRepository.save(horario);
    }

    @Transactional
    public void removerHorario(Long idhorario) {

        Horario horario = horarioRepository.findById(idhorario).get();
        horario.setHabilitado(Boolean.FALSE);
        horarioRepository.save(horario);

        AlocacaoAula alocacaoAula = this.obterPorID(horario.getAlocacaoAula().getId());
        alocacaoAula.getHorarios().removeIf(h -> h.getId().equals(idhorario));
        repository.save(alocacaoAula);
    }

    private void validarHorario(AlocacaoAula alocacao, Horario horario) {

        if (!horario.getHorarioFim().isAfter(horario.getHorarioInicio())) {
            throw new HorarioException("Horário final deve ser maior que o horário inicial.");
        }

        Long professorId = alocacao.getProfessor().getId();

        Long salaId = alocacao.getSala().getId();

        Long turmaId = alocacao.getTurma().getId();

        if (horarioRepository.verificarConflitoProfessor(
                professorId,
                horario.getDiaSemana(),
                horario.getHorarioInicio(),
                horario.getHorarioFim()) > 0) {

            throw new HorarioException(
                    HorarioException.MSG_PROFESSOR_OCUPADO);
        }

        if (horarioRepository.verificarConflitoSala(
                salaId,
                horario.getDiaSemana(),
                horario.getHorarioInicio(),
                horario.getHorarioFim()) > 0) {

            throw new HorarioException(
                    HorarioException.MSG_SALA_OCUPADA);
        }

        if (horarioRepository.verificarConflitoTurma(
                turmaId,
                horario.getDiaSemana(),
                horario.getHorarioInicio(),
                horario.getHorarioFim()) > 0) {

            throw new HorarioException(
                    HorarioException.MSG_TURMA_OCUPADA);
        }

        if (horarioRepository.verificarHorarioDuplicado(
                alocacao.getId(),
                horario.getDiaSemana(),
                horario.getHorarioInicio(),
                horario.getHorarioFim()) > 0) {

            throw new HorarioException(
                    HorarioException.MSG_HORARIO_DUPLICADO);
        }
    }

    private void validarHorarioAtualizacao(AlocacaoAula alocacao, Horario horario, Long horarioId) {

        if (!horario.getHorarioFim().isAfter(horario.getHorarioInicio())) {
            throw new HorarioException("Horário final deve ser maior que o horário inicial.");
        }

        Long professorId = alocacao.getProfessor().getId();
        Long salaId = alocacao.getSala().getId();
        Long turmaId = alocacao.getTurma().getId();

        if (horarioRepository.verificarConflitoProfessorAtualizacao(
                horarioId,
                professorId,
                horario.getDiaSemana(),
                horario.getHorarioInicio(),
                horario.getHorarioFim()) > 0) {

            throw new HorarioException(HorarioException.MSG_PROFESSOR_OCUPADO);
        }

        if (horarioRepository.verificarConflitoSalaAtualizacao(
                horarioId,
                salaId,
                horario.getDiaSemana(),
                horario.getHorarioInicio(),
                horario.getHorarioFim()) > 0) {

            throw new HorarioException(HorarioException.MSG_SALA_OCUPADA);
        }

        if (horarioRepository.verificarConflitoTurmaAtualizacao(
                horarioId,
                turmaId,
                horario.getDiaSemana(),
                horario.getHorarioInicio(),
                horario.getHorarioFim()) > 0) {

            throw new HorarioException(HorarioException.MSG_TURMA_OCUPADA);
        }

        if (horarioRepository.verificarHorarioDuplicadoAtualizacao(
                horarioId,
                alocacao.getId(),
                horario.getDiaSemana(),
                horario.getHorarioInicio(),
                horario.getHorarioFim()) > 0) {

            throw new HorarioException(HorarioException.MSG_HORARIO_DUPLICADO);
        }
    }
}
